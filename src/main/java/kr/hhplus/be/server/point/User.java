package kr.hhplus.be.server.point;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class User{
    @NotNull
    @Schema(description = "user id")
    long id;
    @NotNull
    @Schema(description = "포인트 잔액")
    long amount;
    @Schema(description = "수정일시")
    Date updateAt;
    @NotNull
    @Schema(description = "생성일시")
    Date createAt;

    public User(long id, long amount, Date updateAt, Date createAt) {
        this.id = id;
        this.amount = amount;
        this.updateAt = updateAt;
        this.createAt = createAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
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
