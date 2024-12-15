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
    telephone char(11) not null ,
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
       nature char(30) not null ,/** 性质，约定为课程，临时预约等。到时候前端就用那个输入多选框约束*/
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
     introduction text  null,
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

插入测试数据 以防丢失记录 在test下新建explain.sql文件存储 表测试数据应该>6条
~~~
explain
INSERT INTO `user` (id, account, password, telephone, role, create_time, update_time)
VALUES
    ('NzqqfNEu3ET07lulQ5dGuHzpnh', 'user001', 'password123', '13800000001', '001', '2024-12-12 10:00:00', '2024-12-12 10:00:00'),
    ('LAoe4Ica3aEkzwRXYgiZXqQLqs', 'user002', 'password456', '13800000002', '002', '2024-12-12 11:00:00', '2024-12-12 11:00:00'),
    ('kTV2fG9hbeZGUciQyRldCsStKp', 'user003', 'password789', '13800000003', '003', '2024-12-12 12:00:00', '2024-12-12 12:00:00'),
    ('XDFBEp0Xp0TdUSLxt6ZEcmCZpK', 'user004', 'password000', '13800000004', '001', '2024-12-12 13:00:00', '2024-12-12 13:00:00'),
    ('tyUhAq3iFbxqk1C8uJZcBDdHsq', 'user005', 'password111', '13800000005', '002', '2024-12-12 14:00:00', '2024-12-12 14:00:00'),
    ('eaXSt5KjgeOvug41SJJWGJNamt', 'user006', 'password222', '13800000006', '003', '2024-12-12 15:00:00', '2024-12-12 15:00:00'),
    ('eDIv4sk7duduj7qxkM9sFb2jLH', 'user007', 'password333', '13800000007', '001', '2024-12-12 16:00:00', '2024-12-12 16:00:00'),
    ('maGPIj4W3rUtLDn79xh6h57O4X', 'user008', 'password444', '13800000008', '002', '2024-12-12 17:00:00', '2024-12-12 17:00:00'),
    ('iqV7Y3CzUY6s4iwx3j3If56d0b', 'user009', 'password555', '13800000009', '003', '2024-12-12 18:00:00', '2024-12-12 18:00:00'),
    ('Cw9H2rPXKeQTfa8WVnEhwsJZOd', 'user010', 'password666', '13800000010', '001', '2024-12-12 19:00:00', '2024-12-12 19:00:00');


~~~


查询逻辑测试效率
~~~
explain
select * from user u
where u.id = '1';

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

~~~

~~~

~~~