CREATE TABLE subjects (
    subject_id BIGINT PRIMARY KEY,
    short_name VARCHAR(20) NOT NULL,
    full_name VARCHAR(20) NOT NULL,
    kind VARCHAR(30) NOT NULL,
    neis_code VARCHAR(20),
    neis_middle VARCHAR(20),
    neis_high VARCHAR(20),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    apply_cho BOOLEAN NOT NULL DEFAULT FALSE,
    can_transfer BOOLEAN NOT NULL DEFAULT TRUE,
    want_count INTEGER NOT NULL DEFAULT 4,
    sort BIGINT,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP NOT NULL
);

CREATE TABLE subject_id_sequence (
    kind VARCHAR(30) PRIMARY KEY,
    next_value BIGINT NOT NULL
);