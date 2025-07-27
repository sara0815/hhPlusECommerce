package kr.hhplus.be.server.user.repository;

import kr.hhplus.be.server.user.entity.User;

public interface UserRepository {
    User findById(long userId);

    User selectById(long id);

    User save(User user);
}
