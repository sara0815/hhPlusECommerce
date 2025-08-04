package kr.hhplus.be.server.order.repository;

import kr.hhplus.be.server.order.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductJpaRepository extends JpaRepository<OrderProduct, Long> {
}
