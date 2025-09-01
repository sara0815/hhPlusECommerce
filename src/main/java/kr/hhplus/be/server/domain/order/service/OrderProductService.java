package kr.hhplus.be.server.domain.order.service;

import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import kr.hhplus.be.server.domain.order.repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;

    public List<OrderProduct> saveList(List<OrderProduct> orderProductList) {
        return orderProductRepository.saveAll(orderProductList);
    }

    public List<OrderProduct> getListByOrderId(Long orderId) {
        return orderProductRepository.findAll(orderId);
    }
}
