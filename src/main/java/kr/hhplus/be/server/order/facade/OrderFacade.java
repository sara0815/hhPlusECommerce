package kr.hhplus.be.server.order.facade;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.coupon.entity.Coupon;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import kr.hhplus.be.server.coupon.service.CouponService;
import kr.hhplus.be.server.coupon.service.UserCouponService;
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
    public OrderResponse order(long userId, List<OrderProduct> orderProductList, List<UserCoupon> userCouponList) {
        // 재고 체크
        boolean stockResult = productService.checkStock(orderProductList);
        if (!stockResult) {
            return new OrderResponse(false, "재고가 부족한 상품이 포함되어 있습니다.", orderProductList);
        }
        // 쿠폰 사용 가능 여부
        boolean couponResult = userCouponService.checkCoupon(userCouponList);
        if (!couponResult) {
            return new OrderResponse(false, "사용 불가능한 쿠폰이 있습니다.", orderProductList);
        }

        // 최종 금액 계산
        long totalPrice = productService.calculateOrderProductPrice(orderProductList);
        long couponDiscountRate = couponService.couponListDiscountRate(userCouponList);
        long discountPrice = totalPrice * (couponDiscountRate/100);
        long paymentPrice = totalPrice - discountPrice;

        User user = userService.getUser(userId);
        if (user.getPoint() < paymentPrice) {
            return new OrderResponse(false, "잔액이 부족합니다.", orderProductList);
        }
        // 포인트 사용 처리
        user = pointService.usePoint(userId, paymentPrice);

        // 주문 정보 저장
        Order order = orderService.save(new Order(userId, OrderStatus.ORDER_COMPLETE));

        // 쿠폰 사용 처리 / 사용정보 업데이트
        List<UserCoupon> usedUserCouponList = userCouponService.updateUsedInfo(userCouponList, order.getId());

        List<Long> couponIdList = usedUserCouponList.stream().map(UserCoupon::getCouponId).toList();
        List<Coupon> couponList = couponService.getList(couponIdList);

        // 결제 정보 저장
        Payment payment = paymentService.save(new Payment(order.getId(), totalPrice, discountPrice, paymentPrice));
        // 주문 상품 정보 저장
        orderProductList = orderProductService.saveList(orderProductList);

        return new OrderResponse(true, "주문 성공", order,  payment, orderProductList, couponList, user);
    }
}

// 코드 재사용성을 높일 수 있는지?
// 검증 코드가 여기저기 분포..
// todo 여기에 transactional?