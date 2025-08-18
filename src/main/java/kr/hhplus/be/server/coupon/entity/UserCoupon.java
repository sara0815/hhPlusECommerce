package kr.hhplus.be.server.coupon.entity;

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
public class UserCoupon {
    @NotNull
    @Schema(description = "회원 발급 쿠폰 id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @Schema(description = "회원 id")
    long userId;

    @NotNull
    @Schema(description = "쿠폰 id")
    long couponId;

    @NotNull
    @Schema(description = "사용 여부")
    boolean used;

    @Schema(description = "사용일시")
    Date usedAt;

    @Schema(description = "수정일시")
    Date updateAt;

    @NotNull
    @Schema(description = "생성일시")
    Date createAt;

    public UserCoupon(long userId, long couponId) {
        this.userId = userId;
        this.couponId = couponId;
        this.used = false;
    }

    public UserCoupon(long userId, long couponId, boolean used, Date createAt) {
        this.userId = userId;
        this.couponId = couponId;
        this.used = used;
        this.createAt = createAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
    }

}


