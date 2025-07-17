package kr.hhplus.be.server.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class Payment {
    @NotNull
    @Schema(description = "결제 id")
    long id;
    @NotNull
    @Schema(description = "주문 id")
    long orderId;
    @NotNull
    @Schema(description = "할인 전 총 금액")
    long totalPrice;
    @NotNull
    @Schema(description = "사용 쿠폰 id")
    long userCouponId;
    @NotNull
    @Schema(description = "쿠폰 할인 금액")
    long couponDiscountPrice;
    @NotNull
    @Schema(description = "최종 결제 금액")
    long paymentPrice;
    @NotNull
    @Schema(description = "생성일시")
    Date createAt;

    public Payment(long id, long orderId, long totalPrice, long userCouponId, long couponDiscountPrice, long paymentPrice, Date createAt) {
        this.id = id;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.userCouponId = userCouponId;
        this.couponDiscountPrice = couponDiscountPrice;
        this.paymentPrice = paymentPrice;
        this.createAt = createAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(long userCouponId) {
        this.userCouponId = userCouponId;
    }

    public long getCouponDiscountPrice() {
        return couponDiscountPrice;
    }

    public void setCouponDiscountPrice(long couponDiscountPrice) {
        this.couponDiscountPrice = couponDiscountPrice;
    }

    public long getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(long paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
