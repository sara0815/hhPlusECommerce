package kr.hhplus.be.server.coupon.service;

import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.repository.UserCouponRepository;
import kr.hhplus.be.server.domain.coupon.service.UserCouponService;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserCouponServiceTest {

    @Mock
    UserCouponRepository userCouponRepository;
    @InjectMocks
    UserCouponService userCouponService;

    @Test
    @DisplayName("사용 가능한 쿠폰인지 체크 성공 테스트")
    void checkCoupon() {
        // given
        UserCoupon userCoupon = new UserCoupon(1L, 1L, 1L, false, 0, null, null, new Date());
        given(userCouponRepository.findById(1L)).willReturn(Optional.of(userCoupon));
        // when
        // then
        assertThatCode(() -> userCouponService.checkCoupon(1L)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("사용 가능한 쿠폰인지 체크 실패 테스트")
    void checkCouponFail() {
        // given
        UserCoupon userCoupon = new UserCoupon(1L, 1L, 1L, true, 1, new Date(), null, new Date());
        given(userCouponRepository.findById(1L)).willReturn(Optional.of(userCoupon));
        List<UserCoupon> userCouponList = new ArrayList<>();
        userCouponList.add(userCoupon);
        // when
        ;
        // then
        assertThatThrownBy(() -> userCouponService.checkCoupon(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 사용된 쿠폰입니다.");
    }

    @Test
    void updateUsedInfo() {
        // given
        UserCoupon userCoupon = new UserCoupon(1L, 1L, 1L, false, 0, new Date(), null, new Date());
        given(userCouponRepository.findById(1L)).willReturn(Optional.of(userCoupon));
        given(userCouponRepository.save(userCoupon)).willReturn(userCoupon);
        // when
        UserCoupon result = userCouponService.updateUsedInfo(1L, 123);
        // then
        assertThat(result.getCouponId()).isEqualTo(1);
        assertThat(result.getUserId()).isEqualTo(1);
        assertThat(result.isUsed()).isEqualTo(true);
        verify(userCouponRepository, times(1)).save(any());
    }
}