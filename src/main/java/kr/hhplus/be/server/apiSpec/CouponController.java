package kr.hhplus.be.server.apiSpec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Operation(summary = "선착순 쿠폰 발급")
    @PostMapping("{couponId}")
    public UserCoupon issueCoupon(
        @Parameter(name="couponId", description="발급 받을 쿠폰 ID")
        @PathVariable long couponId,
        @Parameter(description = "회원 id")
        @RequestParam long userId
    ) {
        return new UserCoupon(userId, couponId, false, new Date());
    }
}
