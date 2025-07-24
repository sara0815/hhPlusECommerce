package kr.hhplus.be.server.coupon.repository;

import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.coupon.entity.Coupon;

import java.util.List;

public interface CouponRepository {
    Coupon findById(long couponId);

    List<Coupon> findAllById(List<Long> idList);
}
