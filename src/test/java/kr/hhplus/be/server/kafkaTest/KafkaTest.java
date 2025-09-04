package kr.hhplus.be.server.kafkaTest;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.kafka.CouponConsumer;
import kr.hhplus.be.server.domain.coupon.repository.CouponJpaRepository;
import kr.hhplus.be.server.domain.coupon.repository.UserCouponJpaRepository;
import kr.hhplus.be.server.domain.coupon.repository.UserCouponRepository;
import kr.hhplus.be.server.domain.coupon.service.CouponService;
import kr.hhplus.be.server.domain.order.dto.OrderRequest;
import kr.hhplus.be.server.domain.order.dto.OrderResponse;
import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import kr.hhplus.be.server.domain.order.facade.OrderFacade;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductJpaRepository;
import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@ActiveProfiles("test")
@SpringBootTest
public class KafkaTest {

    @Autowired
    OrderFacade orderFacade;
    @Autowired
    CouponService couponService;
    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    CouponJpaRepository couponJpaRepository;
    @Autowired
    ProductJpaRepository productJpaRepository;
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    UserCouponJpaRepository userCouponJpaRepository;

    private User user;
    private Coupon coupon;
    private Product product;

    @BeforeEach
    void setUp() {
        user = new User(100000L);
        userJpaRepository.save(user);
        coupon = new Coupon(10, 1000, 0, new Date());
        couponJpaRepository.save(coupon);
        product = new Product("테스트상품", 10000, 1000);
        productJpaRepository.save(product);
    }

    @Test
    public void 카프카_쿠폰_요청() {
        long userId = user.getId();
        long couponId = coupon.getId();
        couponService.requestCoupon(couponId, userId);
        int partition = (int) (couponId % 10);
//        verify(kafkaTemplate, timeout(5000))
//                .send(eq("coupon"), eq(partition), eq(String.valueOf(couponId)), eq(String.valueOf(userId)));
        System.out.println(new Date());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int retry = 5;
        List<UserCoupon> userCouponList = List.of();
        while (retry-- > 0) {
            userCouponList = userCouponJpaRepository.findByUserId(userId);
            if (!userCouponList.isEmpty()) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Assertions.assertThat(userCouponList.get(0).getCouponId()).isEqualTo(couponId);
    }

    @Test
    public void 외부_플랫폼_전송() {
        List<OrderProduct> orderProductList = new ArrayList<>();
        OrderProduct orderProduct = new OrderProduct(product.getId(), 1L);
        orderProductList.add(orderProduct);
        OrderRequest orderRequest = new OrderRequest(orderProductList, null, user.getId());
        Long result = orderFacade.order(orderRequest);
        verify(kafkaTemplate, timeout(5000))
            .send(eq("orderComplete"), eq(String.valueOf(result)), anyString());
    }
}
