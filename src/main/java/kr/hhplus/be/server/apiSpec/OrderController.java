package kr.hhplus.be.server.apiSpec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import kr.hhplus.be.server.order.Order;
import kr.hhplus.be.server.order.OrderProduct;
import kr.hhplus.be.server.order.OrderStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Operation(summary = "주문생성")
    @PostMapping("/")
    public Order createOrder(@RequestBody List<@Valid OrderProduct> orderProductList) {
        return new Order(1, 1, OrderStatus.ORDER_COMPLETE, new Date());
    }

}
