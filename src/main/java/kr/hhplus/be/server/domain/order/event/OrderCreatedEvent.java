package kr.hhplus.be.server.domain.order.event;

import kr.hhplus.be.server.domain.order.entity.Order;

public class OrderCreatedEvent {
    private final Order order;

    public OrderCreatedEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
