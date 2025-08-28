package kr.hhplus.be.server.point.service;

import kr.hhplus.be.server.domain.point.repository.PointHistoryRepository;
import kr.hhplus.be.server.domain.point.service.PointService;
import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PointHistoryRepository pointHistoryRepository;

    @InjectMocks
    PointService pointService;

    @Test
    void chargePoint() {
        // given
        given(userRepository.findById(1L)).willReturn(Optional.of(new User(1L, 1000, null, new Date())));
        // when
        User result = pointService.chargePoint(1L, 1000);
        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getPoint()).isEqualTo(2000);
    }

    @Test
    void usePoint() {
        // given
        given(userRepository.findById(1L)).willReturn(Optional.of(new User(1L, 1000, null, new Date())));
        // when
        User result = pointService.usePoint(1L, 500);
        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getPoint()).isEqualTo(500);
    }
}