package kr.hhplus.be.server.coupon.service.integrationTest;

import kr.hhplus.be.server.coupon.dto.UserCouponResponse;
import kr.hhplus.be.server.coupon.entity.Coupon;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import kr.hhplus.be.server.coupon.repository.CouponJpaRepository;
import kr.hhplus.be.server.coupon.service.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class CouponIntegrationTest {

    @Autowired
    CouponService couponService;
    @Autowired
    CouponJpaRepository couponJpaRepository;

    @Transactional
    @Rollback
    @BeforeEach
    void setUp() {
        Date yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        Coupon coupon = new Coupon(10, 10, 0, yesterday);
        couponJpaRepository.save(coupon);
    }

    @Test
    public void 선착순_쿠폰_발급() {
        long couponId = 1L;
        long userId = 1L;
        UserCouponResponse userCoupon = couponService.issueCoupon(couponId, userId);
        assertThat(userCoupon.getCouponId()).isEqualTo(1L);
        assertThat(userCoupon.getUserId()).isEqualTo(1L);
        assertThat(userCoupon.isUsed()).isEqualTo(false);
    }
}
