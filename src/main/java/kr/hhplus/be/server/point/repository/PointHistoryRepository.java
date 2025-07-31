package kr.hhplus.be.server.point.repository;

import kr.hhplus.be.server.point.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository  {
    PointHistory save(PointHistory pointHistory);
}
