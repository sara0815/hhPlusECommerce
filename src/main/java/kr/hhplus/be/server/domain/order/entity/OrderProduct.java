package kr.hhplus.be.server.domain.order.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "주문 상품 정보")
@Entity
public class OrderProduct {

    @NotNull
    @Schema(description = "주문 상품 id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Schema(description = "생성일시")
    Date createAt;

    public OrderProduct(long productId, long count) {
        this.productId = productId;
        this.count = count;
        this.createAt = new Date();
    }

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", count=" + count +
                ", productPrice=" + productPrice +
                ", createAt=" + createAt +
                '}';
    }
}
