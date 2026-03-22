# 로깅 가이드 (Spring Boot 3.2.x)

백엔드 로그는 **`backend/src/main/resources/application.yml`** 의 **`logging.*`** 로 통합 관리합니다.  
Spring Boot가 이 속성을 Logback 설정으로 매핑합니다(별도 `logback-spring.xml` 없음).

선택적으로 **gitignore된** `application-local.yml` 등에 `logging.*` 를 덮어쓸 수 있으나, **프로필별 기본 경로·레벨**은 이미 `application.yml` 안에서 `---` 다중 문서 + `spring.config.activate.on-profile` 로 나뉘어 있습니다.

---

## 1. 설정 파일

| 파일 | 역할 |
|------|------|
| `backend/src/main/resources/application.yml` | DB·서버·JWT + **로그**(패턴, 인코딩, 레벨, 파일 경로, 롤링). 기본 문서 + `local` / `dev` 프로필용 `---` 블록 |
| `backend/src/main/resources/application-local.yml` | (선택, 로컬 전용) `.gitignore` 대상이면 팀에 공유되지 않음. 필요 시 `logging.*` 추가 덮어쓰기 가능 |

---

## 2. 로그 패턴

`application.yml` 의 `logging.pattern.console` / `logging.pattern.file`:

```
%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
```

날짜 형식: **`yyyy-MM-dd HH:mm:ss.SSS`**

---

## 3. 출력 대상

- **콘솔**: `logging.file.name` 이 설정되어 있어도 Spring Boot 기본 구성으로 **콘솔 + 롤링 파일**이 함께 사용됩니다.
- **파일**: `logging.file.name` 및 `logging.logback.rollingpolicy.*` 로 경로·일별 롤링을 지정합니다.

---

## 4. Spring 프로파일별 로그 경로

애플리케이션 **작업 디렉터리(보통 프로젝트 루트 또는 `backend` 모듈 루트)** 기준 상대 경로입니다.  
`application.yml` 내 프로필 전용 문서에서 `logging.file.name` / `file-name-pattern` 이 달라집니다.

### `local` 프로파일

| 구분 | 경로 |
|------|------|
| 현재 로그 | `logs_local/app.log` |
| 날짜별 아카이브 | `logs_local/app_local.yyyy-MM-dd.log` (예: `app_local.2026-03-21.log`) |

IntelliJ에서 `SPRING_PROFILES_ACTIVE=local` 로 실행할 때 해당 경로를 사용합니다.

### `dev` 프로파일

| 구분 | 경로 |
|------|------|
| 현재 로그 | `logs_dev/app.log` |
| 날짜별 아카이브 | `logs_dev/app_dev.yyyy-MM-dd.log` |

**Docker 로 백엔드를 띄울 때** 컨테이너 안 작업 디렉터리는 `/app` 입니다.  
`docker-compose.yml` 의 `backend` 서비스에 **`SPRING_PROFILES_ACTIVE=dev`** 가 설정되어 있어야 위 `logs_dev/` 경로가 사용됩니다.  
또한 이미지 빌드 시 **`/app/logs`, `/app/logs_dev`, `/app/logs_local`** 디렉터리를 만들고 `spring` 사용자에게 권한을 줍니다. 그렇지 않으면 `/app` 이 root 소유라 **파일 로그 생성 시 `Failed to create parent directories`** 가 날 수 있습니다.

### `local` 도 아니고 `dev` 도 아닐 때

예: 프로덕션 빌드, `prod` 만 켠 경우 등.

| 구분 | 경로 |
|------|------|
| 현재 로그 | `logs/app.log` |
| 날짜별 아카이브 | `logs/app.yyyy-MM-dd.log` |

---

## 5. 파일 롤링 정책

- **설정 키**: `logging.logback.rollingpolicy.*` (Spring Boot → Logback `RollingFileAppender` + 시간 기반 롤링)
- **보관 일수**: `max-history` = **2**
- **전체 용량 상한**: `total-size-cap` = **1GB**

---

## 6. 로거 레벨 (요약)

공통(`application.yml` 기본 문서의 `logging.level`):

| 로거 | 레벨 | 설명 |
|------|------|------|
| **root** | **INFO** (`dev` 프로필에서는 **DEBUG** 로 덮어씀) | 애플리케이션 일반 로그 |
| `org.springframework` | **WARN** | Spring 내부 로그 과다 출력 방지 |
| `org.hibernate.SQL` | **DEBUG** | 실행 SQL 문 |
| `org.hibernate.orm.jdbc.bind` | **TRACE** | SQL 바인딩 파라미터 값 (Hibernate 6 계열) |
| `com.ai_kids_care` | **DEBUG** | 애플리케이션 패키지 |

SQL·바인딩 로그는 상위 로거를 타며 **콘솔과 파일 모두**에 남습니다.

`spring.jpa.show-sql` 은 `false` 로 두고, **로그로 SQL을 보는 방식**을 권장합니다 (`logging.level` 과 맞추기 쉬움).

---

## 7. `application.yml` 구조 (요약)

- **첫 번째 문서**: 기본 `spring.*`, `server`, `jwt`, 그리고 공통 `logging.*` (기본 파일 경로 `logs/`, root INFO 등).
- **`on-profile: local` 문서**: `logging.file.name`, `rollingpolicy.file-name-pattern` 만 `logs_local/` 기준으로 재정의.
- **`on-profile: dev` 문서**: `logs_dev/` 경로 + `logging.level.root: DEBUG`.

복잡한 appender·필터가 필요하면 `logback-spring.xml` 을 다시 두고 `logging.config` 로 지정하는 방식도 가능합니다.

---

## 8. Git 에서 제외하는 로그 폴더

`.gitignore` 에 다음이 포함되어 있습니다.

- `logs/`, `log/`
- **`logs*/`** — `logs_local/`, `logs_dev/` 등 **`logs` 로 시작하는 디렉터리** 전체

로그 파일은 저장소에 올리지 않습니다.

---

## 9. 주의사항

1. **`local` 과 `dev` 프로파일을 동시에 활성화**하면, 여러 프로필용 `logging.*` 블록이 겹치며 **어느 값이 적용될지 예측하기 어려울 수 있습니다.** 로그 경로를 명확히 하려면 **보통 한 프로파일만** 켜는 것이 안전합니다.
2. 로그 파일 경로는 **실행 시 작업 디렉터리**에 따라 달라질 수 있습니다. IntelliJ 에서는 Run Configuration 의 Working directory 를 확인하세요.
3. 도커에서 **`SPRING_PROFILES_ACTIVE` 없이** 실행하면 `logs/app.log` 폴백 구간이 쓰이는데, 컨테이너 권한 문제로 동일하게 실패할 수 있습니다. **백엔드 컨테이너는 `dev` 프로파일 + 로그 디렉터리 사전 생성**을 사용하세요.

### 트러블슈팅: `Failed to create parent directories` / `logs/app.log (No such file or directory)`

- 원인: (1) `dev` 미설정으로 `logs/` 사용 + (2) `spring` 사용자가 `/app` 아래에 디렉터리를 만들 수 없음.
- 조치: `docker-compose.yml` 에 `SPRING_PROFILES_ACTIVE: dev` 확인, 이미지 **재빌드** (`docker compose build --no-cache backend` 등) 후 다시 기동.

---

## 10. 빠른 확인

- 프로파일 `local` 로 기동 후, 콘솔에 위 패턴으로 로그가 나오는지 확인합니다.
- `logs_local/app.log` 가 생성되는지 확인합니다.
- DB 조회 등이 발생할 때 `org.hibernate.SQL` / 바인딩 TRACE 가 콘솔·파일 모두에 찍히는지 확인합니다.

---

## 관련 문서

- [README_Docker_IntelliJ_동시실행.md](./README_Docker_IntelliJ_동시실행.md) — Docker / IntelliJ 동시 실행 시 포트·환경 변수
