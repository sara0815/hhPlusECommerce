package kr.hhplus.be.server.product.integrationTest;

import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import kr.hhplus.be.server.domain.order.repository.OrderProductJpaRepository;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductJpaRepository;
import kr.hhplus.be.server.domain.product.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ProductIntegrationTest {

    @Autowired
    ProductService productService;
    @Autowired
    ProductJpaRepository productJpaRepository;
    @Autowired
    OrderProductJpaRepository orderProductJpaRepository;

    @Transactional
    @Rollback
    @BeforeEach
    void setUp() {
        Date yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        productJpaRepository.deleteAll();
        orderProductJpaRepository.deleteAll();
        productJpaRepository.save(new Product("테스트상품1", 10000, 1000));
        productJpaRepository.save(new Product("테스트상품2", 10000, 1000));
        productJpaRepository.save(new Product("테스트상품3", 10000, 1000));
        productJpaRepository.save(new Product("테스트상품4", 10000, 1000));
        productJpaRepository.save(new Product("테스트상품5", 10000, 1000));
        productJpaRepository.save(new Product("테스트상품6", 10000, 1000));
        productJpaRepository.save(new Product("테스트상품7", 10000, 1000));
        productJpaRepository.save(new Product("테스트상품8", 10000, 1000));
        productJpaRepository.save(new Product("테스트상품9", 10000, 1000));
        productJpaRepository.save(new Product("테스트상품10", 10000, 1000));
        orderProductJpaRepository.save(new OrderProduct(1L, 1L));
        orderProductJpaRepository.save(new OrderProduct(3L, 3L));
        orderProductJpaRepository.save(new OrderProduct(5L, 5L));
        orderProductJpaRepository.save(new OrderProduct(7L, 7L));
        orderProductJpaRepository.save(new OrderProduct(9L, 9L));
        List<OrderProduct> orderProductList = orderProductJpaRepository.findAll();
    }

    @AfterEach
    void cleanUp() {
        productJpaRepository.deleteAll();
        orderProductJpaRepository.deleteAll();
    }

    @Test
    void 상품리스트() {
        List<Product> productList = productService.getProductList();
        assertThat(productList).isNotEmpty();
        assertThat(productList).hasSize(10);
    }

    @Test
    void top5() {
        List<Product> best5List = productService.getBestProductList();
        assertThat(best5List).hasSize(5);
    }
}
