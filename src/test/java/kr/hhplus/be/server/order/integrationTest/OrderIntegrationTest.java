package kr.hhplus.be.server.order.integrationTest;

import kr.hhplus.be.server.coupon.entity.Coupon;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import kr.hhplus.be.server.coupon.repository.CouponJpaRepository;
import kr.hhplus.be.server.coupon.repository.UserCouponJpaRepository;
import kr.hhplus.be.server.coupon.service.CouponService;
import kr.hhplus.be.server.coupon.service.UserCouponService;
import kr.hhplus.be.server.order.dto.OrderResponse;
import kr.hhplus.be.server.order.entity.OrderProduct;
import kr.hhplus.be.server.order.entity.OrderStatus;
import kr.hhplus.be.server.order.facade.OrderFacade;
import kr.hhplus.be.server.order.repository.OrderProductJpaRepository;
import kr.hhplus.be.server.order.service.OrderProductService;
import kr.hhplus.be.server.order.service.OrderService;
import kr.hhplus.be.server.point.service.PointService;
import kr.hhplus.be.server.product.entity.Product;
import kr.hhplus.be.server.product.repository.ProductJpaRepository;
import kr.hhplus.be.server.product.service.ProductService;
import kr.hhplus.be.server.redis.repository.RedisRepository;
import kr.hhplus.be.server.user.entity.User;
import kr.hhplus.be.server.user.repository.UserJpaRepository;
import kr.hhplus.be.server.user.userService.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    @Autowired
    ProductService productService;
    @Autowired
    CouponService couponService;
    @Autowired
    UserService userService;
    @Autowired
    PointService pointService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderProductService orderProductService;
    @Autowired
    OrderProductJpaRepository orderProductJpaRepository;

    private User user;
    private Product product;

    @Transactional
    @Rollback
    @BeforeEach
    void setUp() {
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
        userCouponJpaRepository.deleteAll();
        productJpaRepository.deleteAll();
        orderProductJpaRepository.deleteAll();
        couponJpaRepository.deleteAll();
    }

    @Test
    void 주문테스트() {
        List<OrderProduct> orderProductList = new ArrayList<>();
        OrderProduct orderProduct = new OrderProduct(product.getId(), 1L);
        orderProductList.add(orderProduct);
        Long result = orderFacade.order(user.getId(), orderProductList, null);
        OrderResponse orderInfo = orderFacade.getOrder(result);
        assertAll("주문 확인",
            () -> assertThat(result).isNotNull()
            // () -> verify(productService, times(1)).checkStock(any()),
            // () -> verify(userCouponService, times(1)).checkCoupon(any()),
            // () -> verify(productService, times(1)).calculateOrderProductPrice(any()),
            // () -> verify(couponService, times(0)).couponDiscountRate(any()),
            // () -> verify(userService, times(1)).getUser(any()),
            // () -> verify(pointService, times(1)).usePoint(1L, any()),
            // () -> verify(orderService, times(1)).save(any()),
            // () -> verify(userCouponService, times(1)).updateUsedInfo(null, any()),
            // () -> verify(orderProductService, times(1)).saveList(any())
        );
    }

    @Test
    void 주문_쿠폰사용_테스트() {
        List<OrderProduct> orderProductList = new ArrayList<>();
        OrderProduct orderProduct = new OrderProduct(product.getId(), 1L);
        orderProductList.add(orderProduct);
        List<UserCoupon> userCouponList = userCouponService.getUserCouponList(user.getId());
        Long result = orderFacade.order(user.getId(), orderProductList, userCouponList.get(0).getId());

        assertAll("주문 확인",
                () -> assertThat(result).isNotNull()
                // () -> verify(productService, times(1)).checkStock(any()),
                // () -> verify(userCouponService, times(1)).checkCoupon(any()),
                // () -> verify(productService, times(1)).calculateOrderProductPrice(any()),
                // () -> verify(couponService, times(1)).couponDiscountRate(any()),
                // () -> verify(userService, times(1)).getUser(any()),
                // () -> verify(pointService, times(1)).usePoint(1L, any()),
                // () -> verify(orderService, times(1)).save(any()),
                // () -> verify(userCouponService, times(1)).updateUsedInfo(null, any()),
                // () -> verify(orderProductService, times(1)).saveList(any())
        );
    }

    @Test
    void 외부_플랫폼_데이터전송() {
        List<OrderProduct> orderProductList = new ArrayList<>();
        OrderProduct orderProduct = new OrderProduct(product.getId(), 1L);
        orderProductList.add(orderProduct);
        List<UserCoupon> userCouponList = userCouponService.getUserCouponList(user.getId());
        Long orderResult = orderFacade.order(user.getId(), orderProductList, userCouponList.get(0).getId());

        OrderResponse result = orderFacade.getOrder(orderResult);
        assertAll("주문 데이터",
                () -> assertThat(result.getPayment().getTotalPrice()).isEqualTo(10000),
                () -> assertThat(result.getPayment().getCouponDiscountPrice()).isEqualTo(1000),
                () -> assertThat(result.getPayment().getPaymentPrice()).isEqualTo(9000),
                () -> assertThat(result.getOrder().getStatus()).isEqualTo(OrderStatus.ORDER_COMPLETE),
                () -> assertThat(result.getUser().getId()).isEqualTo(user.getId())
        );
    }
}
