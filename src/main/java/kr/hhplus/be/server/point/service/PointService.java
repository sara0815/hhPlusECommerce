package kr.hhplus.be.server.point.service;

import kr.hhplus.be.server.point.entity.PointHistory;
import kr.hhplus.be.server.point.entity.Type;
import kr.hhplus.be.server.user.entity.User;
import kr.hhplus.be.server.point.repository.PointHistoryRepository;
import kr.hhplus.be.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {
    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public User chargePoint(long userId, long amount) {
        User user = userRepository.selectById(userId);
        if (user == null) {
            throw new IllegalArgumentException("해당 사용자가 존재하지 않습니다: " + userId);
        }
        user = user.chargePoint(amount);
        user = userRepository.save(user);
        savePointHistory(userId, amount, user.getPoint(), Type.CHARGE);
        return user;
    }

    public User usePoint(long userId, long amount) {
        User user = userRepository.selectById(userId);
        if (user == null) {
            throw new IllegalArgumentException("해당 사용자가 존재하지 않습니다: " + userId);
        }
        user = user.usePoint(amount);
        user = userRepository.save(user);
        savePointHistory(userId, amount, user.getPoint(), Type.CHARGE);
        return user;
    }

    private void savePointHistory(long userId, long amount, long afterAmount, Type type) {
        PointHistory pointHistory = new PointHistory(userId, amount, afterAmount, type);
        pointHistoryRepository.save(pointHistory);
    }
}
