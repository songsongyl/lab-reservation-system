package org.example.labreservationsystem.repository;

import org.example.labreservationsystem.dox.User;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ListCrudRepository<User, String> {

    User findByAccount(String account);

//    @Query(value = """
//            SELECT
//a.dayofweek,
//a.section,
//a.teacher ->>'$.name' as teacherName,
//c.name as courseName,
//c.clazz ,
//CONCAT(GROUP_CONCAT(a.week ORDER BY a.week ASC)) AS weeks,
//c.experiment_hour,
//a.lab_name
//FROM
//appointment a
//JOIN
//course c ON a.teacher ->> '$.id' = c.teacher_id AND a.course ->> '$.id' = c.id
//WHERE
//a.teacher ->> '$.id' = :teacherId
//GROUP BY
//a.teacher ->> '$.id', a.course ->> '$.id',a.dayofweek,a.section, teacherName,courseName, c.clazz, c.experiment_hour, a.lab_name;
//""" , resultSetExtractorClass = Course1ResultSetExtractor.class)
//    List<Course1> findCourseByTeacherId(String teacherId);

}
