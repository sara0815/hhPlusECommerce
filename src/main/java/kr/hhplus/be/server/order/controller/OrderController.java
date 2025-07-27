package kr.hhplus.be.server.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import kr.hhplus.be.server.order.entity.Order;
import kr.hhplus.be.server.order.entity.OrderProduct;
import kr.hhplus.be.server.order.entity.OrderRequest;
import kr.hhplus.be.server.order.entity.OrderResponse;
import kr.hhplus.be.server.order.facade.OrderFacade;
import kr.hhplus.be.server.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderFacade orderFacade;

    @Operation(summary = "주문생성")
    @PostMapping("/")
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {

        long userId = orderRequest.getUserId();
        List<OrderProduct> orderProductList = orderRequest.getOrderProductList();
        List<UserCoupon> orderCouponList = orderRequest.getUserCouponlist();
        return orderFacade.order(userId, orderProductList, orderCouponList);
    }

}
