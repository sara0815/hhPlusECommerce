package kr.hhplus.be.server.point.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
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

    public PointHistory(long userId, long amount, long afterAmount, Type type) {
        this.userId = userId;
        this.amount = amount;
        this.afterAmount = afterAmount;
        this.type = type;
        this.createAt = new Date();
    }

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
    }
}
