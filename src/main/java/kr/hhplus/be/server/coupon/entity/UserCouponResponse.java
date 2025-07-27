package kr.hhplus.be.server.coupon.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserCouponResponse {
    @NotNull
    boolean status;
    UserCoupon userCoupon;
    String msg;

    @Override
    public String toString() {
        return "UserCouponResponse{" +
                "status=" + status +
                ", userCoupon=" + userCoupon +
                ", msg='" + msg + '\'' +
                '}';
    }
}
