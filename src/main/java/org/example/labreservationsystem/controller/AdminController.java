package org.example.labreservationsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.Lab;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.dto.LabCountByDayofweekDTO;
import org.example.labreservationsystem.dto.LabCountDTO;
import org.example.labreservationsystem.service.AdminService;
import org.example.labreservationsystem.vo.ResultVO;
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
    //添加用户
    @PostMapping("user")
    public ResultVO postUser(@RequestBody User user) {
        adminService.addUser(user);
        return ResultVO.ok();
    }
    //添加用户
    @PostMapping("user3")
    public ResultVO addSingleUser(@RequestBody User user) {
        adminService.addSingleUser(user);
        return ResultVO.success(user);
    }
    //基于账号重置密码
    @PutMapping("users/{account}/password")
    public ResultVO putPassword(@PathVariable String account) {
        adminService.updateUserPassword(account);
        return ResultVO.ok();
    }
    //将用户密码重置为其账号
//users页面中为users列表，点开某个项通过弹窗查看具体信息，因此更改具体信息直接在users页面更改
    @PatchMapping("user2")
    public ResultVO resetPassword(@RequestBody User user) {
        if (user.getId() != null && user.getAccount() != null){
            adminService.changePassword(user.getId(),user.getAccount());
            return ResultVO.success("修改成功");
        }
        return ResultVO.error(500,"传值为空");
    }
    //添加实验室
    @PostMapping("lab")
    public ResultVO addLab(@RequestAttribute("role") String role,@RequestBody Lab lab) {
        adminService.addLab(role,lab);
        return ResultVO.ok();
    }

    //获取全部用户信息
    @GetMapping("users")
    public ResultVO getUsers() {
        return ResultVO.success(adminService.listUsers());
    }
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
    //查询指定实验室管理员的所有实验室
    @GetMapping("labs/{labAdminId}")
    public ResultVO getLab(@PathVariable String labAdminId) {
        adminService.findLabsByLabAdminId(labAdminId);
        return ResultVO.ok();
    }
}
