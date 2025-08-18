package kr.hhplus.be.server.order.repository;

import kr.hhplus.be.server.order.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository {
    List<OrderProduct> saveAll(List<OrderProduct> orderProductList);

    List<OrderProduct> findAll(Long orderId);

    void deleteAll();

}
