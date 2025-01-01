package org.example.labreservationsystem.mapper;
import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.Appointment;
import org.example.labreservationsystem.dox.Course;
import org.example.labreservationsystem.dto.Appointment1DTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Appointment1DTOResultSetExtractor implements ResultSetExtractor<List<Appointment1DTO>> {

    @Override
    public List<Appointment1DTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Appointment1DTO> appointment1s = new ArrayList<>();
        while (rs.next()) {
           /*  读取 weeks 列的 JSON 字符串
            String weeksJson = rs.getString("weeks");
             将 JSON 字符串转换为 List<Integer>
            List<Integer> weeks = null;
            try {
                weeks = objectMapper.readValue(weeksJson, objectMapper.getTypeFactory().constructCollectionType(List.class, Integer.class));
            } catch (Exception e) {
                log.error("Error parsing weeks JSON: " + weeksJson, e);
            }*/
            Course c = Course.builder()
                    .id(rs.getString("c.id"))
                    .name(rs.getString("name"))
                    .teacherId(rs.getString("teacher_id"))
                    .clazz(rs.getString("clazz"))
                    .experimentHour(rs.getInt("experiment_hour"))
                    .quantity(rs.getInt("quantity"))
                    .type(rs.getInt("type"))
                    .semester(rs.getString("c.semester"))
                    .build();
            Appointment appointment = Appointment.builder()
                    .id(rs.getString("a.id"))
                    .teacher(rs.getString("teacher"))
                    .course(rs.getString("course"))
                    .labId(rs.getString("lab_id"))
                    .labName(rs.getString("lab_name"))
                    .semester(rs.getString("a.semester"))
                    .nature(rs.getString("nature"))
                    .week(rs.getInt("week"))
                    .dayofweek(rs.getInt("dayofweek"))
                    .section(rs.getInt("section"))
                    .build();
            Appointment1DTO appointment1 = new Appointment1DTO().builder()
                    .appointment(appointment)
                    .course(c)
                    .build();
            appointment1s.add(appointment1);
        }
        return appointment1s;
    }
}
