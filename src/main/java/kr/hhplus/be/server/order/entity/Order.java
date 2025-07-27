package kr.hhplus.be.server.order.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @NotNull
    @Schema(description = "주문 id")
    long id;
    @NotNull
    @Schema(description = "회원 id")
    long userId;
    @NotNull
    @Schema(description = "주문 상태")
    OrderStatus status;
    @NotNull
    @Schema(description = "생성일시")
    Date createAt;

    public Order(long userId, OrderStatus orderStatus) {
        this.userId = userId;
        this.status = orderStatus;
    }

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
    }
}
