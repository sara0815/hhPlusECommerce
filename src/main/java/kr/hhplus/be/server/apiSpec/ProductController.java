package kr.hhplus.be.server.apiSpec;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.product.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Operation(summary = "상품 조회")
    @GetMapping("/{id}")
    public Product getProduct(
            @Parameter(name="id", description = "상품 id")
            @PathVariable long id
    ) {
        return new Product(id, "상품1", 10000, 1000, null, new Date());
    }

    @Operation(summary = "best top5 상품 조회", description = "3일간 top5 베스트 상품 조회")
    @GetMapping("/best")
    public List<Product> getBestProduct(@PathVariable long id) {
        List<Product> productList = new ArrayList<>();
        Product product = new Product(id, "상품1", 10000, 1000, null, new Date());
        productList.add(product);
        return productList;
    }
}
