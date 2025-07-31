package kr.hhplus.be.server.product.repository;

import kr.hhplus.be.server.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(long id);
    List<Product> findAll();
    List<Product> getBestProductList();
    List<Product> findAllById(List<Long> idList);
}
