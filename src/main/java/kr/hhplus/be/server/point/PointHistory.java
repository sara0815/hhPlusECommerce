package kr.hhplus.be.server.point;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class PointHistory {
    @NotNull
    @Schema(description = "포인트 내역 id")
    long id;
    @NotNull
    @Schema(description = "회원 id")
    long userId;
    @NotNull
    @Schema(description = "충전/사용 금액")
    long amount;
    @NotNull
    @Schema(description = "충전/사용 후 잔액")
    long afterAmount;
    @NotNull
    @Schema(description = "충전-CHARGE/사용-USE")
    Type type;
    @NotNull
    @Schema(description = "생성일시")
    Date createAt;

    public PointHistory(long id, long userId, long amount, long afterAmount, Type type, Date createAt) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.afterAmount = afterAmount;
        this.type = type;
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

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getAfterAmount() {
        return afterAmount;
    }

    public void setAfterAmount(long afterAmount) {
        this.afterAmount = afterAmount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
