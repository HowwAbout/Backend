

## 💡 프로젝트 개요

**HowAbout**은 AI 기반 데이트 활동 추천 및 계획 생성을 지원하는 웹 애플리케이션입니다. 사용자는 다양한 활동을 선택하고 조합해 자신만의 데이트 플랜을 생성할 수 있습니다.

* **프로젝트 기간**: 2024.08.17 - 2024.09.12 (4주)
* **개발자**: 박설
---

## ⚙️ 기술 스택

| 구분        | 기술                            |
| --------- | ----------------------------- |
| Language  | Java                          |
| Framework | Spring Boot, Spring Security  |
| DB        | MySQL                         |
| ORM       | Spring Data JPA               |
| 인증/보안     | JWT                           |
| API 문서화   | Swagger (springdoc-openapi)   |

---


## 📌 주요 기능 및 구현 내용

### 🔐 인증/인가

* JWT 기반 인증 및 인가 구현 (AccessToken + RefreshToken)
* Spring Security를 통한 사용자 권한 처리 및 CSRF 방지
* 로그인/회원가입/로그아웃 API 구현

### 👤 사용자 API

* 이메일 중복 검사 및 유효성 검증
* 비밀번호 암호화 저장 (BCryptPasswordEncoder)
* 사용자 등록, 로그인, 비밀번호 변경

### 📍 데이트 활동 API (DateActivity)

* 활동 등록/조회/수정/삭제 (CRUD)
* 활동별 위치, 설명, 시간, 이미지 등 관리
* 모든 활동 목록 조회 API 제공

### 📅 데이트 플랜 API (DatePlan)

* 데이트 플랜 등록/수정/삭제
* 다대다(N\:M) 관계 매핑: DatePlan ↔ PlanActivity ↔ DateActivity
* 활동 순서를 지정하여 플랜 구성 (`activity_order` 활용)
* 플랜 내 활동의 순서 변경 및 제거 기능

---

## 🔄 API 명세

`Swagger`를 통해 모든 API 명세를 자동 문서화

