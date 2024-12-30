package org.example.labreservationsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.dto.LabCountDTO;
import org.example.labreservationsystem.service.UserService;
import org.example.labreservationsystem.vo.ResultVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class AdminControllerTest {

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
}