package kr.hhplus.be.server.product.repository;

import kr.hhplus.be.server.product.entity.Product;

import java.util.List;

public interface ProductRepository {
    Product findById(long id);
    List<Product> findAll();
    List<Product> getBestProductList(); // todo best 5 repository??
    List<Product> findAllById(List<Long> idList);
}
