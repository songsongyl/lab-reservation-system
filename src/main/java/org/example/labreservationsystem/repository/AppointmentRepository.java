package org.example.labreservationsystem.repository;
import org.example.labreservationsystem.dox.Appointment;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.dto.Appointment1DTO;
import org.example.labreservationsystem.dto.LabCountByDayofweekDTO;
import org.example.labreservationsystem.mapper.Appointment1DTOResultSetExtractor;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment,String> {
    @Query("""
    select dayofweek,count(distinct lab_id) as quantity from appointment  group by dayofweek;
""")
    List<LabCountByDayofweekDTO> countLabByDayofweek();

    @Query("""
    select *
    from appointment a where a.teacher ->> '$.id'=:teacherId
""")
    List<Appointment> findAppointmentByTeacherId(String teacherId);

    List<Appointment> findAllByLabId(String labId);

    List<Appointment> findByTeacherAndSemester(String tid,String semester);

    @Query("""
select * from appointment a where course->>'$.id' = :cid and teacher->>'$.id' = :tid;
""")
    List<Appointment> findByCourseId(String tid,String cid);

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
}
