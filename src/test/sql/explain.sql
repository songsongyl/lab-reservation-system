explain
select * from user u
join `2022222994`.course c on c.teacher_id = u.id
join appointment a on c.id = a.course_id
join `2022222994`.lab on a.lab_id = lab.id
where u.id = '1';


explain
select * from appointment

