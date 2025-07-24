package kr.hhplus.be.server.coupon.service;

import kr.hhplus.be.server.coupon.entity.Coupon;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import kr.hhplus.be.server.coupon.entity.UserCouponResponse;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
        given(couponRepository.findById(1L)).willReturn(new Coupon(1L, 10, 10, 10, new Date(), null, new Date()));
        given(couponRepository.findById(2L)).willReturn(new Coupon(2L, 20, 10, 10, new Date(), null, new Date()));
        List<UserCoupon> userCouponList = new ArrayList<>();
        userCouponList.add(new UserCoupon(1L, 1L));
        userCouponList.add(new UserCoupon(1L, 2L));
        // when
        Long discountRate = couponService.couponListDiscountRate(userCouponList);
        // then
        assertThat(discountRate).isEqualTo(28);
    }

    @Test
    @DisplayName("쿠폰 발급 성공 테스트")
    void issueCoupon() {
        // given
        Date yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        Date now = new Date();
        UserCoupon userCoupon = new UserCoupon(1L, 1L, 1L, false, 0, null, null, now);
        UserCouponResponse expected = new UserCouponResponse(true, userCoupon, "발급 성공");
        given(couponRepository.findById(1L)).willReturn(new Coupon(1L, 10, 10, 10, yesterday, null, new Date()));
        given(userCouponRepository.save(new UserCoupon(1, 1))).willReturn(userCoupon);
        // when
        UserCouponResponse result = couponService.issueCoupon(1, 1);
        System.out.println("????" + result.toString());
        System.out.println(expected.toString());
        // then
        assertThat(result).isEqualTo(expected);
    }
}