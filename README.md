# tradex-server

보안 중심 주식 거래 시스템 백엔드.

## 모듈 구조

```
tradexServer/
├── bootstrap         실행 진입점 (Spring Boot main, application.yaml, ExceptionHandler)
├── common            공통 (BaseResponse, ClockConfig)
├── auth              인증 / JWT / 세션
├── member            회원 PII (AES-GCM, HMAC)
├── registration      auth + member orchestration
└── instrument        종목 마스터
```

## 의존 방향

```
                 bootstrap
                /    |    \
       registration  |     instrument
          /    \     |
        auth   member
          \    /
          common
```

- `bootstrap`만 모든 도메인을 알고 있음
- 도메인 모듈끼리 직접 의존 금지 (auth ↔ member 차단)
- `registration`만 예외적으로 auth, member 의존 (orchestration)
- 모든 모듈은 `common` 의존

## 빌드 / 실행

```
./run.sh
```

`.env` 파일이 프로젝트 루트에 있어야 함.

## DB 마이그레이션

도메인별로 SQL 분산 배치.

```
auth/src/main/resources/db/migration/auth/
member/src/main/resources/db/migration/member/
instrument/src/main/resources/db/migration/instrument/
```

`bootstrap/src/main/resources/application.yaml`의 `spring.flyway.locations`에 모두 등록됨.
