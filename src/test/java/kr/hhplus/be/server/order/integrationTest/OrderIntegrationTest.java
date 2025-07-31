package kr.hhplus.be.server.order.integrationTest;

import kr.hhplus.be.server.coupon.entity.Coupon;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import kr.hhplus.be.server.coupon.repository.CouponJpaRepository;
import kr.hhplus.be.server.coupon.repository.CouponRepository;
import kr.hhplus.be.server.coupon.repository.UserCouponJpaRepository;
import kr.hhplus.be.server.coupon.repository.UserCouponRepository;
import kr.hhplus.be.server.coupon.service.UserCouponService;
import kr.hhplus.be.server.order.dto.OrderResponse;
import kr.hhplus.be.server.order.entity.OrderProduct;
import kr.hhplus.be.server.order.entity.OrderStatus;
import kr.hhplus.be.server.order.facade.OrderFacade;
import kr.hhplus.be.server.order.service.OrderService;
import kr.hhplus.be.server.product.entity.Product;
import kr.hhplus.be.server.product.repository.ProductJpaRepository;
import kr.hhplus.be.server.user.entity.User;
import kr.hhplus.be.server.user.repository.UserJpaRepository;
import kr.hhplus.be.server.user.userService.UserService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@ActiveProfiles("test")
public class OrderIntegrationTest {

    @Autowired
    OrderFacade orderFacade;
    @Autowired
    CouponJpaRepository couponJpaRepository;
    @Autowired
    UserCouponJpaRepository userCouponJpaRepository;
    @Autowired
    ProductJpaRepository productJpaRepository;
    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    UserCouponService userCouponService;

    @Transactional
    @Rollback
    @BeforeEach
    void setUp() {
        User user = new User(10000);
        userJpaRepository.save(user);
        Date yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        Coupon coupon = new Coupon(10, 100, 1, yesterday, null, yesterday);
        Coupon targetCoupon = couponJpaRepository.save(coupon);
        UserCoupon userCoupon = new UserCoupon(1L, targetCoupon.getId());
        userCouponJpaRepository.save(userCoupon);
        userCouponJpaRepository.save(userCoupon);
        Product product = new Product("테스트상품", 10000, 1000);
        productJpaRepository.save(product);
    }

    @Test
    void 주문테스트() {
        List<OrderProduct> orderProductList = new ArrayList<>();
        OrderProduct orderProduct = new OrderProduct(1L, 1L);
        orderProductList.add(orderProduct);
        OrderResponse result = orderFacade.order(1L, orderProductList, new ArrayList<>());
        assertAll("주문 확인",
                () -> assertThat(result.getPayment().getTotalPrice()).isEqualTo(10000),
                () -> assertThat(result.getPayment().getCouponDiscountPrice()).isEqualTo(0),
                () -> assertThat(result.getPayment().getPaymentPrice()).isEqualTo(10000),
                () -> assertThat(result.getOrder().getStatus()).isEqualTo(OrderStatus.ORDER_COMPLETE),
                () -> assertThat(result.getUser().getId()).isEqualTo(1L)
        );
    }

    @Test
    void 주문_쿠폰사용_테스트() {
        List<OrderProduct> orderProductList = new ArrayList<>();
        OrderProduct orderProduct = new OrderProduct(1L, 1L);
        orderProductList.add(orderProduct);
        List<UserCoupon> userCouponList = userCouponService.getUserCouponList(1L);
        List<UserCoupon> useList = new ArrayList<>();
        useList.add(userCouponList.get(0));
        OrderResponse result = orderFacade.order(1L, orderProductList, useList);

        assertAll("주문 확인",
                () -> assertThat(result.getPayment().getTotalPrice()).isEqualTo(10000),
                () -> assertThat(result.getPayment().getCouponDiscountPrice()).isEqualTo(1000),
                () -> assertThat(result.getPayment().getPaymentPrice()).isEqualTo(9000),
                () -> assertThat(result.getOrder().getStatus()).isEqualTo(OrderStatus.ORDER_COMPLETE),
                () -> assertThat(result.getUser().getId()).isEqualTo(1L),
                () -> assertThat(result.getCouponList()).hasSize(1)
        );
    }

    @Test
    void 외부_플랫폼_데이터전송() {
        List<OrderProduct> orderProductList = new ArrayList<>();
        OrderProduct orderProduct = new OrderProduct(1L, 1L);
        orderProductList.add(orderProduct);
        List<UserCoupon> userCouponList = userCouponService.getUserCouponList(1L);
        List<UserCoupon> useList = new ArrayList<>();
        useList.add(userCouponList.get(1));
        OrderResponse orderResult = orderFacade.order(1L, orderProductList, useList);

        OrderResponse result = orderFacade.getOrder(orderResult.getOrder().getId());
        assertAll("주문 데이터",
                () -> assertThat(result.getPayment().getTotalPrice()).isEqualTo(10000),
                () -> assertThat(result.getPayment().getCouponDiscountPrice()).isEqualTo(1000),
                () -> assertThat(result.getPayment().getPaymentPrice()).isEqualTo(9000),
                () -> assertThat(result.getOrder().getStatus()).isEqualTo(OrderStatus.ORDER_COMPLETE),
                () -> assertThat(result.getUser().getId()).isEqualTo(1L)
        );
    }
}
