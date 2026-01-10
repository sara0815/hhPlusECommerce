import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 1000,           // 동시에 1000명 유저
  duration: '20s',    // 20초 동안 실행
};

const BASE_URL = 'http://host.docker.internal:8080';


export default function () {
  const couponId = 1;  // 테스트할 쿠폰 ID
  const userId = Math.floor(Math.random() * 10000) + 1; // 1~10000 랜덤 유저

  const res = http.post(`${BASE_URL}/coupon/${couponId}?userId=${userId}`);

  check(res, {
    'status is 200': (r) => r.status === 200,
  });

  sleep(1); // 요청 간격 (필요 없으면 지워도 됨)
}


// docker run --rm -i -v "C:\Users\sara8\Documents\code\hanghae99\server-java\K6Test:/scripts" grafana/k6 run `
//   --out influxdb=http://host.docker.internal:8086/k6 `
//   /scripts/coupon_test.js
