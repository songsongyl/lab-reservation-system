package org.example.labreservationsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.News;
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
    @Test
    void updadteNews(){
        News news = News.builder().title("903维修").content("dsa").author("sda").build();
        adminController.updateNewsById("01JGAMXXV8DSJR0ZA4YQABYW23",news);
    }
    @Test
    void deleteNewsById() {
        adminController.deleteNewsById("01JGAMXXV8DSJR0ZA4YQABYW23");
    }
    @Test
    void addNews(){
        News news = News.builder().author("sda").content("das").title("903维修").build();
        adminController.addNews(news);
    }
}