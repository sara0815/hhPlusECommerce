import http from 'k6/http';
import { check } from 'k6';

export const options = {
  stages: [
    { duration: '5s', target: 200 }, // 5초 동안 200명으로 증가
    { duration: '15s', target: 200 }, // 15초 유지
    { duration: '5s', target: 0 },    // 감소
  ],
};

const BASE_URL = 'http://host.docker.internal:8080';

export default function () {
  const userId = Math.floor(Math.random() * 10000) + 1;

  const payload = JSON.stringify({
    userId: userId,
    userCouponId: null,   // 쿠폰 미사용 (랜덤으로 쓰려면 Math.random() 조건 걸어도 됨)
    orderProductList: [
      {
        productId: Math.floor(Math.random() * 100) + 1, // 상품 1~100 랜덤
        quantity: 1
      }
    ]
  });

  const headers = { 'Content-Type': 'application/json' };

  const res = http.post(`${BASE_URL}/order/`, payload, { headers });

  check(res, {
    'status is 200': (r) => r.status === 200,
  });
}
