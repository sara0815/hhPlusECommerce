package kr.hhplus.be.server.order.repository;

import kr.hhplus.be.server.order.entity.OrderProduct;

import java.util.List;

public interface OrderProductRepository {
    List<OrderProduct> saveAll(List<OrderProduct> orderProductList);
}
