package org.example.labreservationsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.service.UserService;
import org.example.labreservationsystem.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
//@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
@RequestMapping("/api/users/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Operation(summary = "查看所有公告")
    @GetMapping("news")
    public ResultVO findAllNews() {
        return ResultVO.success(userService.findAllNews());
    }
    @Operation(summary = "查看所有实验室信息")
    @GetMapping("labs")
    public ResultVO findAllLabs() {
        return ResultVO.success(userService.findAllLabs());
    }
}
