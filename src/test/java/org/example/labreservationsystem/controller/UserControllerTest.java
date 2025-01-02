package org.example.labreservationsystem.controller;

import lombok.extern.slf4j.Slf4j;
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
        userController.updatePassword("123456","8");
        log.debug("{}",passwordEncoder.matches("123456","$2a$10$BdfPDfbinax3rnxNCTTb7uzaH29B9UMPLgGZE3.5deECWCoqsqiPq"));
    }
}