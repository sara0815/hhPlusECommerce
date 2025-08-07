package kr.hhplus.be.server.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import kr.hhplus.be.server.order.entity.OrderProduct;
import kr.hhplus.be.server.order.dto.OrderRequest;
import kr.hhplus.be.server.order.dto.OrderResponse;
import kr.hhplus.be.server.order.facade.OrderFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderFacade orderFacade;

    @Operation(summary = "주문생성")
    @PostMapping("/")
    public Long createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        long userId = orderRequest.getUserId();
        List<OrderProduct> orderProductList = orderRequest.getOrderProductList();
        long orderCouponId = orderRequest.getUserCouponId();
        return orderFacade.order(userId, orderProductList, orderCouponId);
    }

    @Operation(summary = "주문 정보 외부 플랫폼 전송")
    @PostMapping("/{id}")
    public OrderResponse createOrder(@Valid @RequestParam Long orderId) {
        return orderFacade.getOrder(orderId);
    }
}
