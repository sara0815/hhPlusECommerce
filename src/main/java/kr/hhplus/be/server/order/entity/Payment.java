package kr.hhplus.be.server.order.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
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
    @Schema(description = "쿠폰 할인 금액")
    long couponDiscountPrice;
    @NotNull
    @Schema(description = "최종 결제 금액")
    long paymentPrice;
    @NotNull
    @Schema(description = "생성일시")
    Date createAt;

    public Payment(long orderId, long totalPrice, long couponDiscountPrice, long paymentPrice) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.couponDiscountPrice = couponDiscountPrice;
        this.paymentPrice = paymentPrice;
    }

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
    }

}
