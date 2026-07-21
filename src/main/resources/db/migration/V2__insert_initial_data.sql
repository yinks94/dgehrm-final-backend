INSERT INTO members(
    member_id, username, password, role,
    created_at, modified_at
) VALUES
('metro_master', '시관리자', '$2a$10$w3U/l3DqK7Qj4Wf8wFzgOOZqBvP6C5O0j5oA9o3w5x2y8z1a2b3c4', 'ROLE_METRO_MASTER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('region_master', '지역관리자', '$2a$10$w3U/l3DqK7Qj4Wf8wFzgOOZqBvP6C5O0j5oA9o3w5x2y8z1a2b3c4', 'ROLE_REGION_MASTER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('metro_app', '시배정자', '$2a$10$w3U/l3DqK7Qj4Wf8wFzgOOZqBvP6C5O0j5oA9o3w5x2y8z1a2b3c4', 'ROLE_METRO_APP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('region_app', '지역배정자', '$2a$10$w3U/l3DqK7Qj4Wf8wFzgOOZqBvP6C5O0j5oA9o3w5x2y8z1a2b3c4', 'ROLE_REGION_APP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO subjects (
    subject_id, short_name, full_name, kind,
    enabled, apply_cho, can_transfer, want_count, sort,
    created_at, modified_at
) VALUES
(1001, '국어', '국어', '교과', TRUE, TRUE, TRUE, 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1002, '수학', '수학', '교과', TRUE, TRUE, TRUE, 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1003, '영어', '영어', '교과', TRUE, TRUE, TRUE, 4, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, '전문', '전문교과', '전문교과', TRUE, FALSE, TRUE, 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3001, '비교과', '비교과', '비교과', TRUE, FALSE, FALSE, 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4001, '특수', '특수', '특수', TRUE, FALSE, FALSE, 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO subject_id_sequence (kind, next_value) VALUES
('교과', 1004),
('전문교과', 2002),
('비교과', 3002),
('특수', 4002);