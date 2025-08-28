package kr.hhplus.be.server.domain.coupon.repository;

import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserCouponRepositoryImpl implements UserCouponRepository {

    private final UserCouponJpaRepository userCouponJpaRepository;

    @Override
    public Optional<UserCoupon> findById(long id) {
        return userCouponJpaRepository.findById(id);
    }

    @Override
    public List<UserCoupon> findByUserID(long userId) {
        return userCouponJpaRepository.findByUserId(userId);
    }

    @Override
    public List<UserCoupon> saveAll(List<UserCoupon> userCouponList) {
        return userCouponJpaRepository.saveAll(userCouponList);
    }

    @Override
    public UserCoupon save(UserCoupon userCoupon) {
        return userCouponJpaRepository.save(userCoupon);
    }

    @Override
    public List<Long> findAllByOrderId(long orderId) {
        return userCouponJpaRepository.findAllByOrderId(orderId);
    }

    @Override
    public List<UserCoupon> findAllByCouponID(long couponId) {
        return userCouponJpaRepository.findAllByCouponId(couponId);
    }

    @Override
    public long countByCouponId(long couponId) {
        return userCouponJpaRepository.countByCouponId(couponId);
    }
}
