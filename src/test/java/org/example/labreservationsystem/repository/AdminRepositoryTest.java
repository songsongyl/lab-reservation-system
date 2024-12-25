package org.example.labreservationsystem.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class AdminRepositoryTest {
    @Autowired
    private AdminRepository adminRepository;

    @Test
   public void countLabByState() {
        log.debug("countLabByState");
        log.debug(adminRepository.countLabByState().toString());
        log.debug("22");
        assertFalse(adminRepository.countLabByState().isEmpty(), "查询结果不应该为空");
    }
}