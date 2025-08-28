package kr.hhplus.be.server.domain.dataplatform;

import kr.hhplus.be.server.domain.order.dto.OrderRequest;
import kr.hhplus.be.server.domain.order.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "data-client",
        url = "https://외부_데이터_전송_url.com"
)
public interface DataClient {

    @PostMapping("/order/{id}")
    void sendOrderInfo(@RequestBody OrderRequest orderRequest);
}
