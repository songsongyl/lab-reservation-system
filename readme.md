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

**注册与登录**  

- 默认admin登录 验证码
**首页**  

- logo 头像 欢迎回来 悬浮之后显示 退出 个人信息管理 侧边栏 课程管理 Routerview 路由跳转  

- 显示当前教师课表信息 包括周次 课程名称 实验室名称等等  

- 课程： 列表显示 更新移除 左上角加号添加课程 点击之后弹出模态框 form表单 输入课程名称 选课人数 学时 有数字限制 提交  

- 实验室预约： 加载当前老师全部课程 显示课程名称 下拉菜单展示  选择课程后加载指定课程详细信息 已选/全部 8/8  
 （首页已经加载课表数据，是否需要重新请求？） --基于缓存   
  限制人数 显示全部可用实验室（实验名称） 人数装不下的单独声明 一个cell可以装多个课程   
  点击一个cell显示详细信息 模态框显示 1.复选框（选不选） 课程名称 复选框来选周次    
  

- 临时预约：考试
- 当前预约： 选择课程 渲染table 右侧垃圾桶（不需要更改 应该删掉重新预约）    
-  数据库操作校验  水平越权(老师删除课程 要从token拿出teacher_id 结合course_id 才能防止水平越权 但是每个语句都要写 所以要写通用代码 如果不一致 证明经过篡改 所以抛出异常)  
- 系统管理：设备管理
- 统计

- 业务逻辑划分：
- 管理员无需添加课程 也不用预约
- 教师无需添加用户，实验室
- 操作与角色权限密不可分 所以按照角色划分   
userService teacherService adminService


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
问题：json 用on关联不能命中索引 改表的结构 以冗余字段存在 多插入几条数据就能命中索引  


最终版  
~~~
create table if not exists `user`
(
    id char(26) primary key ,
    account char(10) not null unique ,
    password varchar(60) not null ,
    telephone char(11) not null ,
    name varchar(6) not null , /**字符 一个中文三个字节 一个字符*/
    role char(4) not null,/**乱码长度为四个 可以用$*/
    create_time datetime  null default current_timestamp,
    update_time datetime  null default current_timestamp on update current_timestamp
);

create table if not exists `course`
(
    id char(26) primary key ,
    name varchar(20) not null ,
   clazz varchar(30) not null /*上课年级专业班级*/,
    type tinyint unsigned check ( 0 or 1),/**必修课，选修课*/
    teacher_id char(26) not null,
    experiment_hour tinyint unsigned not null,/**实验学时,到时候可以校验一下（当老师选的学时还有剩余时）*/
    semester char(4) not null ,/**学期，用下拉框，让老师们选 24-1 默认*/
    quantity tinyint unsigned not null ,/*学生人数*/

    index(teacher_id,semester)
);

create table if not exists `appointment` (
       id char(26) primary key,
       teacher json  not null comment '{id, name}',
       course json not null  comment '{id,name}',
        semester char(4) not null ,/*学期*/
#        lab json not null  comment '{id,name}',
        lab_id char(26) not null ,
        lab_name varchar(10) not null ,
       nature varchar(4) not null ,/** 性质，约定为课程，临时预约等。到时候前端就选择而不是输入 统一用其他表示*/
       week tinyint unsigned not null,/**周次 考虑查询效率 所以不用数组[1,3,5] 空间换时间*/
       dayofweek tinyint unsigned not null ,/**周几 */
       section tinyint unsigned not null, /**节次*/

       unique(lab_id,semester,week,dayofweek,section),/*实验室id要带索引 唯一索引已经包括 移到第一位  无法命中索引 单独lab_id可以*/
       index((cast(teacher ->> '$.id' as char(26)) collate utf8mb4_bin),(cast(course ->> '$.id' as char(26)) collate utf8mb4_bin))


);

create table if not exists `appointment1` (
     id char(26) primary key,
     teacher json  not null comment '{id, name}',
     course json not null  comment '{id,name}',
     semester char(4) not null ,/*学期*/
     lab json comment '{id,name}' not null ,
     nature varchar(4) not null ,/** 性质，约定为课程，临时预约等。到时候前端就选择而不是输入 统一用其他表示*/
     week tinyint unsigned not null,/**周次 考虑查询效率 所以不用数组[1,3,5] 空间换时间*/
     dayofweek tinyint unsigned not null ,/**周几 */
     section tinyint unsigned not null, /**节次*/

     unique((cast(lab ->> '$.id' as char(26)) collate utf8mb4_bin),semester,week,dayofweek,section),/*实验室id要带索引 唯一索引已经包括 移到第一位  无法命中索引 单独lab_id可以*/
     index((cast(teacher ->> '$.id' as char(26)) collate utf8mb4_bin),(cast(course ->> '$.id' as char(26)) collate utf8mb4_bin))
  /*引入水平越权之后 只能查询老师的全部预约 或者老师某门课程的预约 所以复合索引*/
);

create table if not exists `lab` (
     id char(26) primary key ,
     name varchar(10) not null ,
     state tinyint unsigned check ( 0 or 1) default 1,/**被维修还是可用*/
     quantity tinyint unsigned  null ,
     description varchar(500)  null,
     manager json null  comment '{id, name}',

     index(state)
);

create table if not exists `news` (
      id char(26) primary key ,
      title varchar(50) not null ,
      content varchar(2000) not null ,
      author varchar(50) not null ,
      create_time datetime not null default current_timestamp,
      update_time datetime not null default current_timestamp on update current_timestamp

);





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

~~~


查询逻辑测试效率（一）  
~~~
explain
select * from course c
where c.teacher_id = '1';

# 查询学期24-1第一周周一第一节可用实验室 a.week=1 and a.dayofweek = 1 and a.section = 1不能放在where里面 where会过滤掉a表记录为空的数据 变成innerjoin
explain
select * from  lab l left join appointment a on l.id = a.lab_id
and a.semester = '2026' and a.week=1 and a.dayofweek = 1 and a.section = 1  where a.lab_id is null and l.state = 1;


# 查询指定课程的预约记录
explain
select * from appointment a
where  a.course ->> '$.id' = '1';

# 查询指定课程的预约记录
explain
select * from appointment a
where a.teacher ->> '$.id' = '1';
~~~


查询逻辑测试效率（二）

~~~

~~~