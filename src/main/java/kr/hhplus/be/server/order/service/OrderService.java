package kr.hhplus.be.server.order.service;

import kr.hhplus.be.server.order.entity.Order;
import kr.hhplus.be.server.order.entity.OrderProduct;
import kr.hhplus.be.server.redis.repository.RedisRepository;
import kr.hhplus.be.server.order.repository.OrderRepository;
import kr.hhplus.be.server.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RedisRepository redisRepository;

    private final ProductService productService;

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order getInfo(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없습니다."));
    }

    public void saveTotalCount(List<OrderProduct> orderProductList) {
        for (OrderProduct orderProduct : orderProductList) {
            redisRepository.addProductScore(orderProduct);
        }
    }
}
