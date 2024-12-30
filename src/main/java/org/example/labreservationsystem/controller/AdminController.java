package org.example.labreservationsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @GetMapping( "graph")
    public ResultVO accountLabByState() {
        log.debug("111");
        List<LabCountDTO> labCountDTOList = adminService.countLabByState();
//        log.info("Request URI: {}", request.getRequestURI());
        return ResultVO.success(labCountDTOList);
    }
    @Operation(summary = "显示实验室状态")
    @GetMapping("labstate")
    public ResultVO accountLabByStateWeek() {
        Map<String,List<?>> labState = adminService.getLabState(2);
        return ResultVO.success(labState);
    }

    @Operation(summary = "查看所有公告")
    @GetMapping("news")
    public ResultVO findAllNews() {
        return ResultVO.success(adminService.findAllNews());
    }
    @Operation(summary = "查看所有实验室信息")
    @GetMapping("labs")
    public ResultVO findAllLabs() {
        return ResultVO.success(adminService.findAllLabs());
    }

}
