package kr.hhplus.be.server.user.userService;

import kr.hhplus.be.server.user.entity.User;
import kr.hhplus.be.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(long userId) {
        return userRepository.findById(userId);
    }
}
