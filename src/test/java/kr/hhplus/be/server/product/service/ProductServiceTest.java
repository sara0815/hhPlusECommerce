package kr.hhplus.be.server.product.service;

import kr.hhplus.be.server.order.entity.OrderProduct;
import kr.hhplus.be.server.product.entity.Product;
import kr.hhplus.be.server.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductService productService;

    @Test
    @DisplayName("재고 확인 성공 테스트")
    void checkStock() {
        // given
        List<OrderProduct> orderProductList = new ArrayList<>();
        orderProductList.add(new OrderProduct(1L, 1));
        orderProductList.add(new OrderProduct(2L, 2));

        given(productRepository.findById(1L)).willReturn(Optional.of(new Product(1L, "테스트 상품", 1000, 1000, null, new Date())));
        given(productRepository.findById(2L)).willReturn(Optional.of(new Product(2L, "테스트 상품2", 2000, 1000, null, new Date())));

        // when
        assertThatCode(() -> productService.checkStock(orderProductList)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("재고 확인 실패 테스트")
    void checkStockFail() {
        // given
        List<OrderProduct> orderProductList = new ArrayList<>();
        orderProductList.add(new OrderProduct(2L, 2));
        orderProductList.add(new OrderProduct(1L, 2));
        given(productRepository.findById(1)).willReturn(Optional.of(new Product(1L, "테스트 상품", 1000, 1, null, new Date())));
        given(productRepository.findById(2)).willReturn(Optional.of(new Product(2L, "테스트 상품2", 2000, 1000, null, new Date())));

        // then
        assertThatThrownBy(() -> productService.checkStock(orderProductList))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("재고가 부족합니다.");
    }

    @Test
    @DisplayName("주문 상품 금액 계산 테스트")
    void calculateOrderProductPrice() {
        // given
        List<OrderProduct> orderProductList = new ArrayList<>();
        orderProductList.add(new OrderProduct(1L, 1));
        orderProductList.add(new OrderProduct(2L, 2));
        given(productRepository.findById(1)).willReturn(Optional.of(new Product(1L, "테스트 상품", 1000, 1, null, new Date())));
        given(productRepository.findById(2)).willReturn(Optional.of(new Product(1L, "테스트 상품2", 2000, 1000, null, new Date())));
        // when
        long price = productService.calculateOrderProductPrice(orderProductList);
        // then
        assertThat(price).isEqualTo(5000);
    }
}