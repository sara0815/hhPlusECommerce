package kr.hhplus.be.server.product.service;

import kr.hhplus.be.server.order.entity.OrderProduct;
import kr.hhplus.be.server.redis.repository.RedisRepository;
import kr.hhplus.be.server.product.entity.Product;
import kr.hhplus.be.server.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final RedisRepository redisRepository;

    @Cacheable(key="#id", value="product")
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "상품이 존재하지 않습니다."));
    }

    public List<Product> getProductList() {
        return productRepository.findAll();
    }

//    @Cacheable(key="'BEST_PRODUCT_LIST'", value="BEST_PRODUCT_LIST")
    public List<Product> getBestProductList() {
        // return productRepository.getBestProductList();
        Set<Long> productIdSets = redisRepository.findAllProductsOrderByScoreDesc();
        List<Long> productIdList = new ArrayList<>(productIdSets);
        List<Product> productList = productRepository.findAllById(productIdList);
//        Map<Long, Product> productMap = productList.stream()
//                .collect(Collectors.toMap(Product::getId, p -> p));
//
//        return productIdList.stream()
//                .map(productMap::get)
//                .toList();
        return productList;
    }

    public List<Product> getOrderProductList(List<OrderProduct> orderProductList) {
        List<Long> productIdList = orderProductList.stream()
            .map(OrderProduct::getProductId)
            .collect(Collectors.toList());
        return productRepository.findAllById(productIdList);
    }

    public void checkStock(List<OrderProduct> orderProductList) {
        for (OrderProduct orderProduct : orderProductList) {
            long productId = orderProduct.getProductId();
            Product product = getProduct(productId);
            if (product.getStock() - orderProduct.getCount() < 0) {
               throw new IllegalStateException("재고가 부족합니다.");
            }
        }
    }

    public long calculateOrderProductPrice(List<OrderProduct> orderProductList) {
        long totalPrice = 0;
        for (OrderProduct orderProduct : orderProductList) {
            Product product = productRepository.findById(orderProduct.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."));
            long productPrice = product.getPrice() * orderProduct.getCount();
            totalPrice += productPrice;
        }
        return totalPrice;
    }


//    @DistributedLock(key="#product")
    @Transactional
    public void updateStock(List<OrderProduct> orderProductList) {
        for (OrderProduct orderProduct : orderProductList) {
            Product product = productRepository.findById(orderProduct.getProductId()).orElseThrow();
            long stock = product.getStock() - orderProduct.getCount();
            if (stock < 0) {
                throw new IllegalStateException("재고가 부족합니다.");
            }
            product.setStock(stock);
            // productRepository.save(product);
        }
    }

    public void mergeRankingScore() {
        redisRepository.mergeProductRankingScore();
    }
}
