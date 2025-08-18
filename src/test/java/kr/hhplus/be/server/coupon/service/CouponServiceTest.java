package kr.hhplus.be.server.coupon.service;

import kr.hhplus.be.server.coupon.entity.Coupon;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import kr.hhplus.be.server.coupon.dto.UserCouponResponse;
import kr.hhplus.be.server.coupon.repository.CouponRepository;
import kr.hhplus.be.server.coupon.repository.UserCouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    CouponRepository couponRepository;
    @Mock
    UserCouponRepository userCouponRepository;

    @InjectMocks
    CouponService couponService;

    @Test
    @DisplayName("할인율 계산")
    void couponListDiscountRate() {
        // given
        given(couponRepository.findById(1L)).willReturn(Optional.of(new Coupon(1L, 10, 10, 10, new Date(), null, new Date())));
        given(userCouponRepository.findById(1L)).willReturn(Optional.of(new UserCoupon(1L, 10, 1L, false, null, null, new Date())));
        // when
        Long discountRate = couponService.couponDiscountRate(1L);
        // then
        assertThat(discountRate).isEqualTo(10);
    }

    @Test
    @DisplayName("쿠폰 발급 성공 테스트")
    void issueCoupon() {
        // given
        Date yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        Date now = new Date();
        UserCoupon userCoupon = new UserCoupon(1L, 1L, 1L, false, null, null, now);
        given(couponRepository.findByIdWithLock(1L)).willReturn(Optional.of(new Coupon(1L, 10, 10, 0, yesterday, null, new Date())));
        given(userCouponRepository.save(any(UserCoupon.class))).willReturn(userCoupon);
        // when
        UserCouponResponse result = couponService.issueCoupon(1, 1);
        // then

        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getCouponId()).isEqualTo(1L);
        assertThat(result.isUsed()).isEqualTo(false);
    }
}