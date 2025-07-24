package kr.hhplus.be.server.product.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @NotNull
    @Schema(description = "상품 id")
    long id;
    @NotNull
    @Schema(description = "상품명")
    String name;
    @NotNull
    @Schema(description = "상품 가격")
    long price;
    @NotNull
    @Schema(description = "재고")
    long stock;
    @Schema(description = "수정일시")
    Date updateAt;
    @NotNull
    @Schema(description = "생성일시")
    Date createAt;
}

