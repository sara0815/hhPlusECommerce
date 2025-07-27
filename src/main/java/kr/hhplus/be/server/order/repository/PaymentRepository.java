package kr.hhplus.be.server.order.repository;

import kr.hhplus.be.server.order.entity.Payment;

public interface PaymentRepository {

    Payment save(Payment payment);
}
