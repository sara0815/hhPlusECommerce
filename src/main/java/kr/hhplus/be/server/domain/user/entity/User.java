package kr.hhplus.be.server.domain.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @NotNull
    @Schema(description = "user id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @Schema(description = "포인트 잔액")
    long point;

    @Schema(description = "수정일시")
    Date updateAt;

    @NotNull
    @Schema(description = "생성일시")
    Date createAt;

    @Version
    Long version;

    public User(long point) {
        this.point = point;
        this.createAt = new Date();
    }

    public User(long id, long point, Date updateAt, Date createAt) {
        this.id = id;
        this.point = point;
        this.updateAt = updateAt;
        this.createAt = createAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
    }

    public User chargePoint(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("0 이하 값은 충전할 수 없습니다.");
        }
        this.point = this.point + amount;
        return this;
    }

    public User usePoint(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("0 이하 값은 사용할 수 없습니다.");
        }
        this.point = this.point - amount;
        return this;
    }
}
