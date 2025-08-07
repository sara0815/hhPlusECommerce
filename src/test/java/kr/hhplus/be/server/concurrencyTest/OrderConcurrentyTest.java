package kr.hhplus.be.server.concurrencyTest;

import kr.hhplus.be.server.order.facade.OrderFacade;
import kr.hhplus.be.server.order.repository.OrderJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class OrderConcurrentyTest {

    @Autowired
    OrderFacade orderFacade;
    @Autowired
    OrderJpaRepository orderJpaRepository;

    // todo 비관 vs 낙관 이유 명확하게
    // todo 선착순 쿠폰 발급 - 비관  / 쿠폰 수량 업데이트가 많아 충돌 가능성이 높다
    // todo 포인트 충전 -
    // todo 주문 - 결제가 여러번 되는 경우
    //  포인트 차감,
    //  쿠폰 사용,
    //  재고 차감 - 비관 /
    //  서비스에 @transactional

    // 비관 vs 낙관


}
