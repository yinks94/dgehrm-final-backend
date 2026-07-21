#dgehrm-final-backen

## docker 관련
```bash
backend>docker compose up -d    #docker 실행
backend>docker compose down     #docker 중지
backend>docker compose ps       #docker 상태 확인
backend>docker exec -it dgehrm-mariadb mariadb -u dgehrm -p  dgehrm_final       # mariadb console 접속
```

## flyway 설정확인
```bash
>gradle flywayInfo      # 마이그레이션 적용 현황 확인 (Pending/Success)
>gradle flywayMigrate   # DB 초기화 — 대기 중인 마이그레이션(V1, V2 등) 적용
>gradle flywayValidate  # 적용된 마이그레이션과 파일 정합성 검증
>gradle flywayRepair    # 적용된 마이그레이션과 파일 정합성 검증
>gradle flywayClean     # DB를 완전히 비우고 처음부터 재적용 (파괴적 — 로컬 개발 DB에서만 사용)

```



backend/
└── src/
    └── main/
        └── java/kr/ink94/dgehrm/
            ├── global/             # 1. 전역 공통 인프라 (독립적, 프레임워크 종속성)
            │   ├── config/         # CORS, Security, Swagger 설정
            │   ├── error/          # 전역 예외 처리 (GlobalExceptionHandler, ErrorCode)
            │   ├── response/       # Next.js 전송용 공통 응답 포맷 (ApiResponse)
            │   └── util/           # JWT, 날짜/시간 등 순수 유틸리티
            │
            ├── core/               # 2. 애플리케이션 핵심 공통 비즈니스 (도메인 간 공유)
            │   ├── auth/           # 인증/인가 핵심 로직 (PrincipalDetails, AuthService)
            │   ├── common/         # BaseEntity(생성/수정일), 공통 인터페이스
            │   └── notification/   # 알림, 메일 발송 등 시스템 전반 공통 서비스
            │
            └── domain/             # 3. 비즈니스 도메인 (기능별 응집)
                ├── user/           # 회원 도메인
                │   ├── controller/ # UserController
                │   ├── service/    # UserService
                │   ├── repository/ # UserRepository
                │   ├── entity/     # User (JPA 엔티티)
                │   └── dto/        # UserRequest, UserResponse
                │
                ├── post/           # 게시글 도메인 (필요 시 추가)
                │   ├── controller/
                │   ├── service/
                │   └── ...
                │
                └── order/          # 주문 도메인 (필요 시 추가)
                    └── ...