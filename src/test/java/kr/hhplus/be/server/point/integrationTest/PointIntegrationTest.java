package kr.hhplus.be.server.point.integrationTest;

import kr.hhplus.be.server.point.repository.PointHistoryRepository;
import kr.hhplus.be.server.point.service.PointService;
import kr.hhplus.be.server.user.entity.User;
import kr.hhplus.be.server.user.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class PointIntegrationTest {

    @Autowired
    PointService pointService;
    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    PointHistoryRepository pointHistoryRepository;

    @Transactional
    @Rollback
    @BeforeEach
    public void setUp() {
        User user = new User(10000);
        userJpaRepository.save(user);
    }

    @Test
    public void 포인트_충전() {
        User result = pointService.chargePoint(1L, 10000);
        assertThat(result.getPoint()).isEqualTo(20000);
//        verify(pointHistoryRepository).save();
    }

    @Test
    public void 포인트_사용() {
        User result = pointService.usePoint(1L, 4000);
        assertThat(result.getPoint()).isEqualTo(6000);
    }
}
