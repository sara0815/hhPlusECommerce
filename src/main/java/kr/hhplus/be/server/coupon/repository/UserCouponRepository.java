package kr.hhplus.be.server.coupon.repository;

import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.coupon.entity.UserCoupon;

import java.util.List;

public interface UserCouponRepository {
    UserCoupon findById(long id);

    UserCoupon findByUserID(long userId);

    List<UserCoupon> saveAll(List<UserCoupon> userCouponList);

    UserCoupon save(UserCoupon userCoupon);

}
