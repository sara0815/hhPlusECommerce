package kr.hhplus.be.server.scheduler;

import kr.hhplus.be.server.domain.coupon.service.CouponService;
import kr.hhplus.be.server.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ProductService productService;
    private final CouponService couponService;

    @Scheduled(cron = "10 0 0 * * *")
    @CacheEvict("BEST_PRODUCT_LIST")
    public void deleteCacheBestProductList() {}

    @Scheduled(cron = "0 0 0 * * *")
    public void mergeProductRankingScore() {
        productService.mergeRankingScore();
    }

    @Scheduled(cron = "*/2 * * * * *")
    public void issueCoupon() {
        couponService.issueCoupon();
    }
}
