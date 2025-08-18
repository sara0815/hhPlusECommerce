package kr.hhplus.be.server.scheduler;

import kr.hhplus.be.server.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ProductService productService;

    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict("BEST_PRODUCT_LIST")
    public void deleteCacheBestProductList() {}
}
