package kr.hhplus.be.server.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.coupon.entity.Coupon;
import kr.hhplus.be.server.order.entity.Order;
import kr.hhplus.be.server.order.entity.OrderProduct;
import kr.hhplus.be.server.order.entity.Payment;
import kr.hhplus.be.server.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "주문 정보")
public class OrderResponse {
    @NotNull
    @Schema(description = "주문 성공 여부")
    boolean status;

    @NotNull
    @Schema(description = "메시지")
    String msg;

    @Schema(description = "주문 정보")
    Order order;

    @Schema(description = "결제 정보")
    Payment payment;

    @Schema(description = "주문 상품 정보")
    List<OrderProduct> orderProductList;

    @NotNull
    @Schema(description = "주문 회원 정보")
    User user;

    public OrderResponse(boolean status, String msg, List<OrderProduct> orderProductList) {
        this.status = status;
        this.msg = msg;
        this.orderProductList = orderProductList;
    }
}
// 이렇게 request, response 도 entity 로 설정하는게 맞는건지??
// entity 가 이렇게 많아지는건가?