package kr.hhplus.be.server.order.facade;

import jakarta.validation.Valid;
import kr.hhplus.be.server.coupon.entity.Coupon;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import kr.hhplus.be.server.coupon.service.CouponService;
import kr.hhplus.be.server.coupon.service.UserCouponService;
import kr.hhplus.be.server.order.dto.OrderResponse;
import kr.hhplus.be.server.order.entity.*;
import kr.hhplus.be.server.order.service.OrderProductService;
import kr.hhplus.be.server.order.service.OrderService;
import kr.hhplus.be.server.order.service.PaymentService;
import kr.hhplus.be.server.point.service.PointService;
import kr.hhplus.be.server.product.service.ProductService;
import kr.hhplus.be.server.user.entity.User;
import kr.hhplus.be.server.user.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderFacade {
    private final OrderService orderService;
    private final UserService userService;
    private final UserCouponService userCouponService;
    private final ProductService productService;
    private final CouponService couponService;
    private final PointService pointService;
    private final OrderProductService orderProductService;
    private final PaymentService paymentService;

    @Transactional
    public Long order(long userId, List<OrderProduct> orderProductList, Long userCouponId) {
        // 재고 체크
        productService.checkStock(orderProductList);
        // 쿠폰 사용 가능 여부
        if (userCouponId != null) {
            userCouponService.checkCoupon(userCouponId);
        }

        // 최종 금액 계산
        long totalPrice = productService.calculateOrderProductPrice(orderProductList);
        long couponDiscountRate = 0;
        long discountPrice = 0;
        if (userCouponId != null) {
            couponDiscountRate = couponService.couponDiscountRate(userCouponId);
            discountPrice = (long)(totalPrice * (couponDiscountRate/100.0));
        }
        long paymentPrice = totalPrice - discountPrice;

        // 포인트 사용 처리
        pointService.usePoint(userId, paymentPrice);

        // 주문 정보 저장
        Order order = orderService.save(new Order(userId, OrderStatus.ORDER_COMPLETE));

        // 쿠폰 사용 처리 / 사용정보 업데이트
        userCouponService.updateUsedInfo(userCouponId, order.getId());

        // 결제 정보 저장
        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setTotalPrice(totalPrice);
        payment.setUserCouponId(userCouponId);
        payment.setCouponDiscountPrice(discountPrice);
        payment.setPaymentPrice(paymentPrice);
        paymentService.save(payment);
        // 주문 상품 정보 저장
        orderProductService.saveList(orderProductList);

        return order.getId();
    }

    public OrderResponse getOrder(@Valid Long orderId) {
        Order order = orderService.getInfo(orderId);
        List<OrderProduct> orderProductList = orderProductService.getListByOrderId(orderId);
        Payment payment = paymentService.getInfoByOrderId(orderId);
        User user = userService.getUser(order.getUserId());
        return new OrderResponse(true, "주문 성공", order,  payment, orderProductList, user);
    }
}
