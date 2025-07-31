//package kr.hhplus.be.server.apiSpec;
//
//import io.swagger.v3.oas.annotations.Operation;
//import jakarta.validation.Valid;
//import kr.hhplus.be.server.order.entity.Order;
//import kr.hhplus.be.server.order.entity.OrderProduct;
//import kr.hhplus.be.server.order.entity.OrderStatus;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Date;
//import java.util.List;
//
//@RestController
//@RequestMapping("/order")
//public class OrderController {
//
//    @Operation(summary = "주문생성")
//    @PostMapping("/")
//    public Order createOrder(@RequestBody List<@Valid OrderProduct> orderProductList) {
//        return new Order(1, 1, OrderStatus.ORDER_COMPLETE, new Date());
//    }
//}
