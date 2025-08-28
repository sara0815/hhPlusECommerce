package kr.hhplus.be.server.domain.order.repository;

import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderProductRepositoryImpl implements OrderProductRepository {
    @Override
    public List<OrderProduct> saveAll(List<OrderProduct> orderProductList) {
        return List.of();
    }

    @Override
    public List<OrderProduct> findAll(Long orderId) {
        return List.of();
    }

    @Override
    public void deleteAll() {}
}
