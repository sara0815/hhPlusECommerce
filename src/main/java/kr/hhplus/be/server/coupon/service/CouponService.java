package kr.hhplus.be.server.coupon.service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.coupon.entity.Coupon;
import kr.hhplus.be.server.coupon.entity.UserCoupon;
import kr.hhplus.be.server.coupon.dto.UserCouponResponse;
import kr.hhplus.be.server.coupon.repository.CouponRepository;
import kr.hhplus.be.server.coupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            Coupon coupon = couponRepository.findById(userCoupon.getCouponId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "쿠폰을 찾을 수 없습니다."));
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
    public UserCoupon issueCoupon(long couponId, long userId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "쿠폰을 찾을 수 없습니다."));;
        if (new Date().before(coupon.getIssueStartDatetime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아직 발급 시작 전입니다.");
        }
        if (coupon.getTotalCount() - coupon.getIssuedCount() > 0) {
            return userCouponRepository.save(new UserCoupon(userId, couponId));
        }
        throw new IllegalStateException("선착순 발급이 종료되었습니다.");
    }
}
