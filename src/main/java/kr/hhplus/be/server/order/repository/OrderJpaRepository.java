package kr.hhplus.be.server.order.repository;

import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.userId = :userId")
    List<Order> findAllByUserId(@NotNull long userId);
}
