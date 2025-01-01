package org.example.labreservationsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.Lab;
import org.example.labreservationsystem.dox.News;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.dto.LabCountByDayofweekDTO;
import org.example.labreservationsystem.dto.LabCountDTO;
import org.example.labreservationsystem.service.AdminService;
import org.example.labreservationsystem.service.UserService;
import org.example.labreservationsystem.vo.ResultVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
//@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;

    //统计图表2
    //adminService 中有个方法 返回空闲中，维修中，使用中的设备数量
    @GetMapping( "graph2")
    public ResultVO accountLabByState() {
        log.debug("111");
        List<LabCountDTO> labCountDTOList = adminService.countLabByState();
//        log.info("Request URI: {}", request.getRequestURI());
        return ResultVO.success(labCountDTOList);
    }
    //统计图表1
    @Operation(summary = "显示实验室状态")
    @GetMapping("graph1")
    public ResultVO accountLabByStateWeek() {
        Map<String,List<?>> labState = adminService.getLabState();
        return ResultVO.success(labState);
    }
    //统计图表3
    @GetMapping("graph3")
    public ResultVO accountByDayOfWeek() {
        List<LabCountByDayofweekDTO> labCountByDayofweekDTOS =  adminService.countLabByDayofweek();
        return ResultVO.success(labCountByDayofweekDTOS);
    }
    //删除单个公告
    @DeleteMapping("news/{id}")
    public ResultVO deleteNewsById(@RequestAttribute("role") String role,@PathVariable String id){
        adminService.deleteNewsById(role,id);
        return ResultVO.ok();
    }
    //更新公告
    @PatchMapping("news")
    public ResultVO updateNewsById(@RequestAttribute("role") String role,@RequestBody News news){
        adminService.updateNewsById(role,news);
        return ResultVO.success(news);
    }
    //增加公告
    @PostMapping("news")
    public ResultVO addNews(@RequestAttribute("role") String role,@RequestBody News news){
        adminService.addNews(role,news);
        return ResultVO.success(news);
    }
    //批量删除公告
    @DeleteMapping("news")
    public ResultVO deleteNews(@RequestBody List<String> newsIds){
        adminService.deleteNews(newsIds);
        return ResultVO.ok();
    }
    //添加实验室
    @PostMapping("lab")
    public ResultVO addLab(@RequestAttribute("role") String role,@RequestBody Lab lab) {
        adminService.addLab(role,lab);
        return ResultVO.ok();
    }
    //添加用户
    @PostMapping("users")
    public ResultVO postUser(@RequestBody User user) {
        adminService.addUser(user);
        return ResultVO.ok();
    }
    //重置密码
    @PutMapping("users/{account}/password")
    public ResultVO putPassword(@PathVariable String account) {
        adminService.updateUserPassword(account);
        return ResultVO.ok();
    }
    //获取全部用户信息
    @GetMapping("users")
    public ResultVO getUsers() {
        return ResultVO.success(adminService.listUsers());
    }
}
