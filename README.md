## 프로젝트

## Getting Started

### Prerequisites

#### Running Docker Containers

`local` profile 로 실행하기 위하여 인프라가 설정되어 있는 Docker 컨테이너를 실행해주셔야 합니다.

```bash
docker-compose up -d
```

## 아키텍처 선택 이유
- layered + interface 아키텍처를 선택하였습니다.
- 클린 아키텍처와 헥사고날 아키텍처는 폴더 구조가 복잡하고 오버스택이라는 코치님들의 의견을 듣고 layered + interface 아키텍처를 선택하게 되었습니다. 저 또한 익숙하지 않아 과제에 비해 복잡한 폴더 구조로 인해 개발 비용이 많이 들 것이라고 생각하게 되었습니다.  
- 레이어드 아키텍처와 레이어드 인터페이스 아키텍처 중에는 차후 대용량 트래픽을 배울 예정이기 때문에 DB가 변경 될 가능성이 있지 않을까? 하는 생각으로 선택하게 되었습니다. 