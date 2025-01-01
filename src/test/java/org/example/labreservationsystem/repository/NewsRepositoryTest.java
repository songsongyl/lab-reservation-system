package org.example.labreservationsystem.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.News;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class NewsRepositoryTest {
@Autowired
private NewsRepository newsRepository;
    @Test
    void findAllNews() {
        List<News> news = (List<News>) newsRepository.findAll();
        log.info(news.toString());

    }

    @Test
    void deleteById() {
        newsRepository.deleteById("01JGAMXXV8DSJR0ZA4YQABYW23");
    }


    @Test
    void saveNews() {
        News news = News.builder()
                .author("lks")
                .content("kkkkkkkkkkkkkkkkkkkkk")
                .title("908教室第一个设备在维修中")
                .build();
        newsRepository.save(news);
    }
}