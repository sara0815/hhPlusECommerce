package kr.hhplus.be.server.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

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

    public Product(long id, String name, long price, long stock, Date updateAt, Date createAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.updateAt = updateAt;
        this.createAt = createAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
