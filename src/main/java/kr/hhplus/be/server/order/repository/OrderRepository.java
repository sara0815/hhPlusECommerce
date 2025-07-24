package kr.hhplus.be.server.order.repository;

import kr.hhplus.be.server.order.entity.Order;

public interface OrderRepository {
    Order save(Order order);

}
