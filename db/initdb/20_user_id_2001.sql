--원래 있던 user_id와 role_assignment_id의 중복문제 해결


BEGIN;

-- 1. 외래 키 제약 조건 검사 일시 중지
SET session_replication_role = 'replica';

-- 2. users 테이블의 기본 키(PK) 변경 (1 -> 2001, 2 -> 2002)
UPDATE users SET user_id = user_id + 2000 WHERE user_id IN (1, 2);

-- 3. users.user_id를 참조하는 모든 자식 테이블의 외래 키(FK) 일괄 연쇄 변경
UPDATE superadmins SET user_id = user_id + 2000 WHERE user_id IN (1, 2);
UPDATE teachers SET user_id = user_id + 2000 WHERE user_id IN (1, 2);
UPDATE guardians SET user_id = user_id + 2000 WHERE user_id IN (1, 2);

UPDATE user_role_assignments SET user_id = user_id + 2000 WHERE user_id IN (1, 2);
UPDATE user_role_assignments SET granted_by_user_id = granted_by_user_id + 2000 WHERE granted_by_user_id IN (1, 2);
UPDATE user_role_assignments SET revoked_by_user_id = revoked_by_user_id + 2000 WHERE revoked_by_user_id IN (1, 2);

UPDATE user_kindergarten_memberships SET user_id = user_id + 2000 WHERE user_id IN (1, 2);
UPDATE cctv_cameras SET created_by_user_id = created_by_user_id + 2000 WHERE created_by_user_id IN (1, 2);
UPDATE child_class_assignments SET created_by_user_id = created_by_user_id + 2000 WHERE created_by_user_id IN (1, 2);
UPDATE class_teacher_assignments SET created_by_user_id = created_by_user_id + 2000 WHERE created_by_user_id IN (1, 2);
UPDATE class_room_assignments SET created_by_user_id = created_by_user_id + 2000 WHERE created_by_user_id IN (1, 2);

UPDATE event_reviews SET user_id = user_id + 2000 WHERE user_id IN (1, 2);
UPDATE notifications SET recipient_user_id = recipient_user_id + 2000 WHERE recipient_user_id IN (1, 2);
UPDATE notification_rules SET user_id = user_id + 2000 WHERE user_id IN (1, 2);
UPDATE device_tokens SET user_id = user_id + 2000 WHERE user_id IN (1, 2);
UPDATE audit_logs SET user_id = user_id + 2000 WHERE user_id IN (1, 2);
UPDATE announcements SET author_id = author_id + 2000 WHERE author_id IN (1, 2);

-- 4. user_role_assignments 테이블의 기본 키(PK) 변경 (1 -> 2001)
UPDATE user_role_assignments SET role_assignment_id = 4001 WHERE role_assignment_id = 1;

-- 5. 외래 키 제약 조건 검사 정상화
SET session_replication_role = 'origin';

COMMIT;