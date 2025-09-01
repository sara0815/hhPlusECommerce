package kr.hhplus.be.server.redis.repository;

import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {

    private final RedisTemplate redisTemplate;

    @Override
    public void addProductScore(OrderProduct orderProduct) {
        String today = LocalDate.now().toString();
        redisTemplate.opsForZSet().incrementScore("product:rank:" + today, orderProduct.getProductId(), orderProduct.getCount());
    }

    @Override
    public Set<Long> findAllProductsOrderByScoreDesc() {
        Set<Long> result = redisTemplate.opsForZSet().reverseRange("product:rank:3days", 0, 9);
        return result;
    }

    @Override
    public void mergeProductRankingScore() {
        String key0 = "product:rank:" + LocalDate.now().toString(); // 당일 (테스트용)
        String key1 = "product:rank:" + LocalDate.now().minusDays(1).toString(); // 1일전
        String key2 = "product:rank:" + LocalDate.now().minusDays(2).toString(); // 2일전
        String key3 = "product:rank:" + LocalDate.now().minusDays(3).toString(); // 3일전
        redisTemplate.opsForZSet().unionAndStore(key1, Arrays.asList(key0, key2, key3), "product:rank:3days");
    }

    @Override
    public long getRanking(long couponId, long userId) {
        return redisTemplate.opsForZSet().rank("coupon:" + couponId, userId);
    }

    @Override
    public void saveRequestUser(long couponId, long userId) {
        redisTemplate.opsForZSet().add("coupon:" + couponId, userId, System.currentTimeMillis());
    }

    @Override
    public void saveUserCoupon(long couponId, long userId) {
        String key = "userCoupon:" + userId;

        Map<String, Object> userCoupon = new HashMap<>();
        userCoupon.put("couponId", couponId);
        userCoupon.put("used", false);
        userCoupon.put("createAt", LocalDateTime.now().toString());
        redisTemplate.opsForHash().putAll(key, userCoupon);
    }

    @Override
    public Set<String> getRequestKeyList(String key) {
        return redisTemplate.keys(key + ":*");
    }

    @Override
    public Set<String> getAllKeyList() {
        return redisTemplate.keys("*");
    }

    @Override
    public Set<Object> getQueue(long couponId) {
        return redisTemplate.opsForZSet().range("coupon:" + couponId, 0, -1);
    }

    @Override
    public long getQueueCount(long couponId) {
        return redisTemplate.opsForZSet().zCard("coupon:" + couponId);
    }
}
