package kr.hhplus.be.server.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

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

    public Order(long id, long userId, OrderStatus status, Date createAt) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.createAt = createAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
