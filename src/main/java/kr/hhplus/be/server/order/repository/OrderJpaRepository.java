package kr.hhplus.be.server.order.repository;

import kr.hhplus.be.server.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
