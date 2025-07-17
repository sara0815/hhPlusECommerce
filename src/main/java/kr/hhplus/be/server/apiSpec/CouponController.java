package kr.hhplus.be.server.apiSpec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.coupon.UserCoupon;
import kr.hhplus.be.server.order.Order;
import kr.hhplus.be.server.order.OrderStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        return new UserCoupon(userId, couponId, 1, false, null, new Date());
    }
}
