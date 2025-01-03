package org.example.labreservationsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.vo.ResultVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AdminControllerTest {
    @Autowired
    private AdminController adminController;

    @Test
    public void accountLabByState() {
        log.debug(adminController.accountLabByState().toString());
    }



    @Test
    void accountLabByStateWeek() {
        ResultVO resultVO = adminController.accountLabByStateWeek();
        log.debug(resultVO.toString());
    }


    @Test
    void accountByLab() {
        ResultVO resultVO = adminController.accountByDayOfWeek();
        log.debug(resultVO.toString());
    }
    @Test
    void addUser(){
        User user = User.builder()
                .name("黄蓉")
                .account("2038782220")
                .password("123434")
                .role(User.USER)
                .telephone("12333999999")
                .build();
        adminController.postUser(user);
    }

    @Test
    void updateUserPassword(){
        adminController.putPassword("account01");

    }
}
