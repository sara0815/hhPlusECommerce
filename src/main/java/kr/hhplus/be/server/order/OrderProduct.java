package kr.hhplus.be.server.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "주문 상품 정보")
public class OrderProduct {
    @NotNull
    @Schema(description = "주문 상품 id")
    long id;
    @NotNull
    @Schema(description = "주문 id")
    long orderId;
    @NotNull
    @Schema(description = "상품 id")
    long productId;
    @NotNull
    @Schema(description = "주문 수량")
    long count;
    @NotNull
    @Schema(description = "상품 가격")
    long productPrice;

    public OrderProduct(long id, long orderId, long productId, long count, long productPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.count = count;
        this.productPrice = productPrice;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(long productPrice) {
        this.productPrice = productPrice;
    }

}
