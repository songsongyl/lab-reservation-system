explain
select * from course c
where c.teacher_id = '1';

# 查询学期24-1第一周周一第一节可用实验室 a.week=1 and a.dayofweek = 1 and a.section = 1不能放在where里面 where会过滤掉a表记录为空的数据 变成innerjoin
explain
select * from  lab l left join appointment a on l.id = a.lab_id
and a.semester = '24-1' and a.week=1 and a.dayofweek = 1 and a.section = 1  where a.lab_id is null and l.state = 1;


explain
select * from  lab l left join appointment1 a on l.id = a.lab_id
    and a.week=1 and a.dayofweek = 1 and a.section = 1 and a.semester = '24-1' where a.lab_id is null and l.state = 1;
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
select * from lab l left join  appointment a on a.lab_id= l.id
and a.week =1 and a.dayofweek = 1 where a.lab_id is null and l.state = 1;


# 查询第一周周一可用节次 改进版
explain
select * from lab l left join  appointment a on a.lab_id= l.id
and a.week =1 and a.dayofweek = 1 where a.lab_id is null;


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

INSERT INTO `course` (id, name, quantity,  semester, clazz, type, teacher_id, experiment_hour) VALUES
('1', '高等数学', 120, '24-1', '22-数学与应用数学-1', 1, 1, 8),
('2', '大学物理', 90, '24-1', '22物理学-1', 1, 2, 6),
('3', '编程基础', 75, '24-2', '22计算机科学与技术-2', 0, 1, 8),
('4', '工程制图', 60, '24-1', '22机械工程-3', 0, 3, 8),
('5', '基础化学', 80, '23-1', '23-化学工程与工艺-1', 1, 2, 16),
('6', '英语听力', 100, '24-2', '23-英语-1', 1, 1, 8);

INSERT INTO `appointment` (id, teacher, course, lab_id,lab_name, nature, week, dayofweek, section,semester) VALUES
   ('1',
    JSON_OBJECT('1', '张三'),
    JSON_OBJECT('1', '高等数学'),
    1,'901', 2, 3, 1,1,'24-1'),
   ('2',
    JSON_OBJECT('1', '张三'),
    JSON_OBJECT('2', '大学物理'),
    2,'902', 4, 5, 2,1,'24-2'),
   ('3',
    JSON_OBJECT('2', '李四'),
    JSON_OBJECT('3', '编程基础'),
    3,'903', 6, 1, 3,1,'24-1'),
   ('4',
    JSON_OBJECT('3', '王五'),
    JSON_OBJECT('4', '工程制图'),
    1,901,8, 4, 2,2,'23-1'),
   ('5',
    JSON_OBJECT('4', '赵六'),
    JSON_OBJECT('5', '基础化学'),
    2,'902', 10, 2, 1,3,'23-2'),
   ('6',
    JSON_OBJECT('4', '赵六'),
    JSON_OBJECT('6', '英语听力'),
    4,'904',12, 6, 4,4,'24-2');

INSERT INTO `lab` (id, name, state, quantity, description, manager)
VALUES
    ('1', '901', 1, 55, 'Physics research lab', '{"id": "mngr001", "name": "John Doe"}'),
    ('2', '902', 0, 62, 'Chemistry research lab', '{"id": "mngr002", "name": "Jane Smith"}'),
    ('3', '903', 1, 70, 'Biology research lab', '{"id": "mngr003", "name": "Alice Johnson"}'),
    ('4', '904', 1, 43, 'Computer science lab', '{"id": "mngr004", "name": "Bob Lee"}'),
    ('5', '905', 0, 50, 'Mathematics lab', '{"id": "mngr005", "name": "Cathy Green"}'),
    ('6', '906', 1, 45, 'Engineering research lab', '{"id": "mngr006", "name": "David Brown"}');
