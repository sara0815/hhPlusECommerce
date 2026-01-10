import http from 'k6/http';
import { check } from 'k6';

export const options = {
  stages: [
    { duration: '5s', target: 10 }, // 5초 동안 500명으로 급격히 증가 (피크 도달)
    { duration: '10s', target: 10 }, // 피크 상태 유지
    { duration: '5s', target: 0 },     // 다시 0으로 감소
  ],
};

const BASE_URL = 'http://host.docker.internal:8080';

export default function () {
  const couponId = 1;  // 테스트할 쿠폰 ID
  const userId = Math.floor(Math.random() * 10000) + 1; // 1~10000 랜덤 유저

  const res = http.post(`${BASE_URL}/coupon/${couponId}?userId=${userId}`);

  check(res, {
    'status is 200': (r) => r.status === 200,
  });
}

// docker run --rm -i -v "C:\Users\sara8\Documents\code\hanghae99\server-java\K6Test:/scripts" grafana/k6 run `
// --out influxdb=http://host.docker.internal:8086/k6 `
// /scripts/coupon_peak_test.js