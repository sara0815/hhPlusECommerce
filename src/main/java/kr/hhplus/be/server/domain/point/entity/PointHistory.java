package kr.hhplus.be.server.domain.point.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PointHistory {
    @NotNull
    @Schema(description = "포인트 내역 id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
// jpa랑 매핑 (DB 테이블 1:1)

// domain 에는 충전 사용 로직같은게 같이 들어가는 /비즈니스에서 사용하는 데이터가 들어감
// ex> point

// dto 계층마다 이동할 때 데이터를 옮겨주는 용도 
// 사용하면 안되는 데이터를 막아줄 수도 있음 / 진짜 사용할 데이터를 걸러주는 필터 역할