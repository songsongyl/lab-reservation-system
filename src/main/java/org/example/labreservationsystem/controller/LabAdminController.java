package org.example.labreservationsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.Lab;
import org.example.labreservationsystem.dox.News;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.dto.LabCountByDayofweekDTO;
import org.example.labreservationsystem.dto.LabCountDTO;
import org.example.labreservationsystem.service.LabAdminService;
import org.example.labreservationsystem.vo.ResultVO;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
//@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
@RequestMapping("/api/labadmin/")
@RequiredArgsConstructor
public class LabAdminController {
    private final LabAdminService labAdminService;


    //删除单个公告
    @DeleteMapping("news/{id}")
    public ResultVO deleteNewsById(@RequestAttribute("role") String role,@PathVariable String id){
        labAdminService.deleteNewsById(role,id);
        return ResultVO.ok();
    }
    //更新公告
    @PatchMapping("news")
    public ResultVO updateNewsById(@RequestAttribute("role") String role,@RequestBody News news){
        labAdminService.updateNewsById(role,news);
        return ResultVO.success(news);
    }
    //增加公告
    @PostMapping("news")
    public ResultVO addNews(@RequestAttribute("role") String role,@RequestBody News news){
        labAdminService.addNews(role,news);
        return ResultVO.success(news);
    }
    //批量删除公告
    @DeleteMapping("news")
    public ResultVO deleteNews(@RequestBody List<String> newsIds){
        labAdminService.deleteNews(newsIds);
        return ResultVO.ok();
    }

   //更新实验室状态
    @PatchMapping("labs/{labId}")
    public ResultVO updateLabById(@PathVariable String labId,@RequestBody Lab lab){
        labAdminService.updateLabState(labId,lab.getState());
        return ResultVO.ok();
    }
}
