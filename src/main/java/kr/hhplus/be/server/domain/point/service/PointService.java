package kr.hhplus.be.server.domain.point.service;

import kr.hhplus.be.server.domain.point.entity.PointHistory;
import kr.hhplus.be.server.domain.point.entity.Type;
import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.domain.point.repository.PointHistoryRepository;
import kr.hhplus.be.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PointService {
    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public User chargePoint(long userId, long amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."));;
        if (user == null) {
            throw new NullPointerException("해당 사용자가 존재하지 않습니다: " + userId);
        }
        user = user.chargePoint(amount);
        userRepository.save(user);
        savePointHistory(userId, amount, user.getPoint(), Type.CHARGE);
        return user;
    }

    @Transactional
    public User usePoint(long userId, long amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."));;
        if (user == null) {
            throw new NullPointerException("해당 사용자가 존재하지 않습니다: " + userId);
        }
        if (user.getPoint() < amount) {
            throw new IllegalStateException("잔액이 부족합니다. 잔액 : " + user.getPoint());
        }
        user = user.usePoint(amount);
        userRepository.save(user);
        savePointHistory(userId, amount, user.getPoint(), Type.USE);
        return user;
    }

    private void savePointHistory(long userId, long amount, long afterAmount, Type type) {
        PointHistory pointHistory = new PointHistory(userId, amount, afterAmount, type);
        pointHistoryRepository.save(pointHistory);
    }

}
