package kr.hhplus.be.server.domain.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserCouponResponse {
    @NotNull
    @Schema(description = "회원 발급 쿠폰 id")
    long id;
    
    @NotNull
    @Schema(description = "회원 id")
    long userId;
    
    @NotNull
    @Schema(description = "쿠폰 id")
    long couponId;
    
    @NotNull
    @Schema(description = "사용 여부")
    boolean used;
    
    @NotNull
    @Schema(description = "생성일시")
    Date createAt;

}
