package kr.hhplus.be.server.redis.repository;

import kr.hhplus.be.server.domain.order.entity.OrderProduct;

import java.util.Set;

public interface RedisRepository {
    void addProductScore(OrderProduct orderProduct);
    Set<Long> findAllProductsOrderByScoreDesc();
    void mergeProductRankingScore();

    void saveRequestUser(long couponId, long userId);

    long getRanking(long couponId, long userId);

    void saveUserCoupon(long couponId, long userId);

    Set<String> getRequestKeyList(String key);

    Set<String> getAllKeyList();

    Set<Object> getQueue(long couponId);

    long getQueueCount(long couponId);
}
