package kr.hhplus.be.server.coupon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import kr.hhplus.be.server.coupon.entity.UserCouponResponse;
import kr.hhplus.be.server.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    @Operation(summary = "선착순 쿠폰 발급")
    @PostMapping("{couponId}")
    public UserCouponResponse issueCoupon(
        @Parameter(name="couponId", description="발급 받을 쿠폰 ID")
        @PathVariable long couponId,
        @Parameter(description = "회원 id")
        @RequestParam long userId
    ) {
        return couponService.issueCoupon(couponId, userId);
    }
}

// todo 유효성 검증?
