package kr.hhplus.be.server.coupon.repository;

import kr.hhplus.be.server.coupon.entity.UserCoupon;

import java.util.List;
import java.util.Optional;

public interface UserCouponRepository {
    Optional<UserCoupon> findById(long id);

    List<UserCoupon> findByUserID(long userId);

    List<UserCoupon> saveAll(List<UserCoupon> userCouponList);

    UserCoupon save(UserCoupon userCoupon);

    List<Long> findAllByOrderId(Long orderId);
}
