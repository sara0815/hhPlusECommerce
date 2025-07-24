package kr.hhplus.be.server.coupon.service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.coupon.entity.Coupon;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import kr.hhplus.be.server.coupon.entity.UserCouponResponse;
import kr.hhplus.be.server.coupon.repository.CouponRepository;
import kr.hhplus.be.server.coupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    public long couponListDiscountRate(List<UserCoupon> userCouponList) { // % 단위로 반환
        if (userCouponList.isEmpty()) {
            return 0;
        }
        double discountRate = 1;
        for (UserCoupon userCoupon : userCouponList) {
            Coupon coupon = couponRepository.findById(userCoupon.getCouponId());
            discountRate *= ((100 - (double)coupon.getDiscountRate()) / 100);
        }
        discountRate = 1 - discountRate;
        discountRate = Math.ceil(discountRate * 100);
        return (long) discountRate;
    }

    public List<Coupon> getList(List<Long> idList) {
        return couponRepository.findAllById(idList);
    }

    @Transactional
    public UserCouponResponse issueCoupon(long couponId, long userId) {
        Coupon coupon = couponRepository.findById(couponId);
        if (new Date().before(coupon.getIssueStartDatetime())) {
            System.out.println("new Date" + new Date());
            System.out.println("issue" + coupon.getIssueStartDatetime());
            return new UserCouponResponse(false, null, "아직 발급 시작 전입니다.");
        }
        if (coupon.getTotalCount() - coupon.getIssuedCount() > 0) {
            UserCoupon userCoupon = userCouponRepository.save(new UserCoupon(userId, couponId));
            return new UserCouponResponse(true, userCoupon, "발급 성공");
        }
        return new UserCouponResponse(false, null, "선착순 발급이 종료되었습니다.");
    }
}
