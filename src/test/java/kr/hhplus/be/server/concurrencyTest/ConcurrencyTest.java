package kr.hhplus.be.server.concurrencyTest;

import kr.hhplus.be.server.coupon.entity.Coupon;
import kr.hhplus.be.server.coupon.repository.CouponJpaRepository;
import kr.hhplus.be.server.coupon.service.CouponService;
import kr.hhplus.be.server.order.entity.Order;
import kr.hhplus.be.server.order.entity.OrderProduct;
import kr.hhplus.be.server.order.facade.OrderFacade;
import kr.hhplus.be.server.order.repository.OrderJpaRepository;
import kr.hhplus.be.server.order.repository.OrderRepository;
import kr.hhplus.be.server.point.service.PointService;
import kr.hhplus.be.server.product.entity.Product;
import kr.hhplus.be.server.product.repository.ProductJpaRepository;
import kr.hhplus.be.server.user.entity.User;
import kr.hhplus.be.server.user.repository.UserJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class ConcurrencyTest {

    @Autowired
    CouponService couponService;
    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    CouponJpaRepository couponJpaRepository;
    @Autowired
    PointService pointService;
    @Autowired
    ProductJpaRepository productJpaRepository;
    @Autowired
    OrderFacade orderFacade;
    @Autowired
    OrderJpaRepository orderJpaRepository;

    private User user1;
    private User user2;
    private Coupon coupon;
    private Product product;

    @BeforeEach
    void setUp() {
        user1 = new User(0L);
        userJpaRepository.save(user1);
        user2 = new User(0L);
        userJpaRepository.save(user2);

        Date yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        coupon = new Coupon(10L, 100L, 0L, yesterday, null, new Date());
        couponJpaRepository.save(coupon);

        product = new Product("테스트상품", 5000, 10);
        product = productJpaRepository.save(product);

        couponJpaRepository.flush();
        userJpaRepository.flush();
    }

    @AfterEach
    void cleanUp() {
        userJpaRepository.deleteAll();
        couponJpaRepository.deleteAll();
        orderJpaRepository.deleteAll();
    }

    @Test
    public void 선착순_쿠폰_발급() { // 비관적 락
        CountDownLatch latch = new CountDownLatch(2);
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(2);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 10, SECONDS, queue);
        executor.execute(() -> {
            try {
                couponService.requestIssueCoupon(coupon.getId(), user1.getId());
            }
            finally {
                latch.countDown();
            }
        });
        executor.execute(() -> {
            try {
                couponService.requestIssueCoupon(coupon.getId(), user2.getId());
            }
            finally {
                latch.countDown();
            }
        });
        latch.countDown();

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Coupon result = couponJpaRepository.findById(coupon.getId()).orElseThrow();
        assertThat(result.getIssuedCount()).isEqualTo(2);
    }

    @Test
    public void 포인트충전() { // 낙관적 락
        CountDownLatch latch = new CountDownLatch(2);
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(2);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 10, SECONDS, queue);
        executor.execute(() -> {
            try {
                pointService.chargePoint(user1.getId(), 1000);
            } finally {
                latch.countDown();
            }
        });

        executor.execute(() -> {
            try {
                pointService.chargePoint(user1.getId(), 1000);
            } finally {
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        User user = userJpaRepository.findById(user1.getId()).orElseThrow();
        assertThat(user.getPoint()).isEqualTo(1000);
    }

    @Test
    public void 중복_주문_포인트_체크() {
        pointService.chargePoint(user1.getId(), 10000);
        CountDownLatch latch = new CountDownLatch(2);
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(2);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 10, SECONDS, queue);
        List<OrderProduct> orderProductList1 = new ArrayList<>();
        long id = product.getId();
        OrderProduct orderProduct = new OrderProduct(product.getId(), 1L);
        orderProductList1.add(orderProduct);
        executor.execute(() -> {
            try {
                Long orderResult = orderFacade.order(user1.getId(), orderProductList1, null);
            } finally {
                latch.countDown();
            }
        });
        executor.execute(() -> {
            try {
                Long orderResult = orderFacade.order(user1.getId(), orderProductList1, null);
            } finally {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Order> result = orderJpaRepository.findAllByUserId(user1.getId());
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void 재고_차감() {
        pointService.chargePoint(user1.getId(), 10000);
        pointService.chargePoint(user2.getId(), 10000);
        CountDownLatch latch = new CountDownLatch(2);
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(2);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 10, SECONDS, queue);
        List<OrderProduct> orderProductList1 = new ArrayList<>();
        List<OrderProduct> orderProductList2 = new ArrayList<>();
        long id = product.getId();
        OrderProduct orderProduct1 = new OrderProduct(product.getId(), 1L);
        OrderProduct orderProduct2 = new OrderProduct(product.getId(), 1L);
        orderProductList1.add(orderProduct1);
        orderProductList2.add(orderProduct2);
        executor.execute(() -> {
            try {
                Long orderResult = orderFacade.order(user1.getId(), orderProductList1, null);
            } finally {
                latch.countDown();
            }
        });
        executor.execute(() -> {
            try {
                Long orderResult = orderFacade.order(user2.getId(), orderProductList2, null);
            } finally {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Product result = productJpaRepository.findById(product.getId()).orElseThrow();
        assertThat(result.getStock()).isEqualTo(8);
    }
}
