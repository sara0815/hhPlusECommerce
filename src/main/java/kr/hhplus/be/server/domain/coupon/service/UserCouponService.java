package kr.hhplus.be.server.domain.coupon.service;

import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.domain.user.repository.UserRepository;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCouponService {
    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;

    public List<UserCoupon> getUserCouponList(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."));
        return userCouponRepository.findByUserID(user.getId());
    }

    public void checkCoupon(Long userCouponId) {
        UserCoupon target = userCouponRepository.findById(userCouponId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "쿠폰이 존재하지 않습니다."));
        if (target.isUsed()) {
            throw new IllegalStateException("이미 사용된 쿠폰입니다.");
        }
    }

    // 쿠폰 사용처리
    @Transactional
    public UserCoupon updateUsedInfo(Long couponId, long orderNumber) {
        if (couponId == null) {
            return null;
        }
        UserCoupon usedCoupon = userCouponRepository.findById(couponId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "쿠폰이 존재하지 않습니다."));
        if (usedCoupon.isUsed()) {
            throw new IllegalStateException("이미 사용된 쿠폰입니다.");
        }
        Date now = new Date();
        usedCoupon.setUpdateAt(now);
        usedCoupon.setUsed(true);
        usedCoupon.setUsedAt(now);
        return userCouponRepository.save(usedCoupon); // todo update를 해야하는지..????
    }

    public List<Long> getCouponIdListByOrderId(Long orderId) {
        return userCouponRepository.findAllByOrderId(orderId);
    }
}
