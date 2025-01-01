package org.example.labreservationsystem.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.Appointment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class AppointmentRepository {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Test
    void findAppointmentByLabId() {
        List<Appointment> appointments = appointmentRepository.findAppointmentByLabId("1");
        for (Appointment a : appointments) {
            log.debug("{}",a);
        }
    }
    @Test
    void save() {
        Appointment a = Appointment.builder()
                .course("{\"id\":\"7\",\"name\":\"大物实验\"}")
                .teacher("{\"id\":\"8\",\"name\":\"kj\"}")
                .labId("6")
                .labName("906")
                .semester("24-1")
                .week(2)
                .dayofweek(1)
                .section(1)
                .nature("课程预约")
                .build();
        appointmentRepository.save(a);
    }
}
