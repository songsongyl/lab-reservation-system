package org.example.labreservationsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.Lab;
import org.example.labreservationsystem.dox.News;
import org.example.labreservationsystem.dox.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
    @Test
    public  void addUser(){
        User user = User.builder()
                .name("黄蓉")
                .account("2038772222")
                .password("123434")
                .role(User.USER)
                .telephone("12333999999")
                .build();
        adminService.addSingleUser(user);
    }
    @Test
    public  void addUser2(){
        User user = User.builder()
                .name("黄蓉")
                .account("2038772221")
                .password("123434")
                .role(User.USER)
                .telephone("12333999999")
                .build();
        adminService.addUser(user);
    }
    @Test
    public  void listUsers(){
        log.debug(adminService.listUsers().toString());
    }

    @Test
    public  void resetPassword(){
        adminService.changePassword("01JGN9E486P956PHQ95H5NZ8WF","20222328");
    }

    @Test
    public  void addLab(){
        Lab lab = Lab.builder().description("sa").state((short) 1).name("910").quantity((short) 69).build();
        adminService.addLab("sqWf",lab);
    }
    @Test
    public  void findLabsByLabAdminId(){
        List<Lab> labs = adminService.findLabsByLabAdminId("mngr001");
        log.debug(labs.toString());
    }
}