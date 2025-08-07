package kr.hhplus.be.server.coupon.repository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Coupon c WHERE c.id = :couponId")
    public Optional<Coupon> findByIdWithLock(long couponId);
}
