package kr.hhplus.be.server.coupon.service;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import kr.hhplus.be.server.coupon.entity.Coupon;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import kr.hhplus.be.server.coupon.dto.UserCouponResponse;
import kr.hhplus.be.server.coupon.repository.CouponRepository;
import kr.hhplus.be.server.coupon.repository.UserCouponRepository;
import kr.hhplus.be.server.redis.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final RedisRepository redisRepository;

    public long couponDiscountRate(Long userCouponId) { // % 단위로 반환
        if (userCouponId == null) {
            return 0;
        }
        UserCoupon userCoupon = userCouponRepository.findById(userCouponId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "발급 쿠폰을 찾을 수 없습니다."));
        Coupon coupon = couponRepository.findById(userCoupon.getCouponId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "쿠폰을 찾을 수 없습니다."));
        return coupon.getDiscountRate();
    }

    public List<Coupon> getList(List<Long> idList) {
        return couponRepository.findAllById(idList);
    }

    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId).orElseThrow();
    }

    public void requestIssueCoupon(long couponId, long userId) {
//        Coupon coupon = couponRepository.findById(couponId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "쿠폰을 찾을 수 없습니다."));
//        if (new Date().before(coupon.getIssueStartDatetime())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아직 발급 시작 전입니다.");
//        }
        redisRepository.saveRequestUser(couponId, userId);
//        if (coupon.getTotalCount() - coupon.getIssuedCount() > 0) {
//            UserCoupon issuedUserCoupon = userCouponRepository.save(new UserCoupon(userId, couponId));
//            coupon.setIssuedCount(coupon.getIssuedCount() + 1);
//            couponRepository.save(coupon);
//            return new UserCouponResponse(issuedUserCoupon.getId(), userId, issuedUserCoupon.getCouponId(), false, new Date());
//        }
    }

    public void issueCoupon() {
        Set<String> keySets = redisRepository.getRequestKeyList("coupon");
        List<String> keyList = new ArrayList<>(keySets);

        keyList.stream().forEach(key -> {
            long couponId = Long.parseLong(key.split(":")[1]);
            Set<Object> queue = redisRepository.getQueue(couponId);
            long issuedCount = userCouponRepository.countByCouponId(couponId);
            Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);
            if (optionalCoupon.isEmpty()) {
                return;
            }
            Coupon coupon = optionalCoupon.orElseThrow();
            if (coupon.getTotalCount() >= issuedCount) {
                int issueNum = 0;
                for (Object userIdObj : queue) {
                    if (coupon.getTotalCount() <= issueNum + issuedCount) {
                        break;
                    }
                    long userId = Long.parseLong(userIdObj.toString());
                    UserCoupon userCoupon = new UserCoupon(userId, couponId);
                    userCouponRepository.save(userCoupon);
                    issueNum++;
                }
            }
        });
    }
}
