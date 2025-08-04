package kr.hhplus.be.server.coupon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import kr.hhplus.be.server.coupon.service.UserCouponService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RestController
@RequestMapping("/user-coupon")
public class UserCouponController {

    private final UserCouponService userCouponService;

    @Operation(summary = "회원 쿠폰 목록 조회")
    @GetMapping("/list/{userId}")
    public List<UserCoupon> getUserCouponList(@Valid @Parameter(name="userId", description = "회원 id") @PathVariable long userId) {
        return userCouponService.getUserCouponList(userId);
    }

}
