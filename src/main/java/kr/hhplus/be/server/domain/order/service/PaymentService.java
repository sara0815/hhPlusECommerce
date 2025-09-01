package kr.hhplus.be.server.domain.order.service;

import kr.hhplus.be.server.domain.order.entity.Payment;
import kr.hhplus.be.server.domain.order.repository.PaymentRepository;
import kr.hhplus.be.server.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProductService productService;

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment getInfoByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "결제 정보를 찾을 수 없습니다."));
    }
}
