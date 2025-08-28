package kr.hhplus.be.server.domain.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import lombok.*;

import java.util.List;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotNull
    @Schema(description = "주문 상품 리스트")
    private List<OrderProduct> orderProductList; // final 불변

    @Schema(description = "사용 쿠폰")
    private Long userCouponId;

    @NotNull
    @Schema(description = "주문 회원 id")
    private long userId;
}
