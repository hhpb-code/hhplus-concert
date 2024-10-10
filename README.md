# 콘서트 예약 서비스

## 기술 스택

### 주요 기술

- Java 17
- Spring Boot 3.3.4

### 빌드 도구

- Gradle(Kotlin DSL) 8.10.2

### 데이터베이스

- MySQL 8.0

## 패키지 구조

```           
com.example.hhpb.concert/
├── interface/                                      # 인터페이스 계층
│   ├── api/                                        # API 계층
│   │   ├── ErrorResponse.java                      # 에러 응답
│   │   ├── ApiControllerAdvice.java                # API 예외 처리
│   │   ├── SwaggerConfig.java                      # Swagger 설정
│   │   ├── filter/                                 # 필터
│   │   ├── interceptor/                            # 인터셉터
│   │   ├── controller/                             # 컨트롤러
│   │   └── dto/                                    # DTO (Request, Response)
│   └── scheduler/                                  # 스케줄러
│
├── domain/                                         # 도메인 계층
│   ├── common/                                     # 공통
│   │   ├── exception/                              # 예외
│   │   │   ├── BusinessException.java              # 비즈니스 예외
│   │   │   └── ErrorCode.java                      # 에러 코드 (interface)
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
        ├── [domain]/                               # 도메인
        │   ├── entity/                             # 엔티티
        │   ├── impl/                               # 구현
        │   │   └── [domain]RepositoryImpl.java     # 리포지토리 구현체
        │   └── JpaRespository.java                 # JPA 리포지토리
        ├── BaseEntity.java                         # 엔티티 공통 필드 (id, createdAt, updatedAt)
        └── JpaAuditingConfiguration.java           # JPA Auditing 설정
```

## 시나리오 분석

- [Milestone](https://github.com/orgs/hhpb-code/projects/2/views/3)
- [Requirement](./docs/REQUIREMENT.md)
- [Flowchart](./docs/FLOWCHART.md)
- [Sequence Diagram](./docs/SEQUENCE_DIAGRAM.md)
- [Class Diagram](./docs/CLASS_DIAGRAM.md)
- [ERD Diagram](./docs/ERD.md)
- [API Spec](./docs/API_SPEC.md)
