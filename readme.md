# Lab Management

## functional requirement
### User Roles and Permissions
The system should support different user roles, such as Admin, Teacher, and Student.  

Admins should have full control over the lab scheduling system, including setting lab availability and managing users.  

Teachers can reserve labs, manage their schedules, and view lab usage statistics.

###  Lab Reservation Management
The system should allow users to view available lab slots based on time, date, and lab requirements.

Users should be able to book labs for a specific duration and receive confirmations.

Users should be able to modify or cancel their bookings based on availability.

The system should prevent double-booking and allow admins to review all reservations.  

## Front-End Design

**登录**  

**首页**  

- logo 头像 欢迎回来 悬浮之后显示 退出 个人信息管理 侧边栏 课程管理 Routerview 路由跳转  

- 显示当前教师课表信息 包括周次 课程名称 实验室名称等等  

- 课程： 列表显示 更新移除 左上角加号添加课程 点击之后弹出模态框 form表单 输入课程名称 选课人数 学时 有数字限制 提交  

- 课程预约： 加载当前老师全部课程 显示课程名称 下拉菜单展示  选择课程后加载指定课程详细信息 已选/全部 8/8  
 （首页已经加载课表数据，是否需要重新请求？） --基于缓存   
  限制人数 显示全部可用实验室（实验名称） 人数装不下的单独声明 一个cell可以装多个课程   
  点击一个cell显示详细信息 模态框显示 1.复选框（选不选） 课程名称 复选框来选周次    
  

- 临时预约：考试
- 当前预约： 选择课程 渲染table 右侧垃圾桶（不需要更改 应该删掉重新预约）  



## Scheme Design

经过多次改版
~~~
create table if not exists `user`
(
    id char(26) primary key ,
    account char(10) not null unique ,
    password varchar(60) not null ,
    name varchar(6) not null , /**字符 一个中文三个字节 一个字符*/
    role char(4) not null,/**乱码长度为四个 可以用$*/
    create_time datetime  null default current_timestamp,
    update_time datetime  null default current_timestamp on update current_timestamp
);

create table if not exists `course`
(
    id char(26) primary key ,
    name varchar(6) not null ,
    major varchar(30) not null ,
    grade int unsigned not null ,/**年级*/
    class tinyint unsigned not null,/**上课班级*/
    type tinyint unsigned check ( 0 or 1),/**必修课，选修课*/
    teacher_id char(26) not null,
    credit_hour tinyint unsigned not null, /**总学时*/
    experiment_hour tinyint unsigned not null,/**实验学时,到时候可以校验一下（当老师选的学时还有剩余时）*/
    semester char(11) not null ,/**学期，用下拉框，让老师们选*/
    quantity int unsigned not null ,/*学生人数*/

    index(teacher_id)
);

create table if not exists `appointment` (
       id char(26) primary key,
       teacher json  not null comment '{id, name}',
       course json not null  comment '{id,name}',
       lab json not null  comment '{id,name}',
       nature varchar(30) not null ,/** 性质，约定为课程，临时预约等。到时候前端就用那个输入多选框约束*/
       week tinyint unsigned not null,/**周次 考虑查询效率 所以不用数组[1,3,5] 空间换时间*/
       dayofweek tinyint unsigned not null ,/**周几 */
       section tinyint unsigned not null, /**节次*/

       unique((cast(lab ->> '$.id' as char(26) )collate utf8mb4_bin),week,dayofweek,section),/*实验室id要带索引 唯一索引已经包括 移到第一位*/
       index((cast(teacher ->> '$.id' as char(26)) collate utf8mb4_bin),(cast(course ->> '$.id' as char(26)) collate utf8mb4_bin))

);

create table if not exists `lab` (
     id char(26) primary key ,
     name varchar(10) not null ,
     state tinyint unsigned check ( 0 or 1) default 1,/**被维修还是可用*/
     quantity tinyint unsigned  null ,
     description text  null,
     manager json null  comment '{id, name}',

     index(state,quantity)
);

create table if not exists `news` (
      id char(26) primary key ,
      title varchar(50) not null ,
      content text not null ,
      author varchar(50) not null ,
      create_time datetime not null default current_timestamp,
      update_time datetime not null default current_timestamp on update current_timestamp,

      index(title)
);




~~~
json 用on关联不能命中索引 改表的结构
最终版
~~~





~~~

插入测试数据 以防丢失记录 在test下新建explain.sql文件存储 表测试数据应该>6条
~~~

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

~~~


查询逻辑测试效率
~~~
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

~~~

~~~

~~~