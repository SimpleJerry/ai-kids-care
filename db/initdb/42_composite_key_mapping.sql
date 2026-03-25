-- 0. 기존 매핑 데이터 초기화 및 시퀀스 리셋 (충돌 방지)
-- DELETE FROM child_class_assignments;
-- DELETE FROM class_teacher_assignments;
-- DELETE FROM class_room_assignments;
-- DELETE FROM room_camera_assignments;

SELECT setval(pg_get_serial_sequence('child_class_assignments', 'assignment_id'), 1, false);
SELECT setval(pg_get_serial_sequence('class_teacher_assignments', 'assignment_id'), 1, false);
SELECT setval(pg_get_serial_sequence('class_room_assignments', 'assignment_id'), 1, false);
SELECT setval(pg_get_serial_sequence('room_camera_assignments', 'assignment_id'), 1, false);


-- 1. child_class_assignments (약 500건 예상)
INSERT INTO child_class_assignments (kindergarten_id, child_id, class_id, start_date, end_date, reason, note, status, created_by_user_id)
WITH Ch AS (SELECT child_id, kindergarten_id, ROW_NUMBER() OVER(PARTITION BY kindergarten_id ORDER BY child_id) - 1 as rn FROM children),
     Cl AS (SELECT class_id, kindergarten_id, ROW_NUMBER() OVER(PARTITION BY kindergarten_id ORDER BY class_id) - 1 as rn, COUNT(*) OVER(PARTITION BY kindergarten_id) as cnt FROM classes)
SELECT 
    Ch.kindergarten_id, Ch.child_id, Cl.class_id, 
    CURRENT_DATE - (Ch.child_id % 60)::int, -- bigint를 int로 형변환
    CASE WHEN Ch.child_id % 10 = 0 THEN CURRENT_DATE - (Ch.child_id % 5)::int ELSE NULL END, -- bigint를 int로 형변환
    CASE Ch.child_id % 3 WHEN 0 THEN '신입원 배정' WHEN 1 THEN '연령 진급' ELSE '반 이동' END,
    CASE Ch.child_id % 4 WHEN 0 THEN '알레르기 주의' WHEN 1 THEN '적응 관찰 필요' ELSE '특이사항 없음' END,
    CASE Ch.child_id % 10 WHEN 0 THEN 'DISABLED' WHEN 1 THEN 'PENDING' ELSE 'ACTIVE' END::status_enum,
    1
FROM Ch JOIN Cl ON Ch.kindergarten_id = Cl.kindergarten_id AND (Ch.rn % NULLIF(Cl.cnt, 0)) = Cl.rn
ON CONFLICT DO NOTHING;


-- 2. class_teacher_assignments (약 125건 예상)
INSERT INTO class_teacher_assignments (kindergarten_id, class_id, teacher_id, role, start_date, reason, status, created_by_user_id)
WITH Cl AS (SELECT class_id, kindergarten_id, ROW_NUMBER() OVER(PARTITION BY kindergarten_id ORDER BY class_id) - 1 as rn FROM classes),
     T AS (SELECT teacher_id, kindergarten_id, ROW_NUMBER() OVER(PARTITION BY kindergarten_id ORDER BY teacher_id) - 1 as rn FROM teachers)
SELECT 
    Cl.kindergarten_id, Cl.class_id, T.teacher_id, 
    CASE T.teacher_id % 3 WHEN 0 THEN '부담임' ELSE '담임' END, 
    CURRENT_DATE - (T.teacher_id % 100)::int, -- bigint를 int로 형변환
    CASE T.teacher_id % 2 WHEN 0 THEN '정규 배정' ELSE '업무 분장' END,
    CASE T.teacher_id % 10 WHEN 0 THEN 'PENDING' ELSE 'ACTIVE' END::status_enum,
    1
FROM Cl JOIN T ON Cl.kindergarten_id = T.kindergarten_id AND Cl.rn = T.rn
ON CONFLICT DO NOTHING;


-- 3. class_room_assignments (약 125건 예상)
INSERT INTO class_room_assignments (kindergarten_id, class_id, room_id, start_at, purpose, status, created_by_user_id)
WITH Cl AS (SELECT class_id, kindergarten_id, ROW_NUMBER() OVER(PARTITION BY kindergarten_id ORDER BY class_id) - 1 as rn FROM classes),
     R AS (SELECT room_id, kindergarten_id, ROW_NUMBER() OVER(PARTITION BY kindergarten_id ORDER BY room_id) - 1 as rn FROM rooms)
SELECT 
    Cl.kindergarten_id, Cl.class_id, R.room_id, 
    CURRENT_TIMESTAMP - ((Cl.class_id % 30) * interval '1 day'),
    CASE Cl.class_id % 3 WHEN 0 THEN '정규반 교실' WHEN 1 THEN '오후 돌봄반' ELSE '특별 활동' END,
    CASE Cl.class_id % 10 WHEN 0 THEN 'DISABLED' ELSE 'ACTIVE' END::status_enum,
    1
FROM Cl JOIN R ON Cl.kindergarten_id = R.kindergarten_id AND Cl.rn = R.rn
ON CONFLICT DO NOTHING;


-- 4. room_camera_assignments (약 150건 예상)
INSERT INTO room_camera_assignments (kindergarten_id, camera_id, room_id, start_at, end_at)
WITH Cam AS (SELECT camera_id, kindergarten_id, ROW_NUMBER() OVER(PARTITION BY kindergarten_id ORDER BY camera_id) - 1 as rn FROM cctv_cameras),
     R AS (SELECT room_id, kindergarten_id, ROW_NUMBER() OVER(PARTITION BY kindergarten_id ORDER BY room_id) - 1 as rn, COUNT(*) OVER(PARTITION BY kindergarten_id) as cnt FROM rooms)
SELECT 
    Cam.kindergarten_id, Cam.camera_id, R.room_id, 
    CURRENT_TIMESTAMP - ((Cam.camera_id % 180) * interval '1 day'),
    CASE WHEN Cam.camera_id % 15 = 0 THEN CURRENT_TIMESTAMP - ((Cam.camera_id % 5) * interval '1 day') ELSE NULL END
FROM Cam JOIN R ON Cam.kindergarten_id = R.kindergarten_id AND (Cam.rn % NULLIF(R.cnt, 0)) = R.rn
ON CONFLICT DO NOTHING;


-- 5. user_role_assignments (약 900건 예상)
INSERT INTO user_role_assignments (user_id, role, scope_type, scope_id, status, granted_at, granted_by_user_id)
SELECT 
    user_id, 'TEACHER'::user_role_enum, 'KINDERGARTEN'::user_role_assignment_scope_type, kindergarten_id, 
    CASE user_id % 20 WHEN 0 THEN 'DISABLED' ELSE 'ACTIVE' END::status_enum, 
    CURRENT_TIMESTAMP - ((user_id % 50) * interval '1 day'), 1 
FROM teachers
ON CONFLICT (user_id, role, scope_type, scope_id) DO NOTHING;

INSERT INTO user_role_assignments (user_id, role, scope_type, scope_id, status, granted_at, granted_by_user_id)
SELECT 
    user_id, 'GUARDIAN'::user_role_enum, 'KINDERGARTEN'::user_role_assignment_scope_type, kindergarten_id, 
    CASE user_id % 20 WHEN 0 THEN 'DISABLED' ELSE 'ACTIVE' END::status_enum, 
    CURRENT_TIMESTAMP - ((user_id % 50) * interval '1 day'), 1 
FROM guardians
ON CONFLICT (user_id, role, scope_type, scope_id) DO NOTHING;