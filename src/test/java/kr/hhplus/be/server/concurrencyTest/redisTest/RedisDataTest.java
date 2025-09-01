package kr.hhplus.be.server.concurrencyTest.redisTest;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponJpaRepository;
import kr.hhplus.be.server.domain.coupon.repository.UserCouponJpaRepository;
import kr.hhplus.be.server.domain.coupon.service.CouponService;
import kr.hhplus.be.server.domain.order.dto.OrderRequest;
import kr.hhplus.be.server.domain.order.dto.OrderResponse;
import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import kr.hhplus.be.server.domain.order.facade.OrderFacade;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductJpaRepository;
import kr.hhplus.be.server.domain.product.service.ProductService;
import kr.hhplus.be.server.redis.repository.RedisRepository;
import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import kr.hhplus.be.server.domain.user.userService.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@SpringBootTest
public class RedisDataTest {

    @Autowired
    OrderFacade orderFacade;
    @Autowired
    CouponService couponService;
    @Autowired
    UserService userService;
    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    CouponJpaRepository couponJpaRepository;
    @Autowired
    RedisRepository redisRepository;
    @Autowired
    UserCouponJpaRepository userCouponJpaRepository;
    @Autowired
    ProductJpaRepository productJpaRepository;
    @Autowired
    ProductService productService;

    private final int numberOfThread = 500;
    private final long couponStock = 100;
    private Coupon coupon;
    private User user;
    private Product product;

    @BeforeEach
    void setUp() {
        couponJpaRepository.deleteAll();
        coupon = new Coupon(10L, couponStock, 0L, new Date());
        coupon = couponJpaRepository.save(coupon);
        for (int i = 1; i <= numberOfThread; i++) {
            User user = new User(10000);
            userJpaRepository.save(user);
        }

        user = new User(10000);
        userJpaRepository.save(user);
        Date yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        Coupon coupon = new Coupon(10, 100, 1, yesterday, null, yesterday);
        Coupon targetCoupon = couponJpaRepository.save(coupon);
        UserCoupon userCoupon = new UserCoupon(user.getId(), targetCoupon.getId());
        userCouponJpaRepository.save(userCoupon);
        userCouponJpaRepository.save(userCoupon);
        product = new Product("테스트상품", 10000, 1000);
        productJpaRepository.save(product);
    }

    @AfterEach
    void cleanUp() {
        userJpaRepository.deleteAll();
        couponJpaRepository.deleteAll();
    }

    @Test
    public void 대기열_쿠폰_요청() {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThread);
        CountDownLatch latch = new CountDownLatch(numberOfThread);

        List<User> userList = userJpaRepository.findAll();
        for (int i = 0; i < numberOfThread; i++) {
            final int idx = i;
            executorService.submit(() -> {
                try {
                    couponService.requestIssueCoupon(coupon.getId(), userList.get(idx).getId());
                }
                finally {
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long requestCount = redisRepository.getQueueCount(coupon.getId());

        Assertions.assertThat(requestCount).isEqualTo(userList.size());
    }

    @Test
    public void 대기열_쿠폰_발급() {
        // 요청
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThread);
        CountDownLatch latch = new CountDownLatch(numberOfThread);

        List<User> userList = userJpaRepository.findAll();
        for (int i = 0; i < numberOfThread; i++) {
            final int idx = i;
            executorService.submit(() -> {
                try {
                    couponService.requestIssueCoupon(coupon.getId(), userList.get(idx).getId());
                }
                finally {
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long requestCount = redisRepository.getQueueCount(coupon.getId());

        Assertions.assertThat(requestCount).isEqualTo(userList.size());

        // 발급
        Set<String> keySets = redisRepository.getRequestKeyList("coupon");
        couponService.issueCoupon();

        long issuedCount = userCouponJpaRepository.countByCouponId(coupon.getId());
        if (coupon.getTotalCount() > numberOfThread) {
            Assertions.assertThat(issuedCount).isEqualTo(numberOfThread);
        }
        else {
            Assertions.assertThat(issuedCount).isEqualTo(coupon.getTotalCount());
        }
    }

    @Test
    void 베스트_상품_키저장_확인() {
        List<OrderProduct> orderProductList = new ArrayList<>();
        OrderProduct orderProduct = new OrderProduct(product.getId(), 1L);
        orderProductList.add(orderProduct);
        Long result = orderFacade.order(new OrderRequest(orderProductList, null, user.getId()));
        OrderResponse orderInfo = orderFacade.getOrder(result);

        productService.mergeRankingScore();
        Set<String> keySets = redisRepository.getRequestKeyList("product:rank");
        Assertions.assertThat(keySets.size()).isEqualTo(2);
        List<Product> bestList = productService.getBestProductList();
        Assertions.assertThat(bestList.size()).isEqualTo(1);
    }
}
