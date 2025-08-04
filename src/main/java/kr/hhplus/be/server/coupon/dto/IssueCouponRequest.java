package kr.hhplus.be.server.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class IssueCouponRequest {

    @NotNull
    @Schema(description = "발급 받을 쿠폰 id")
    long couponId;

    @NotNull
    @Schema(description = "발급 받을 회원 id")
    long userId;
}
