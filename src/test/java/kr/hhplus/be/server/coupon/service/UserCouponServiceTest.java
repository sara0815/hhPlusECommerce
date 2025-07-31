package kr.hhplus.be.server.coupon.service;

import kr.hhplus.be.server.coupon.entity.UserCoupon;
import kr.hhplus.be.server.coupon.repository.UserCouponRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
        List<UserCoupon> userCouponList = new ArrayList<>();
        userCouponList.add(userCoupon);
        // when
        boolean result = userCouponService.checkCoupon(userCouponList);
        // then
        assertThat(result).isEqualTo(true);
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
        boolean result = userCouponService.checkCoupon(userCouponList);
        // then
        assertThat(result).isEqualTo(false);
    }
    @Test
    void updateUsedInfo() {
        // given
        List<UserCoupon> userCouponList = new ArrayList<>();
        UserCoupon userCoupon = new UserCoupon(1L, 1L);
        userCouponList.add(userCoupon);
        // when
        List<UserCoupon> result = userCouponService.updateUsedInfo(userCouponList, 123);
        // then
        assertThat(userCouponList.get(0).getCouponId()).isEqualTo(1);
        assertThat(userCouponList.get(0).getUserId()).isEqualTo(1);
        assertThat(userCouponList.get(0).isUsed()).isEqualTo(true);
        verify(userCouponRepository, times(1)).saveAll(any());
    }
}