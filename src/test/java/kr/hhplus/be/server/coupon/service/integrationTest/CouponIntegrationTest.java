package kr.hhplus.be.server.coupon.service.integrationTest;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponJpaRepository;
import kr.hhplus.be.server.domain.coupon.service.CouponService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    private Coupon coupon;

    @Transactional
    @Rollback
    @BeforeEach
    void setUp() {
        Date yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        coupon = new Coupon(10, 10, 0, yesterday);
        coupon = couponJpaRepository.save(coupon);
    }

    @AfterEach
    void cleanUp() {
        couponJpaRepository.deleteAll();
    }

    /*
    @Test
    public void 선착순_쿠폰_발급() {
        long couponId = coupon.getId();
        long userId = 1L;
        UserCouponResponse userCoupon = couponService.issueCoupon(couponId, userId);
        assertThat(userCoupon.getCouponId()).isEqualTo(couponId);
        assertThat(userCoupon.getUserId()).isEqualTo(userId);
        assertThat(userCoupon.isUsed()).isEqualTo(false);
        Coupon coupon = couponJpaRepository.findById(couponId).orElseThrow();
        assertThat(coupon.getIssuedCount()).isEqualTo(1L);
    }
     */
}
