explain
select * from course c
where c.teacher_id = '1';

# 查询学期24-1第一周周一第一节可用实验室 a.week=1 and a.dayofweek = 1 and a.section = 1不能放在where里面 where会过滤掉a表记录为空的数据 变成innerjoin
# 错误版本 无法命中索引
# explain
# select * from  lab l left join appointment1 a on l.id = a.lab ->> '$.id'
# and a.semester = '2026' and a.week=1 and a.dayofweek = 1 and a.section = 1  where a.lab ->> '$.id' is null and l.state = 1;
#
# 将lab拍平 以冗余字段存在 可以命中索引 方法1
explain
select * from  lab l left join appointment a on l.id = a.lab_id
and a.semester = '2026' and a.week=1 and a.dayofweek = 1 and a.section = 1  where a.lab_id is null and l.state = 1;
# 方法2
select * from  lab l left join appointment a on l.id = a.lab_id
and a.semester = '2026' and a.week=1 and a.dayofweek = 1 and a.section = 1  where isnull(a.lab_id ) and l.state = 1;

# 查询uid=1的老师预约的实验室
explain
select u.name ,l.name,a.week,a.dayofweek,a.section,c.name from user u join course c on teacher_id = u.id
join appointment a on a.course ->> '$.id' = c.id
join lab l on l.id = a.lab_id
where u.id = '1';

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

explain
SELECT state, count(state) as quantity from lab group by state;

SELECT
    CASE
        WHEN state = '0' THEN 'repairLab'
        WHEN state = '1' THEN 'leisureLab'
        ELSE 'useLab'
        END AS state_name,
    count(state) AS quantity
FROM lab
GROUP BY state;

-- 1.指定老师的课表,查课程信息，班级，实验室，周次，星期，节次，a表，并course表查班级?
explain
select * from appointment a
where a.teacher ->> '$.id' = '1' and a.course ->> '$.id' = '1';



-- 2.基于学期/周/星期/节，查询可用教室
explain
select * from lab l
                  left join appointment a
                            on l.id=a.lab_id and a.semester='24-1' and a.week=1 and a.dayofweek=2 and a.section=3
where a.lab_id is null and l.state = 1;

-- 3.基于周/星期，查询空闲节可用教室
# CROSS JOIN 是一种笛卡尔积连接，将 lab 表中的每一行与 s 表中的每一行连接。s 表是一个内联的子查询，它生成了一个包含四个值（1、2、3、4）的虚拟表，表示四个节次。
# 这个 CROSS JOIN 的目的是生成一个实验室和每个节次的组合。
# GROUP_CONCAT 是一个聚合函数，它将同一组内的多个节次连接成一个字符串。
# ORDER BY s.section 确保返回的节次是按升序排列的。
# 结果会给出一个字段 free_sections，表示每个实验室的未预约节次，多个节次用逗号隔开。
explain
SELECT
    l.id AS lab_id,
    l.name AS lab_name,
    GROUP_CONCAT(s.section ORDER BY s.section) AS free_sections
FROM
    lab l
        CROSS JOIN
    (SELECT 1 AS section UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) s
        LEFT JOIN
    appointment a
    ON
        l.id = a.lab_id
            AND a.semester = '24-1' -- 替换为具体学期
            AND a.week = 1        -- 替换为具体周次
            AND a.dayofweek = 2    -- 替换为具体星期（1=周一，7=周日）
            AND a.section = s.section
WHERE
    l.state = 1 -- 实验室状态为可用
  AND a.id IS NULL -- 节次无预约
GROUP BY
    l.id, l.name;

-- 4.根据老师id查出该老师所有的预约课程
# JSON_EXTRACT(a.teacher, '$.id') AS teacher_id：
# 从 teacher 字段中提取 id，并将其命名为 teacher_id。
# 假设 teacher 字段是一个 JSON 格式的数据，$.id 表示提取 teacher 对象中的 id 字段。
explain
SELECT
    a.id AS appointment_id,
    JSON_EXTRACT(a.teacher, '$.id') AS teacher_id,
    JSON_EXTRACT(a.teacher, '$.name') AS teacher_name,
    JSON_EXTRACT(a.course, '$.id') AS course_id,
    JSON_EXTRACT(a.course, '$.name') AS course_name,
    a.semester,
    a.nature,
    a.lab_id,
    a.lab_name,
    a.week,
    a.dayofweek,
    a.section
FROM
    appointment a
WHERE
    JSON_EXTRACT(a.teacher, '$.id') = '1'; -- 替换为具体老师ID

-- 5.根据老师id和指定学期查出该老师该学期所有的预约课程
SELECT
    a.id AS appointment_id,
    JSON_EXTRACT(a.teacher, '$.id') AS teacher_id,
    JSON_EXTRACT(a.teacher, '$.name') AS teacher_name,
    JSON_EXTRACT(a.course, '$.id') AS course_id,
    JSON_EXTRACT(a.course, '$.name') AS course_name,
    a.semester,
    a.nature,
    a.lab_id,
    a.lab_name,
    a.week,
    a.dayofweek,
    a.section
FROM
    appointment a
WHERE
    JSON_EXTRACT(a.teacher, '$.id') = '1' -- 替换为具体老师ID
  AND a.semester = '24-1'; -- 替换为具体学期

-- 6.根据课程id和老师id查某个老师某个学期某门课的预约情况
SELECT
    a.id AS appointment_id,
    JSON_EXTRACT(a.teacher, '$.id') AS teacher_id,
    JSON_EXTRACT(a.teacher, '$.name') AS teacher_name,
    JSON_EXTRACT(a.course, '$.id') AS course_id,
    JSON_EXTRACT(a.course, '$.name') AS course_name,
    a.semester,
    a.nature,
    a.lab_id,
    a.lab_name,
    a.week,
    a.dayofweek,
    a.section
FROM
    appointment a
WHERE
    JSON_EXTRACT(a.teacher, '$.id') = '1' -- 替换为具体老师ID
  AND JSON_EXTRACT(a.course, '$.id') = '1' -- 替换为具体课程ID
  AND a.semester = '24-1'; -- 替换为具体学期

# 7、加载当前教师全部课程，基于id，course表
select * from course c
where c.teacher_id=1;
# 8、加载人数可用实验室，基于教师id.课程id,查可用实验室?
select a.lab_name from appointment a
                           join lab l
                                on l.id = a.lab_id
                           join course c
                                on a.course ->> '$.id'=c.id
where a.teacher ->> '$.id' = '1' and a.course->>'$.id' = '1'and l.state=1 and c.quantity<l.quantity;
# 9、加载全部可用实验室，基于教师id.课程id,仅加载名称
select l.name from lab l
where l.state = 1;
# 10、基于指定教室，加载课表
select * from appointment a
where a.lab_name='901';
# 11、基于教师id，课程id，查询预约信息
explain
select *
from appointment a
where a.teacher ->> '$.id'='1' and a.course ->> '$.id'='1';
# 12、基于老师id，课程id，查所有周次 全索引index
explain
SELECT
    CAST(teacher ->> '$.id' AS CHAR(26)) AS teacher_id,
    CAST(course ->> '$.id' AS CHAR(26)) AS course_id,
    CONCAT(GROUP_CONCAT(week ORDER BY week ASC)
    ) AS weeks
FROM
    appointment a
where a.teacher ->> '$.id'='1' and a.course ->> '$.id'='1'
GROUP BY
    teacher_id, course_id;
# 13、基于老师id，课程id，查课表
explain
SELECT
    a.*,c.*

FROM
    appointment a
        left JOIN
    course c ON a.teacher ->> '$.id' = c.teacher_id AND a.course ->> '$.id' = c.id
WHERE
    a.teacher ->> '$.id' = '01JFJ5CWY6FD4XTTHR42FBS6A4'and a.semester='24-2';
explain
SELECT
    a.teacher ->>'$.name' as teacherName,
    c.name as courseName,
    c.clazz ,
    CONCAT(GROUP_CONCAT(a.week ORDER BY a.week ASC)) AS weeks,
    c.experiment_hour,
    a.lab_name
FROM
    appointment a
        JOIN
    course c ON a.teacher ->> '$.id' = c.teacher_id AND a.course ->> '$.id' = c.id
WHERE
    a.teacher ->> '$.id' = '01JFJ5CWY6FD4XTTHR42FBS6A4'
GROUP BY
    a.teacher ->> '$.id', a.course ->> '$.id', teacherName,courseName, c.clazz, c.experiment_hour, a.lab_name;
explain
SELECT
    a.dayofweek,
    a.section,
    a.teacher,
    a.course,
    c.clazz,
    JSON_ARRAYAGG(a.week) AS weeks,  -- 使用JSON_ARRAYAGG函数将week值聚合成JSON数组
    c.experiment_hour,
    a.lab_id,
    a.lab_name
FROM
    appointment a
        JOIN
    course c ON a.teacher ->> '$.id' = c.teacher_id AND a.course ->> '$.id' = c.id
WHERE
    a.teacher ->> '$.id' = '01JFJ5CWY6FD4XTTHR42FBS6A4'
GROUP BY
    a.teacher ->> '$.id', a.course ->> '$.id',a.dayofweek,a.section, a.teacher,a.course, c.clazz, c.experiment_hour, a.lab_id,a.lab_name;
# 14、基于老师id，课程id，查a表中有几条记录，乘2即已经选了多少课时lab
explain
SELECT COUNT(*) AS record_count
FROM `appointment`
WHERE teacher ->> '$.id' = '01JFJ5CWY6FD4XTTHR42FBS6A4'
  AND course ->> '$.id' = '1';
# 15、基于老师id，课程id，渲染状态可用，人数可用教室
explain
select l.id,l.name,l.state,l.quantity,l.description,l.enable_equipment,l.manager from lab l
                                                                                          join appointment a on a.lab_id = l.id
                                                                                          join course c on a.course ->> '$.id' = c.id
where l.state = 1 and a.teacher ->> '$.id' = '01JFMF27TJ1TF9YQ240HG7P7EW'and a.course ->> '$.id'='01JG8N7SWX4V02GKEQCJ8K81S8' and c.quantity < l.quantity;
explain
SELECT l.*
FROM `lab` l, `course` c
WHERE l.quantity >= c.quantity
  AND c.teacher_id = '01JFHY1JRC5BJN919YEHQYXWAR'
  AND c.id = '2'and l.state =1;
# 16、基于老师id，课程id，渲染状态可用，人数不够教室
explain
SELECT l.*
FROM lab l
         join  course c on l.quantity < c.quantity
WHERE c.teacher_id = '01JFHY1JRC5BJN919YEHQYXWAR'and c.id = '2'and l.state =1;

delete from course c where c.teacher_id='10' and c.id='4';
# 17、基于老师id，课程id查预约表中记录
SELECT COUNT(*) AS record_count
FROM `appointment`
WHERE teacher ->> '$.id' = '01JFJ5CWY6FD4XTTHR42FBS6A4'
  AND course ->> '$.id' = '1';
# 18、基于实验室id，渲染预约表
explain
select * from appointment a where a.lab_id = '1';


SELECT
    a.*,c.*
FROM
    appointment a
        JOIN
    course c ON a.teacher ->> '$.id' = c.teacher_id AND a.course ->> '$.id' = c.id
WHERE
    a.teacher ->> '$.id' = '8' and a.semester='24-1';






INSERT INTO `lab` (id, name, state, quantity, description, manager,enable_equipment)
VALUES
    ('1', '901', 1, 55, 'Physics research lab', '{"id": "mngr001", "name": "John Doe"}',50),
    ('2', '902', 0, 62, 'Chemistry research lab', '{"id": "mngr002", "name": "Jane Smith"}',40),
    ('3', '903', 1, 70, 'Biology research lab', '{"id": "mngr003", "name": "Alice Johnson"}',66),
    ('4', '904', 1, 43, 'Computer science lab', '{"id": "mngr004", "name": "Bob Lee"}',40),
    ('5', '905', 0, 50, 'Mathematics lab', '{"id": "mngr005", "name": "Cathy Green"}',45),
    ('6', '906', 1, 45, 'Engineering research lab', '{"id": "mngr006", "name": "David Brown"}',30);

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
