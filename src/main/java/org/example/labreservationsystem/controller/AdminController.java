package org.example.labreservationsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dto.LabCountByDayofweekDTO;
import org.example.labreservationsystem.dto.LabCountDTO;
import org.example.labreservationsystem.service.AdminService;
import org.example.labreservationsystem.vo.ResultVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
//@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    //adminService 中有个方法 返回空闲中，维修中，使用中的设备数量
    @GetMapping( "graph2")
    public ResultVO accountLabByState() {
        log.debug("111");
        List<LabCountDTO> labCountDTOList = adminService.countLabByState();
//        log.info("Request URI: {}", request.getRequestURI());
        return ResultVO.success(labCountDTOList);
    }
    @Operation(summary = "显示实验室状态")
    @GetMapping("graph1")
    public ResultVO accountLabByStateWeek() {
        Map<String,List<?>> labState = adminService.getLabState();
        return ResultVO.success(labState);
    }

    @GetMapping("graph3")
    public ResultVO accountByDayOfWeek() {
        List<LabCountByDayofweekDTO> labCountByDayofweekDTOS =  adminService.countLabByDayofweek();
        return ResultVO.success(labCountByDayofweekDTOS);
    }
}
