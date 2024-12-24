package org.example.labreservationsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dto.LabCountDTO;
import org.example.labreservationsystem.service.AdminService;
import org.example.labreservationsystem.vo.ResultVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@CrossOrigin
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    //adminService 中有个方法 返回空闲中，维修中，使用中的设备数量
    @GetMapping("getData")
    public ResultVO accountLabByState() {
        List<LabCountDTO> labCountDTOList = adminService.countLabByState();
        log.info(labCountDTOList.toString());
        return ResultVO.success(labCountDTOList);
    }
}
