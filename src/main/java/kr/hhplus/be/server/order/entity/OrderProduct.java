package kr.hhplus.be.server.order.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "주문 상품 정보")
public class OrderProduct {

    @NotNull
    @Schema(description = "주문 상품 id")
    long id;

    @Schema(description = "주문 id")
    long orderId;

    @NotNull
    @Schema(description = "상품 id")
    long productId;

    @NotNull
    @Schema(description = "주문 수량")
    long count;

    @Schema(description = "상품 가격")
    long productPrice;

    public OrderProduct(long id, long productId, int count) {
        this.id = id;
        this.productId = productId;
        this.count = count;
    }
}
