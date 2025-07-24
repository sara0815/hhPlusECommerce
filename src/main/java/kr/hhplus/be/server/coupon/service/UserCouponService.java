package kr.hhplus.be.server.coupon.service;

import kr.hhplus.be.server.coupon.entity.Coupon;
import kr.hhplus.be.server.user.entity.User;
import kr.hhplus.be.server.user.repository.UserRepository;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import kr.hhplus.be.server.coupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCouponService {
    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;

    public UserCoupon getUserCouponList(long userId) {
        User user = userRepository.selectById(userId);
        if (user == null) {
            throw new IllegalArgumentException("해당 사용자가 존재하지 않습니다: " + userId);
        }
        return userCouponRepository.findByUserID(userId);
    }

    public boolean checkCoupon(List<UserCoupon> userCouponList) {
        for (UserCoupon userCoupon : userCouponList) {
            UserCoupon target = userCouponRepository.findById(userCoupon.getId());
            boolean used = target.isUsed();
            if (used) {
                return false;
            }
        }
        return true;
    }

    public List<UserCoupon> updateUsedInfo(List<UserCoupon> userCouponList, long orderNumber) {
        Date now = new Date();
        for (UserCoupon userCoupon : userCouponList) {
            userCoupon.setUsed(true);
            userCoupon.setUpdateAt(now);
            userCoupon.setUsedAt(now);
            userCoupon.setOrderNumber(orderNumber);
        }
        return userCouponRepository.saveAll(userCouponList);
    }
}
