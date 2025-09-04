package kr.hhplus.be.server.domain.coupon.kafka;

import kr.hhplus.be.server.domain.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CouponConsumer {

    private final CouponService couponService;

    @KafkaListener(topics = "coupon", groupId = "coupon-consumer", concurrency = "10")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println(record.topic() + record.key() + record.value());
        couponService.issueCoupon(Long.parseLong(record.key()), Long.parseLong(record.value()));
    }
}
