package org.example.labreservationsystem.component;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class ULIDTest {
    @Autowired
    private ULID ulid;


    @Test
    void nextULID() {
        log.info(ulid.nextULID());
    }
}