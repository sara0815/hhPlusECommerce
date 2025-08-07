package kr.hhplus.be.server.product.repository;

import kr.hhplus.be.server.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Optional<Product> findById(long id) {
        return productJpaRepository.findById(id);
    }

    @Override
    public Optional<Product> findByIdWithLock(long id) {
        return productJpaRepository.findByIdWithLock(id);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }

    @Override
    public List<Product> getBestProductList() {
        return productJpaRepository.getBestProductList();
    }

    @Override
    public List<Product> findAllById(List<Long> idList) {
        return productJpaRepository.findAllById(idList);
    }

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

}
