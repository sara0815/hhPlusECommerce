package kr.hhplus.be.server.domain.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import kr.hhplus.be.server.domain.order.dto.OrderRequest;
import kr.hhplus.be.server.domain.order.dto.OrderResponse;
import kr.hhplus.be.server.domain.order.facade.OrderFacade;
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
        return orderFacade.order(orderRequest);
    }

//    @Operation(summary = "결제요청")
//    @PostMapping("/payment")
//    public Long payment(@Valid @RequestBody Long orderId) {
//        return orderFacade.payment(orderId);
//    }

    @Operation(summary = "주문 정보 조회")
    @PostMapping("/{id}")
    public OrderResponse orderInfo(@Valid @RequestParam Long orderId) {
        return orderFacade.getOrder(orderId);
    }
}
