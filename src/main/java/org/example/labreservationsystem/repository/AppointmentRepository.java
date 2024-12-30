package org.example.labreservationsystem.repository;
import org.example.labreservationsystem.dox.Appointment;
import org.example.labreservationsystem.dto.LabCountByDayofweekDTO;
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
}
