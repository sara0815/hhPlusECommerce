package kr.hhplus.be.server.domain.coupon.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Coupon {
    @Schema(description = "쿠폰 id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @Min(value = 1)
    @Max(value = 99)
    @Schema(description = "할인 비율 [%]")
    long discountRate;

    @NotNull
    @Min(value = 1)
    @Schema(description = "총 발급 가능 수량")
    long totalCount;

    @NotNull
    @Min(value = 0)
    @Schema(description = "발급된 수량")
    long issuedCount;

    @Schema(description = "발급 시작 일시")
    Date issueStartDatetime;

    @Schema(description = "수정일시")
    Date updateAt;

    @Schema(description = "생성일시")
    Date createAt;

    public Coupon(long discountRate, long totalCount, long issuedCount, Date issueStartDatetime, Date updateAt, Date createAt) {
        this.discountRate = discountRate;
        this.totalCount = totalCount;
        this.issuedCount = issuedCount;
        this.issueStartDatetime = issueStartDatetime;
        this.updateAt = updateAt;
        this.createAt = createAt;
    }

    public Coupon(long discountRate, long totalCount, long issuedCount, Date issueStartDatetime) {
        this.discountRate = discountRate;
        this.totalCount = totalCount;
        this.issuedCount = issuedCount;
        this.issueStartDatetime = issueStartDatetime;
    }

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
    }
}
