create table if not exists `user`
(
    id char(26) primary key ,
    account char(10) not null unique ,
    password varchar(60) not null ,
    telephone char(11) not null ,
    name varchar(6) not null , /**字符 一个中文三个字节 一个字符*/
    role char(4) not null,/**乱码长度为四个 可以用$*/
    create_time datetime   default current_timestamp,
    update_time datetime  default current_timestamp on update current_timestamp
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
        lab_id char(26) not null ,
        lab_name varchar(10) not null ,
       nature varchar(4) not null ,/** 性质，约定为课程，临时预约等。到时候前端就选择而不是输入 统一用其他表示*/
       week tinyint unsigned not null,/**周次 考虑查询效率 所以不用数组[1,3,5] 空间换时间*/
       dayofweek tinyint unsigned not null ,/**周几 */
       section tinyint unsigned not null, /**节次*/

       unique(lab_id,semester,week,dayofweek,section),/*实验室id要带索引 唯一索引已经包括 移到第一位  无法命中索引 单独lab_id可以*/
       index((cast(teacher ->> '$.id' as char(26)) collate utf8mb4_bin),(cast(course ->> '$.id' as char(26)) collate utf8mb4_bin))
        /*引入水平越权之后 只能查询老师的全部预约 或者老师某门课程的预约 所以复合索引*/

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

);

create table if not exists `lab` (
     id char(26) primary key ,
     name varchar(10) not null ,
     state tinyint unsigned check ( 0 or 1) default 1,/**被维修还是可用*/
     quantity tinyint unsigned  null ,/*设备总数*/
     description varchar(500)  null,
     manager json null  comment '{id, name}',
#      equipment json comment '{quantity,[{nature}]}',
    enable_equipment tinyint unsigned null ,/*可用设备数量*/
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

