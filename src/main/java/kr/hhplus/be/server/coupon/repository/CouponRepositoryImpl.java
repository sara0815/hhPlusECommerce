package kr.hhplus.be.server.coupon.repository;

import kr.hhplus.be.server.coupon.entity.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;

    @Override
    public Optional<Coupon> findById(long couponId) {
        return couponJpaRepository.findById(couponId);
    }

    @Override
    public List<Coupon> findAllById(List<Long> idList) {
        return couponJpaRepository.findAllById(idList);
    }
}
