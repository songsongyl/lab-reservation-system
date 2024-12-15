explain
select * from user u
where u.id = '1';

# 查询第一周周一第一节可用实验室
explain
select * from  lab l left join appointment a on l.id = a.lab ->> '$.id'
and a.week=1 and a.dayofweek = 1 and a.section = 1 where a.lab ->> '$.id' is null and l.state = 1;


explain
select * from appointment a
where  a.course ->> '$.id' = 1;

explain
select * from appointment a
where a.teacher ->> '$.id' = 1;

# 查询第一周周一可用节次 这样只要一节被占用 那其他也不能查询出来
explain
select * from lab l left join  appointment a on a.lab->> '$.id'= l.id
and a.week =1 and a.dayofweek = 1 where a.lab ->> '$.id' is null and l.state = 1;


# 改进版
explain
select * from lab l left join  appointment a on a.lab->> '$.id'= l.id
and a.week =1 and a.dayofweek = 1 where a.lab ->> '$.id' is null and l.state = 1;
