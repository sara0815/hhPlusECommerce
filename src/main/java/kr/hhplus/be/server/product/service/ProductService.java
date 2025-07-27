package kr.hhplus.be.server.product.service;

import kr.hhplus.be.server.order.entity.OrderProduct;
import kr.hhplus.be.server.product.entity.Product;
import kr.hhplus.be.server.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product getProduct(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    public List<Product> getBestProductList() {
        return productRepository.getBestProductList();
    }

    public List<Product> getOrderProductList(List<OrderProduct> orderProductList) {
        List<Long> productIdList = orderProductList.stream()
            .map(OrderProduct::getProductId)
            .collect(Collectors.toList());
        return productRepository.findAllById(productIdList);
    }

    public boolean checkStock(List<OrderProduct> orderProductList) {
        for (OrderProduct orderProduct : orderProductList) {
            long productId = orderProduct.getProductId();
            Product product = getProduct(productId);
            if (product.getStock() - orderProduct.getCount() < 0) {
                return false;
            }
        }
        return true;
    }

    public long calculateOrderProductPrice(List<OrderProduct> orderProductList) {
        long totalPrice = 0;
        for (OrderProduct orderProduct : orderProductList) {
            Product product = productRepository.findById(orderProduct.getProductId());
            long productPrice = product.getPrice() * orderProduct.getCount();
            totalPrice += productPrice;
        }
        return totalPrice;
    }
}
