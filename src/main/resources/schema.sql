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

