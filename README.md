## 프로젝트

## Getting Started

### Prerequisites

#### Running Docker Containers

`local` profile 로 실행하기 위하여 인프라가 설정되어 있는 Docker 컨테이너를 실행해주셔야 합니다.

```bash
docker-compose up -d
```
---
## - 아키텍처 선택 이유
- layered + interface 아키텍처를 선택하였습니다.
- 클린 아키텍처와 헥사고날 아키텍처는 폴더 구조가 복잡하고 오버스택이라는 코치님들의 의견을 듣고 layered + interface 아키텍처를 선택하게 되었습니다. 저 또한 익숙하지 않아 과제에 비해 복잡한 폴더 구조로 인해 개발 비용이 많이 들 것이라고 생각하게 되었습니다.
- 레이어드 아키텍처와 레이어드 인터페이스 아키텍처 중에는 차후 대용량 트래픽을 배울 예정이기 때문에 DB가 변경 될 가능성이 있지 않을까? 하는 생각으로 선택하게 되었습니다.

---
## - 요구사항
- API
- [x] 잔액 충전
- [x] 잔액 조회
- [x] 상품 조회 - ID, 이름, 가격, 잔여수량(조회 시점 상품별로 정확해야)
- [x] 주문 (상태 처리 / 재고 확인 / 쿠폰 적용 / 보유 잔고 확인 / 잔액 차감 / 재고 차감 / 성공 시 외부 어플리케이션에 데이터 전송)
- [x] 결제
- [x] 선착순 쿠폰 발급
- [x] 선착순 쿠폰 보유 목록 조회
- [x] best 상품 top 5 (3일간)
- [x] 통계 기술 고민

---
## - ERD
![ERD.png](%EC%84%A4%EA%B3%84%2FERD.png)
https://dbdiagram.io/d/6874e772f413ba3508c05838

## - 시퀀스 다이어그램
주문
https://www.mermaidchart.com/app/projects/3ef5fbc9-d121-4228-b1bf-82af5f20bbe4/diagrams/001f69cd-3640-4bb4-8af2-f3f569151535/version/v0.1/edit
쿠폰 발급
https://www.mermaidchart.com/app/projects/3ef5fbc9-d121-4228-b1bf-82af5f20bbe4/diagrams/cbe5c2d0-de1f-4f57-932c-2ae7527c4c8f/version/v0.1/edit
포인트 적립
https://www.mermaidchart.com/app/projects/3ef5fbc9-d121-4228-b1bf-82af5f20bbe4/diagrams/06a92a0b-8a97-4d9e-bf72-0132117f316a/version/v0.1/edit

--- 

## - 병목 분석
[병목분석보고서.md](%EC%84%A4%EA%B3%84%2F%EB%B3%91%EB%AA%A9%EB%B6%84%EC%84%9D%EB%B3%B4%EA%B3%A0%EC%84%9C.md)

## - 동시성 이슈
[동시성이슈보고서.md](%EC%84%A4%EA%B3%84%2F%EB%8F%99%EC%8B%9C%EC%84%B1%EC%9D%B4%EC%8A%88%EB%B3%B4%EA%B3%A0%EC%84%9C.md)

--- 

## - 레디스 캐싱
[레디스 캐싱 보고서.md](%EC%84%A4%EA%B3%84%2F%EB%A0%88%EB%94%94%EC%8A%A4%20%EC%BA%90%EC%8B%B1%20%EB%B3%B4%EA%B3%A0%EC%84%9C.md)

## - 레디스 sorted set 적용
[레디스 sorted set 적용 설계 보고서.md](%EC%84%A4%EA%B3%84%2F%EB%A0%88%EB%94%94%EC%8A%A4%20sorted%20set%20%EC%A0%81%EC%9A%A9%20%EC%84%A4%EA%B3%84%20%EB%B3%B4%EA%B3%A0%EC%84%9C.md)

---

## - msa 설계 가정
[msa설계.md](%EC%84%A4%EA%B3%84%2Fmsa%EC%84%A4%EA%B3%84.md)

--- 

## - Kafka 정리
[kafka.md](%EC%84%A4%EA%B3%84%2Fkafka.md)

## - 카프카 설계
[카프카 설계문서.md](%EC%84%A4%EA%B3%84%2F%EC%B9%B4%ED%94%84%EC%B9%B4%20%EC%84%A4%EA%B3%84%EB%AC%B8%EC%84%9C.md)

---

## - 부하테스트
[부하테스트.md](%EC%84%A4%EA%B3%84%2F%EB%B6%80%ED%95%98%ED%85%8C%EC%8A%A4%ED%8A%B8.md)
- 클린 아키텍처와 헥사고날 아키텍처는 폴더 구조가 복잡하고 오버스택이라는 코치님들의 의견을 듣고 layered + interface 아키텍처를 선택하게 되었습니다. 저 또한 익숙하지 않아 과제에 비해 복잡한 폴더 구조로 인해 개발 비용이 많이 들 것이라고 생각하게 되었습니다.  
- 레이어드 아키텍처와 레이어드 인터페이스 아키텍처 중에는 차후 대용량 트래픽을 배울 예정이기 때문에 DB가 변경 될 가능성이 있지 않을까? 하는 생각으로 선택하게 되었습니다.

---
## - 요구사항
- API
- [x] 잔액 충전
- [x] 잔액 조회
- [x] 상품 조회 - ID, 이름, 가격, 잔여수량(조회 시점 상품별로 정확해야)
- [x] 주문 (상태 처리 / 재고 확인 / 쿠폰 적용 / 보유 잔고 확인 / 잔액 차감 / 재고 차감 / 성공 시 외부 어플리케이션에 데이터 전송)
- [x] 결제
- [x] 선착순 쿠폰 발급
- [x] 선착순 쿠폰 보유 목록 조회
- [x] best 상품 top 5 (3일간)
- [x] 통계 기술 고민

---
## - ERD 
![ERD.png](%EC%84%A4%EA%B3%84%2FERD.png)
https://dbdiagram.io/d/6874e772f413ba3508c05838

## - 시퀀스 다이어그램
  - 주문  
https://www.mermaidchart.com/app/projects/3ef5fbc9-d121-4228-b1bf-82af5f20bbe4/diagrams/001f69cd-3640-4bb4-8af2-f3f569151535/version/v0.1/edit  
  - 쿠폰 발급  
https://www.mermaidchart.com/app/projects/3ef5fbc9-d121-4228-b1bf-82af5f20bbe4/diagrams/cbe5c2d0-de1f-4f57-932c-2ae7527c4c8f/version/v0.1/edit  
- 포인트 적립  
https://www.mermaidchart.com/app/projects/3ef5fbc9-d121-4228-b1bf-82af5f20bbe4/diagrams/06a92a0b-8a97-4d9e-bf72-0132117f316a/version/v0.1/edit

--- 

## - 병목 분석
[병목분석보고서.md](%EC%84%A4%EA%B3%84%2F%EB%B3%91%EB%AA%A9%EB%B6%84%EC%84%9D%EB%B3%B4%EA%B3%A0%EC%84%9C.md)

## - 동시성 이슈
[동시성이슈보고서.md](%EC%84%A4%EA%B3%84%2F%EB%8F%99%EC%8B%9C%EC%84%B1%EC%9D%B4%EC%8A%88%EB%B3%B4%EA%B3%A0%EC%84%9C.md)

--- 

## - 레디스 캐싱
[레디스 캐싱 보고서.md](%EC%84%A4%EA%B3%84%2F%EB%A0%88%EB%94%94%EC%8A%A4%20%EC%BA%90%EC%8B%B1%20%EB%B3%B4%EA%B3%A0%EC%84%9C.md)

## - 레디스 sorted set 적용
[레디스 sorted set 적용 설계 보고서.md](%EC%84%A4%EA%B3%84%2F%EB%A0%88%EB%94%94%EC%8A%A4%20sorted%20set%20%EC%A0%81%EC%9A%A9%20%EC%84%A4%EA%B3%84%20%EB%B3%B4%EA%B3%A0%EC%84%9C.md)

---

## - msa 설계 가정
[msa설계.md](%EC%84%A4%EA%B3%84%2Fmsa%EC%84%A4%EA%B3%84.md)

--- 

## - Kafka 정리
[kafka.md](%EC%84%A4%EA%B3%84%2Fkafka.md)

## - 카프카 설계
[카프카 설계문서.md](%EC%84%A4%EA%B3%84%2F%EC%B9%B4%ED%94%84%EC%B9%B4%20%EC%84%A4%EA%B3%84%EB%AC%B8%EC%84%9C.md)

---

## - 부하테스트
[부하테스트.md](%EC%84%A4%EA%B3%84%2F%EB%B6%80%ED%95%98%ED%85%8C%EC%8A%A4%ED%8A%B8.md)