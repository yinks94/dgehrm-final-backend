-- 1. 사용자 테이블
CREATE TABLE members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id VARCHAR(20) NOT NULL,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_members_member_id UNIQUE (member_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;;

-- 2. 교과목 테이블
CREATE TABLE subjects (
    subject_id BIGINT NOT NULL,
    short_name VARCHAR(20) NOT NULL,
    full_name VARCHAR(20) NOT NULL,
    kind VARCHAR(30) NOT NULL,
    neis_code VARCHAR(20) NULL,
    neis_middle VARCHAR(20) NULL,
    neis_high VARCHAR(20) NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    apply_cho BOOLEAN NOT NULL DEFAULT FALSE,
    can_transfer BOOLEAN NOT NULL DEFAULT TRUE,
    want_count INT NOT NULL DEFAULT 4,
    sort BIGINT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_subjects PRIMARY KEY (subject_id),
    CONSTRAINT uk_subjects_short_name UNIQUE (short_name),
    CONSTRAINT uk_subjects_full_name UNIQUE (full_name),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. 교과목 과목 시퀀스 테이블
CREATE TABLE subject_id_sequence (
    kind VARCHAR(30) NOT NULL,
    next_value BIGINT NOT NULL,
    CONSTRAINT pk_subject_id_sequence PRIMARY KEY (kind)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;