package org.example.labreservationsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.News;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class AdminServiceTest {
    @Autowired
    private AdminService adminService;

    @Test
   public void countLabByState() {
       log.debug(adminService.countLabByState().toString());
    }

}