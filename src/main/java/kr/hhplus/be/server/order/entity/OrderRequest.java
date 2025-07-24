package kr.hhplus.be.server.order.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {
    @NotNull
    @Schema(description = "주문 상품 리스트")
    List<OrderProduct> orderProductList;
    @Schema(description = "사용 쿠폰 리스트")
    List<UserCoupon> userCouponlist;
    @NotNull
    @Schema(description = "주문 회원 id")
    long userId;
}
