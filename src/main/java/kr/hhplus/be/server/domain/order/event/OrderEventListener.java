package kr.hhplus.be.server.domain.order.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.domain.dataplatform.DataClient;
import kr.hhplus.be.server.domain.order.dto.OrderRequest;
import kr.hhplus.be.server.domain.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final DataClient dataClient;
    private final ObjectMapper mapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void CreatedOrderAfterCommit(OrderCreatedEvent event) {
        // 외부 데이터 플랫폼으로 전송
        Order order = event.getOrder();
        String jsonString;
        try {
            jsonString = mapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        kafkaTemplate.send("orderComplete", String.valueOf(order.getId()), jsonString);
    }
}
