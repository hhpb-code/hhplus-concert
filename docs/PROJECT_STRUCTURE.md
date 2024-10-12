# 프로젝트 구조

## 기술 스택

### 주요 기술

- Java 17
- Spring Boot 3.3.4
- Spring Data JPA

### 빌드 도구

- Gradle(Kotlin DSL) 8.10.2

### 데이터베이스

- MySQL 8.0

## 패키지 구조

```
com.example.hhpb.concert/
├── interface/                                      # 인터페이스 계층
│   ├── api/                                        # API 계층
│   │   ├── filter/                                 # 필터
│   │   ├── interceptor/                            # 인터셉터
│   │   ├── controller/                             # 컨트롤러
│   │   └── dto/                                    # DTO (Request, Response)
│   └── scheduler/                                  # 스케줄러
│
├── domain/                                         # 도메인 계층
│   ├── common/                                     # 공통
│   │   └── exception/                              # 예외
│   └── [domain]/                                   # 도메인
│       ├── model/                                  # 모델
│       ├── repository/                             # 리포지토리
│       ├── service/                                # 서비스
│       └── error/                                  # 에러
│
├── application/                                    # 응용 계층
│   └── [domain]/                                   # 도메인
│       └── facade/                                 # 퍼사드
│
└── infra/                                          # 인프라스트럭처 계층
    └── db/                                         # 데이터베이스
        └── [domain]/                               # 도메인
            ├── entity/                             # 엔티티
            └── impl/                               # 구현체
```