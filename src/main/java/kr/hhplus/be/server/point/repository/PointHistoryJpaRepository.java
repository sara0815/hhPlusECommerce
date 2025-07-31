package kr.hhplus.be.server.point.repository;

import kr.hhplus.be.server.point.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryJpaRepository extends JpaRepository<PointHistory, Long> {
}
