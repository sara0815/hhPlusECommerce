package kr.hhplus.be.server.domain.product.repository;

import kr.hhplus.be.server.domain.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(long id);
    Optional<Product> findByIdWithLock(long id);
    List<Product> findAll();
    List<Product> getBestProductList();
    List<Product> findAllById(List<Long> idList);

    Product save(Product product);
}
