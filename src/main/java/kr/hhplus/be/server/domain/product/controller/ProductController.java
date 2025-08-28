package kr.hhplus.be.server.domain.product.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 조회")
    @GetMapping("/{id}")
    public Product getProduct(@Valid @Parameter(name="id", description = "상품 id") @PathVariable long id) {
        return productService.getProduct(id);
    }

    @Operation(summary = "상품 리스트")
    @GetMapping("/")
    public List<Product> getProductList() {
        return productService.getProductList();
    }

    @Operation(summary = "best top5 상품 조회", description = "3일간 top5 베스트 상품 조회")
    @GetMapping("/best")
    public List<Product> getBestProduct() {
        return productService.getBestProductList();
    }
}
