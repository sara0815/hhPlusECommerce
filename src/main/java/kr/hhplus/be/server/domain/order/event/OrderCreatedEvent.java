package kr.hhplus.be.server.domain.order.event;

import kr.hhplus.be.server.domain.order.dto.OrderRequest;

public record OrderCreatedEvent(OrderRequest orderRequest) {
}
