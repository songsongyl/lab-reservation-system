package org.example.labreservationsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.News;
import org.example.labreservationsystem.vo.ResultVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class LabAdminControllerTest {

    @Autowired
    private LabAdminController labAdminController;



    @Test
    void updadteNews(){
        News news = News.builder().title("903维修").content("dsa").author("sda").build();
        labAdminController.updateNewsById("sqWf",news);
    }
    @Test
    void deleteNewsById() {
        labAdminController.deleteNewsById("sqWf","01JGAMXXV8DSJR0ZA4YQABYW23");
    }
    @Test
    void addNews(){
        News news = News.builder().author("sda").content("das").title("903维修").build();
        labAdminController.addNews("sqWf",news);
    }
}