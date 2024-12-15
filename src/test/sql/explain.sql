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
where  a.course ->> '$.id' = 1;

# 查询指定课程的预约记录
explain
select * from appointment a
where a.teacher ->> '$.id' = 1;

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

