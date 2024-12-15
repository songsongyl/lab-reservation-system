explain
select * from course c
where c.teacher_id = '1';

# 查询第一周周一第一节可用实验室 a.week=1 and a.dayofweek = 1 and a.section = 1不能放在where里面 where会过滤掉a表记录为空的数据 变成innerjoin
explain
select * from  lab l left join appointment a on l.id = a.lab ->> '$.id'
and a.week=1 and a.dayofweek = 1 and a.section = 1 where a.lab ->> '$.id' is null and l.state = 1;

# 查询指定课程的预约记录
explain
select * from appointment a
where  a.course ->> '$.id' = '1';

# 查询指定课程的预约记录
explain
select * from appointment a
where a.teacher ->> '$.id' = '1';

# 查询第一周周一可用节次 这样只要一节被占用 那其他也不能查询出来
explain
select * from lab l left join  appointment a on a.lab->> '$.id'= l.id
and a.week =1 and a.dayofweek = 1 where a.lab ->> '$.id' is null and l.state = 1;


# 查询第一周周一可用节次 改进版
explain
select * from lab l left join  appointment a on a.lab->> '$.id'= l.id
and a.week =1 and a.dayofweek = 1 where a.lab ->> '$.id' is null;


INSERT INTO `lab` (id, name, state, quantity, description, manager)
VALUES
    ('1', '901', 1, 55, 'Physics research lab', '{"id": "mngr001", "name": "John Doe"}'),
    ('2', '902', 0, 62, 'Chemistry research lab', '{"id": "mngr002", "name": "Jane Smith"}'),
    ('3', '903', 1, 70, 'Biology research lab', '{"id": "mngr003", "name": "Alice Johnson"}'),
    ('4', '904', 1, 43, 'Computer science lab', '{"id": "mngr004", "name": "Bob Lee"}'),
    ('5', '905', 0, 50, 'Mathematics lab', '{"id": "mngr005", "name": "Cathy Green"}'),
    ('6', '906', 1, 45, 'Engineering research lab', '{"id": "mngr006", "name": "David Brown"}');

INSERT INTO `user` (id, name, account, password, role, create_time, update_time) VALUES
     ('1', '张三', 'account01', 'pass123456', '用户', '2024-12-15 12:00:00', '2024-12-15 12:00:00'),
     ('2', '李四', 'account02', 'abc1234567', '管理员', '2024-12-14 09:30:00', '2024-12-15 08:45:00'),
     ('3', '王五', 'account03', 'pass567890', '用户', '2024-12-13 18:00:00', '2024-12-14 07:50:00'),
     ('4', '赵六', 'account04', 'password01', '游客', '2024-12-12 10:20:00', '2024-12-13 15:40:00'),
     ('5', '钱七', 'account05', 'qwerty1234', '用户', '2024-12-11 08:15:00', '2024-12-11 08:15:00'),
     ('6', '孙八', 'account06', 'zxcvbn0987', '管理员', '2024-12-10 13:45:00', '2024-12-15 14:20:00');

INSERT INTO `course` (id, name, quantity,  semester, major, grade, class, type, teacher_id, credit_hour, experiment_hour) VALUES
('1', '高等数学', 120, '2024-2025秋季', '数学与应用数学', 2024, 1, 0, '1', 64, 16),
('2', '大学物理', 90, '2024-2025春季', '物理学', 2023, 2, 0, '1', 48, 12),
('3', '编程基础', 75, '2024-2025秋季', '计算机科学与技术', 2024, 1, 1, '2', 56, 20),
('4', '工程制图', 60, '2024-2025春季', '机械工程', 2022, 3, 0, '3', 40, 8),
('5', '基础化学', 80, '2024-2025秋季', '化学工程与工艺', 2023, 2, 1, '4', 52, 10),
('6', '英语听力', 100, '2024-2025春季', '英语', 2024, 1, 1, '4', 60, 0);

INSERT INTO `appointment` (id, teacher, course, lab, nature, week, dayofweek, section) VALUES
   ('1',
    JSON_OBJECT('1', '张三'),
    JSON_OBJECT('1', '高等数学'),
    JSON_OBJECT('1','901'),
    '课程预约', 2, 3, 1),
   ('2',
    JSON_OBJECT('1', '张三'),
    JSON_OBJECT('2', '大学物理'),
    JSON_OBJECT('2', '902'),
    '临时预约', 4, 5, 2),
   ('3',
    JSON_OBJECT('2', '李四'),
    JSON_OBJECT('3', '编程基础'),
    JSON_OBJECT('3', '903'),
    '课程预约', 6, 1, 3),
   ('4',
    JSON_OBJECT('3', '王五'),
    JSON_OBJECT('4', '工程制图'),
    JSON_OBJECT('4', '904'),
    '课程预约', 8, 4, 2),
   ('5',
    JSON_OBJECT('4', '赵六'),
    JSON_OBJECT('5', '基础化学'),
    JSON_OBJECT('5', '905'),
    '课程预约', 10, 2, 1),
   ('6',
    JSON_OBJECT('4', '赵六'),
    JSON_OBJECT('6', '英语听力'),
    JSON_OBJECT('6', '906'),
    '临时预约', 12, 6, 4);

INSERT INTO `lab` (id, name, state, quantity, description, manager)
VALUES
    ('1', '901', 1, 55, 'Physics research lab', '{"id": "mngr001", "name": "John Doe"}'),
    ('2', '902', 0, 62, 'Chemistry research lab', '{"id": "mngr002", "name": "Jane Smith"}'),
    ('3', '903', 1, 70, 'Biology research lab', '{"id": "mngr003", "name": "Alice Johnson"}'),
    ('4', '904', 1, 43, 'Computer science lab', '{"id": "mngr004", "name": "Bob Lee"}'),
    ('5', '905', 0, 50, 'Mathematics lab', '{"id": "mngr005", "name": "Cathy Green"}'),
    ('6', '906', 1, 45, 'Engineering research lab', '{"id": "mngr006", "name": "David Brown"}');
