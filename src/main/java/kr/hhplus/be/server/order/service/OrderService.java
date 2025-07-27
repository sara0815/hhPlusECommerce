package kr.hhplus.be.server.order.service;

import kr.hhplus.be.server.order.entity.Order;
import kr.hhplus.be.server.order.repository.OrderRepository;
import kr.hhplus.be.server.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final ProductService productService;

    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
