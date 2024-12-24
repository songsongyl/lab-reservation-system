package org.example.labreservationsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dto.LabCountDTO;
import org.example.labreservationsystem.repository.AdminRepository;
import org.example.labreservationsystem.vo.ResultVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    @Transactional
    public List<LabCountDTO> countLabByState() {
        log.debug(adminRepository.countLabByState().toString());
        return adminRepository.countLabByState();
    }

}
