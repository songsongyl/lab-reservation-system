package org.example.labreservationsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.vo.ResultVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class UserControllerTest {
@Autowired
private UserController userController;
@Autowired
private PasswordEncoder passwordEncoder;
    @Test
    void findAllNews() {
        ResultVO allNews = userController.findAllNews();
        log.info(allNews.toString());
    }

    @Test
    void findAllLabs() {
        ResultVO allLabs = userController.findAllLabs();
        log.debug(allLabs.toString());
    }

    @Test
    void upatePassword() {
        User  user = User.builder().password("1234567").build();
        userController.updatePassword(user,"01JGKD71HD2AHX5Y9C566NYY2Y");
//        log.debug("{}",passwordEncoder.matches("123456","$2a$10$BJdTs0KzHrgtyuEU6k78Gu.yqBZDjcQRPlN1WMHNG1DPl35AL0sGO"));
    }
    //获取老师在该学期的某门课程信息
    @Test
    void findCourseBySemesterAndTIdAndCId(){
        ResultVO courseBySemester = userController.getCourseBySemester("8", "24-1", "8");
        log.debug(courseBySemester.toString());
    }
    //获取老师在该学期的全部课程信息
    @Test
    void findCourseBySemesterAndTId(){
        ResultVO courseBySemester = userController.getCoursesBySemester("8", "24-1");
        log.debug(courseBySemester.toString());
    }

}