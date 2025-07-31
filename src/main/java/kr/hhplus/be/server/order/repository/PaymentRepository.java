package kr.hhplus.be.server.order.repository;

import kr.hhplus.be.server.order.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment save(Payment payment);

    Optional<Payment> findByOrderId(Long orderId);

}
