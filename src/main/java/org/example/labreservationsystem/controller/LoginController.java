package org.example.labreservationsystem.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.exception.Code;
import org.example.labreservationsystem.service.UserService;
import org.example.labreservationsystem.vo.ResultVo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.example.labreservationsystem.component.JWTComponent;

import java.util.Map;
@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/")
public class LoginController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
   private final JWTComponent jwtComponent;
    @PostMapping("login")
    public ResultVo login(@RequestBody User user, HttpServletResponse response) {
//        log.debug(user.toString());
//        return "接收到";

        User userR = userService.getUserByAccount(user.getAccount());
        log.debug("{}",userR);
        if(userR == null || !passwordEncoder.matches(user.getPassword(), userR.getPassword())) {
            log.debug("{}",userR);
            return ResultVo.error(Code.LOGIN_ERROR);
        }
        String token = jwtComponent.encode(Map.of("uid", userR.getId(),"role",userR.getRole()));
        response.setHeader("token", token);
        //request 没有这个方法
        response.setHeader("role", user.getRole());
        return ResultVo.success(userR);
    }
}
