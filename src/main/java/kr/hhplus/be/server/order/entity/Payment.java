package kr.hhplus.be.server.order.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Payment {
    @NotNull
    @Schema(description = "결제 id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @Schema(description = "주문 id")
    long orderId;

    @NotNull
    @Schema(description = "할인 전 총 금액")
    long totalPrice;

    @Schema(description = "사용한 발급된 쿠폰")
    Long userCouponId;

    @Schema(description = "쿠폰 할인 금액")
    Long couponDiscountPrice;

    @NotNull
    @Schema(description = "최종 결제 금액")
    long paymentPrice;

    @NotNull
    @Schema(description = "생성일시")
    Date createAt;

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
    }

}
