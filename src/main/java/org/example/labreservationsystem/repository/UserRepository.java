package org.example.labreservationsystem.repository;

import org.example.labreservationsystem.dox.User;

import org.example.labreservationsystem.dto.Appointment1DTO;
import org.example.labreservationsystem.mapper.Appointment1DTOResultSetExtractor;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends ListCrudRepository<User, String> {

    User findByAccount(String account);

    @Query(value = """
SELECT
    a.*,c.*
FROM
    appointment a
        JOIN
    course c ON a.teacher ->> '$.id' = c.teacher_id AND a.course ->> '$.id' = c.id
WHERE
    a.teacher ->> '$.id' = :teacherId and a.semester=:semester
""" , resultSetExtractorClass = Appointment1DTOResultSetExtractor.class)
    List<Appointment1DTO> findCourseByTeacherIdAndSemester(String semester, String teacherId);

    @Query("""
select * from course c where teacher_id =:id;
""")
   User findByTeacherId(String id);

    @Query("""
select * from user u where id = :id;
""")
    User findByUserId(String id);
}
