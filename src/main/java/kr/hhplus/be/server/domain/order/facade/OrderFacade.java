package kr.hhplus.be.server.domain.order.facade;

import jakarta.validation.Valid;
import kr.hhplus.be.server.common.aop.distributedLock.DistributedLock;
import kr.hhplus.be.server.domain.coupon.service.CouponService;
import kr.hhplus.be.server.domain.coupon.service.UserCouponService;
import kr.hhplus.be.server.domain.order.dto.OrderRequest;
import kr.hhplus.be.server.domain.order.dto.OrderResponse;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import kr.hhplus.be.server.domain.order.entity.OrderStatus;
import kr.hhplus.be.server.domain.order.entity.Payment;
import kr.hhplus.be.server.domain.order.event.OrderCreatedEvent;
import kr.hhplus.be.server.domain.order.service.OrderProductService;
import kr.hhplus.be.server.domain.order.service.OrderService;
import kr.hhplus.be.server.domain.order.service.PaymentService;
import kr.hhplus.be.server.domain.point.service.PointService;
import kr.hhplus.be.server.domain.product.service.ProductService;
import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.domain.user.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

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

    private final ApplicationEventPublisher eventPublisher;

    @DistributedLock(key="#orderRequest.orderProductList.![productId]")
    public Long order(OrderRequest orderRequest) {
        long userId = orderRequest.getUserId();
        Long userCouponId = orderRequest.getUserCouponId();
        List<OrderProduct> orderProductList = orderRequest.getOrderProductList();
        // 재고 체크
        productService.checkStock(orderProductList);
        // 쿠폰 사용 가능 여부
        if (userCouponId != null) {
            userCouponService.checkCoupon(userCouponId);
        }

        // 재고 차감 처리
        productService.updateStock(orderProductList);

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

        orderService.saveTotalCount(orderProductList);

        // 데이터플랫폼 전송 함수
        // dataflatformService.send(orderId)
        eventPublisher.publishEvent(new OrderCreatedEvent(orderRequest));

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
