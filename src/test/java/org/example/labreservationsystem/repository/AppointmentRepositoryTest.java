package org.example.labreservationsystem.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.Appointment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class AppointmentRepositoryTest {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Test
    void findAppointmentByLabId() {
        List<Appointment> appointments = appointmentRepository.findAllByLabId("901");
        for (Appointment a : appointments) {
            log.debug("{}",a);
        }
    }
    @Test
    void save() {
        //转义符
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
        //三引号
        Appointment appointment = Appointment.builder()
                .teacher("""
                {"id":"01JFXVYSMCG63TYK72DP1GBT25","name":"杨过"}
                """)
                .course("""
                {"id":"432","name":"大数据"}
                """)
                .semester("24-1")
                .nature("1")
                .labId("5f6a4e8d6c40437a8f22c8c9")
                .labName("嵌入式系统实验室")
                .week(2)
                .dayofweek(2)
                .section(2)
                .build();
        appointmentRepository.save(appointment);
    }
    @Test
    void delete() {
        appointmentRepository.deleteById("1");
    }
    @Test
    void find(){
        List<Appointment> appointmentByTeacherId = appointmentRepository.findAppointmentByTeacherId("2");
        log.debug("{}",appointmentByTeacherId);
    }
    @Test
    void findByTeacherIdAndSemester(){
        List<Appointment> byTeacherAndSemester = appointmentRepository.findByTeacherAndSemester("8" +
                "", "24-1");
        log.debug("{}",byTeacherAndSemester.toString());
    }
}
