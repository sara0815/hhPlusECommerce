package kr.hhplus.be.server.user.repository;

import kr.hhplus.be.server.user.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(long userId);

    User save(User user);
}
