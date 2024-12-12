create table if not exists `user`
(
    id char(26) primary key ,
    account varchar(10) not null unique ,
    password varchar(20) not null ,
    telephone char(11) not null ,
    role char(3) not null,/**乱码长度为三*/
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp
);

create table if not exists `course`
(

    id char(26) primary key ,
    course_id varchar(10) not null ,/**课程号*/
    name varchar(20) not null ,
    major varchar(30) not null ,
    grade int unsigned not null ,/**年级*/
    class int unsigned not null,/**上课班级*/
    type tinyint unsigned check ( 0 or 1),/**必修课，选修课*/
    teacher_id char(26) not null,
    credit_hour tinyint unsigned not null, /**总学时*/
    experiment_hour tinyint unsigned not null,/**实验学时,到时候可以校验一下（当老师选的学时还有剩余时）*/
    semester char(11) not null ,/**学期，用下拉框，让老师们选*/
    student_number int unsigned not null ,

    index(teacher_id)
);

create table if not exists `timetable` (
       id char(26) primary key,
       teacher json  not null comment '{id, name,major}',
       lab_id char(26) not null ,
       nature char(30) not null ,/** 性质，约定为课程，临时预约等。到时候前端就用那个输入多选框约束*/
       week tinyint unsigned not null,/**非负小整数*/
       dayofweek tinyint unsigned not null /**非负小整数 */,
       lesson tinyint unsigned/**非负int*/,
       unique(lab_id,week,dayofweek,lesson),
       index ((cast(teacher ->> '$.id' as char(26)) collate utf8mb4_bin))

# 备用方案

);

create table if not exists `lab` (
     id char(26) primary key ,
     name int not null ,
     state tinyint unsigned check ( 0 or 1),/**被维修还是可用*/
     seat_number int unsigned not null ,
     introduction varchar(1000) not null,
     manager json not null comment '{id, name}',
     index(state,seat_number)
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

