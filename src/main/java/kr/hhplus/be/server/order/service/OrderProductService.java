package kr.hhplus.be.server.order.service;

import jakarta.validation.Valid;
import kr.hhplus.be.server.order.entity.OrderProduct;
import kr.hhplus.be.server.order.repository.OrderProductRepository;
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
