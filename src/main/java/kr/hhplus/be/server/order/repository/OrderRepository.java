package kr.hhplus.be.server.order.repository;

import kr.hhplus.be.server.order.entity.Order;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(Long orderId);
}
