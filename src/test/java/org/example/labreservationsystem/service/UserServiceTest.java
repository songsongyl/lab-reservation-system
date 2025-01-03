package org.example.labreservationsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.Appointment;
import org.example.labreservationsystem.dox.Course;
import org.example.labreservationsystem.dox.Lab;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.dto.Appointment1DTO;
import org.example.labreservationsystem.dto.NewsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class UserServiceTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Test
    void getUserByAccount() {
        String account = "admin";
        User user = userService.getUserByAccount(account);
        log.debug("{}",user);
    }

    @Test
    void findAllNews() {
        List<NewsDTO> allNews = userService.findAllNews();
        log.debug("{}",allNews);
    }

    @Test
    void findAllLabs() {
        userService.findAllLabs();
        log.debug("{}",userService.findAllLabs());
    }

    @Test
    void getCourses() {
        List<Appointment1DTO> appointment1s = userService.getCourses("24-1","8");
        for(Appointment1DTO appointment1 : appointment1s) {
            log.debug("{}",appointment1);
        }

    }

    @Test
    void findCoursesByTeacherId() {
        List<Course> coursesByTeacherId = userService.findCoursesByTeacherId("8");
        log.debug("{}",coursesByTeacherId.toString());
    }

    @Test
    void addCourse() {

    }

    @Test
    void deleteCourseById() {
        userService.deleteCourseById("01JFJAFCQPJ4V5621NHTVSYWME","01JGGSN1CVW3Z8GG3ZDEE5C03Q");
    }

    @Test
    void deleteCoursesByIds() {
        List<String> ids = new ArrayList<>();
        ids.add("1");
        userService.deleteCoursesByIds("8",ids);
    }

    @Test
    void deleteAllCoursesByTeacherId() {
        userService.deleteAllCoursesByTeacherId("8");

    }

    @Test
    void updateCourse() {
    }

    @Test
    void getHours() {
        int hours = userService.getHours("8", "7");
        log.debug("{}",hours);
    }

    @Test
    void getLabs() {
        Map<String, List<Lab>> map= userService.getLabs("8","7");
        for (Map.Entry<String, List<Lab>> entry : map.entrySet()) {
            log.debug("键: " + entry.getKey());
            log.debug("对应的值列表: ");
            for (Lab lab : entry.getValue()) {
                log.debug("{}",lab);
            }

        }
    }
    //基于实验室id，查预约表
    @Test
    void getAppointment() {
        List<Appointment> appointment = userService.getAppointment("901");
        log.debug("{}",appointment.toString());
    }

    @Test
    void appointCourse() {
        Appointment a = new Appointment().builder()
                .course("{\"id\":\"9\",\"name\":\"ghj\"}")
                .teacher("{\"id\":\"8\",\"name\":\"kj\"}")
                .labId("6")
                .labName("906")
                .semester("24-1")
                .week(4)
                .dayofweek(1)
                .section(1)
                .nature("课程预约")
                .build();
        userService.appointCourse(a);

    }

    @Test
    void deleteAppointmentByTIdAndCId() {
        Appointment a = Appointment.builder().id("8").teacher("8").build();
        userService.deleteAppointmentByTIdAndCId(a);
    }

    @Test
    void updateUserPassword() {
        String account = "2022222908";
        userService.updateUserPassword("3",account);
    }

    @Test
    void getAppointmentsByTIdAndCId(){
        List<Appointment> appointmentsByCIdAndTId = userService.findAppointmentsByCIdAndTId("1", "1");
        log.debug("{}",appointmentsByCIdAndTId.toString());
    }
}