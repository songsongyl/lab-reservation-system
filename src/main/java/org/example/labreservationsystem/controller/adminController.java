package org.example.labreservationsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin")
public class adminController {
    //adminService 中有个方法 返回空闲中，维修中，使用中的设备数量
    @GetMapping("getData")
    public String getData() {
        return "admin";
    }
}
