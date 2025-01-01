package org.example.labreservationsystem.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.Lab;
import org.example.labreservationsystem.dto.EnableEquipmentCountDTO;
import org.example.labreservationsystem.dto.LabCountDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@Slf4j
class LabRepositoryTest {

    @Autowired
    private LabRepository labRepository;
    @Test
    void countLabByState() {
        List<LabCountDTO> labCountDTOS = labRepository.countLabByState();
        log.info(labCountDTOS.toString());
    }

    @Test
    void countEnableEquipment() {
        List<EnableEquipmentCountDTO> enableEquipmentCountDTOS = labRepository.countEnableEquipment();
        log.info(enableEquipmentCountDTOS.toString());
    }

    @Test
    void findAllLabs() {
        log.info(labRepository.findAllLabs().toString());
    }

    @Test
    void findGoodLabs() {
        List<Lab> labs = labRepository.findGoodLabs("01JFHY1JRC5BJN919YEHQYXWAR", "2");
        for(Lab lab:labs) {
            log.debug("{}",lab);
        }
    }

    @Test
    void findBadLabs() {
        List<Lab> labs = labRepository.findBadLabs("01JFHY1JRC5BJN919YEHQYXWAR", "2");
        for(Lab lab:labs) {
            log.debug("{}",lab);
        }
    }

    @Test
    void findLabs() {

    }
}