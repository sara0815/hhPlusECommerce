package kr.hhplus.be.server.domain.order.event;

import kr.hhplus.be.server.domain.dataplatform.DataClient;
import kr.hhplus.be.server.domain.order.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final DataClient dataClient;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void CreatedOrderAfterCommit(OrderRequest orderRequest) {

        // 외부 데이터 플랫폼으로 전송
        dataClient.sendOrderInfo(orderRequest);
    }
}
