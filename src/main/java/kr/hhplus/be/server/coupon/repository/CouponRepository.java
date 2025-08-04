package kr.hhplus.be.server.coupon.repository;

import kr.hhplus.be.server.coupon.entity.Coupon;

import java.util.List;
import java.util.Optional;

public interface CouponRepository {
    Optional<Coupon> findById(long couponId);

    List<Coupon> findAllById(List<Long> idList);
}
