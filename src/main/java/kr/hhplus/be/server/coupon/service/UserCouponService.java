package kr.hhplus.be.server.coupon.service;

import jakarta.validation.Valid;
import kr.hhplus.be.server.coupon.entity.Coupon;
import kr.hhplus.be.server.user.entity.User;
import kr.hhplus.be.server.user.repository.UserRepository;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import kr.hhplus.be.server.coupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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

    public boolean checkCoupon(List<UserCoupon> userCouponList) {
        for (UserCoupon userCoupon : userCouponList) {
            UserCoupon target = userCouponRepository.findById(userCoupon.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "쿠폰이 존재하지 않습니다."));
            if (target.isUsed()) {
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

    public List<Long> getCouponIdListByOrderId(Long orderId) {
        return userCouponRepository.findAllByOrderId(orderId);
    }
}
