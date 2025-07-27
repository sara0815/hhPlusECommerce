package kr.hhplus.be.server.user.entity;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class UserTest {

    @Test
    void chargePoint() {
        // given
        User user = new User(1L, 1000, new Date(), new Date());
        // when
        user = user.chargePoint(1000);
        // then
        AssertionsForClassTypes.assertThat(user.getPoint()).isEqualTo(2000);
    }

    @Test
    void usePoint() {
        // given
        User user = new User(1L, 1000, new Date(), new Date());
        // when
        user = user.usePoint(200);
        // then
        AssertionsForClassTypes.assertThat(user.getPoint()).isEqualTo(800);
    }
}