package kr.hhplus.be.server.order.orderFacade;

import kr.hhplus.be.server.domain.coupon.service.CouponService;
import kr.hhplus.be.server.domain.coupon.service.UserCouponService;
import kr.hhplus.be.server.domain.order.dto.OrderRequest;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import kr.hhplus.be.server.domain.order.entity.OrderStatus;
import kr.hhplus.be.server.domain.order.event.OrderCreatedEvent;
import kr.hhplus.be.server.domain.order.facade.OrderFacade;
import kr.hhplus.be.server.domain.order.service.OrderProductService;
import kr.hhplus.be.server.domain.order.service.OrderService;
import kr.hhplus.be.server.domain.order.service.PaymentService;
import kr.hhplus.be.server.domain.point.service.PointService;
import kr.hhplus.be.server.domain.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderFacadeTest {

    @Mock
    ApplicationEventPublisher eventPublisher;
    @InjectMocks
    OrderFacade orderFacade;
    @Mock
    ProductService productService;
    @Mock
    UserCouponService userCouponService;
    @Mock
    CouponService couponService;
    @Mock
    PointService pointService;
    @Mock
    PaymentService paymentService;
    @Mock
    OrderProductService orderProductService;
    @Mock
    OrderService orderService;

    @Test
    @DisplayName("주문 완료 이벤트 호출 테스트")
    public void orderEventCall() {
        // given
        OrderProduct orderProduct = new OrderProduct(1L, 1L, 1L, 1L, 1000, new Date());
        List<OrderProduct> orderProductList = new ArrayList<>();
        orderProductList.add(orderProduct);
        OrderRequest orderRequest = new OrderRequest(orderProductList, null, 1L);

        given(productService.calculateOrderProductPrice(anyList())).willReturn(1000L);
        Order order = new Order(1L, OrderStatus.ORDER_COMPLETE);
        given(orderService.save(any(Order.class))).willReturn(order);
        // when
        orderFacade.order(orderRequest);

        // then
        verify(eventPublisher, times(1)).publishEvent(any(OrderCreatedEvent.class));
    }
}
