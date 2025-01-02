package org.example.labreservationsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.Appointment;
import org.example.labreservationsystem.dox.Course;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.service.UserService;
import org.example.labreservationsystem.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    //修改自己的密码
    @PatchMapping("password")
    public ResultVO updatePassword(@RequestBody String password, @RequestAttribute("uid") String uid) {
        userService.updateUserPassword(uid, password);
        return ResultVO.ok();
    }

    //获取指定老师id，和学期的全部课表
    @GetMapping("coursetable/{semester}")
    public ResultVO getCourses(@PathVariable String semester ,@RequestAttribute("uid") String teacherId) {
        return ResultVO.success(userService.getCourses(semester,teacherId));
    }

    //获取老师的全部课程信息
    @GetMapping("courses")
    public ResultVO getCoursesInfo(@RequestAttribute("uid") String teacherId) {
        return ResultVO.success(userService.findCoursesByTeacherId(teacherId));
    }

    //获取老师在该学期的全部课程信息
    @GetMapping("courses/{semester}")
    public ResultVO getCoursesBySemester(@RequestAttribute("uid") String teacherId,@PathVariable String semester) {
        return ResultVO.success(userService.findCoursesByTeacherIdAndSemester(teacherId,semester));
    }
    //添加课程
    @PostMapping("course")
    public ResultVO addCourse(@RequestBody Course course) {
        userService.addCourse(course);
        return ResultVO.ok();
    }
    //更新课程
    @PatchMapping("course")
    public ResultVO updateCourse(@RequestBody Course course) {
        userService.updateCourse(course);
        return ResultVO.ok();
    }
    //删除指定老师指定id对应的课程,要判断是否有预约记录
    @DeleteMapping("courses/course/{courseId}")
    public ResultVO deleteCourse(@RequestAttribute("uid")String teacherId, @PathVariable String courseId) {
        userService.deleteCourseById(teacherId,courseId);
        return ResultVO.ok();
    }
    //删除指定老师的所有课程
    @DeleteMapping("courses")
    public ResultVO deleteCoursesByTeacherId(@RequestAttribute("uid") String teacherId) {
        userService.deleteAllCoursesByTeacherId(teacherId);
        return ResultVO.ok();
    }
    //删除指定老师多个课程id对应的课程
    @DeleteMapping("courses/{courseIds}")
    public  ResultVO deleteCoursesByiIds(@RequestAttribute("uid") String teacherId,@PathVariable List<String> courseIds) {
        log.debug("{}",courseIds);
        userService.deleteCoursesByIds(teacherId, courseIds);
        return ResultVO.ok();
    }
    //基于老师id,课程id,获取已经选了多少学时
    @GetMapping("{courseId}/hours")
    public ResultVO getHours(@RequestAttribute("uid") String teacherId, @PathVariable String courseId) {
        return ResultVO.success(userService.getHours(teacherId, courseId));
    }
    //基于老师id,课程id，获取人数可用和不可用的实验室
    @GetMapping("labs/{courseId}")
    public ResultVO getlabs(@RequestAttribute("uid") String teacherId, @PathVariable String courseId) {
        return ResultVO.success(userService.getLabs(teacherId,courseId ));
    }
    //基于实验室id，查预约表
    @GetMapping("appointments/{labId}")
    public ResultVO getAppointment(@PathVariable String labId) {
        return ResultVO.success(userService.getAppointment(labId));
    }
    //预约课程
    @PostMapping("appointment")
    public ResultVO appointmentCourse(@RequestBody Appointment appointment) {
       userService.appointCourse(appointment);
        return ResultVO.ok();
    }
    //基于老师id,课程id移除对应的预约信息
    @DeleteMapping("appointment")
    public ResultVO deleteAppointment(@RequestBody Appointment appointment) {
        userService.deleteAppointment(appointment);
        return ResultVO.ok();
    }
}
