package org.example.labreservationsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.Appointment;
import org.example.labreservationsystem.dox.Course;
import org.example.labreservationsystem.dox.Lab;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.dto.Appointment1DTO;
import org.example.labreservationsystem.dto.LabDTO;
import org.example.labreservationsystem.dto.NewsDTO;
import org.example.labreservationsystem.exception.Code;
import org.example.labreservationsystem.exception.XException;
import org.example.labreservationsystem.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final LabRepository labRepository;
    private final NewsRepository newsRepository;
    private final PasswordEncoder passwordEncoder;
    private final CourseRepository courseRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public User getUserByAccount(String account) {
        return userRepository.findByAccount(account);
    }
    @Transactional
    public List<NewsDTO> findAllNews() {
        return newsRepository.findAllNews();
    }
    @Transactional
    public List<LabDTO> findAllLabs() {
        return labRepository.findAllLabs();
    }
    //指定老师id查全部课表
    @Transactional
    public List<Appointment1DTO> getCourses(String semester , String teacherId) {
        User u  = userRepository.findByTeacherId(teacherId);
        if(u == null) {
            throw XException.builder().number(Code.ERROR).message("老师不存在").build();
        }
        return userRepository.findCourseByTeacherIdAndSemester(semester,teacherId);
    }
    //基于老师id获取全部课程信息
    @Transactional
    public List<Course> findCoursesByTeacherId(String id) {
        User u  = userRepository.findByTeacherId(id);
        if(u == null) {
            throw XException.builder().number(Code.ERROR).message("老师不存在").build();
        }
        return courseRepository.findCoursesByTeacherId(id);
    }
    //添加课程
    @Transactional
    public void addCourse(Course course ) {

        courseRepository.save(course);
    }
    //基于课程id删除某一门课
    @Transactional
    public void deleteCourseById( String teacherId, String courseId) {
        User u = userRepository.findByTeacherId(teacherId);
        Course c = courseRepository.findById(courseId).orElse(null);
        if(u == null || c == null) {
            throw new XException().builder().number(Code.ERROR).message("老师或课程不存在").build();
        }
        int count = courseRepository.findCountByTeacherIdAndCourseId(teacherId,courseId);
        if(count>0) {
            throw new XException().builder().number(Code.ERROR).message("课程存在预约记录，请先移除预约记录").build();
        }
        courseRepository.deleteCourseByTeacherIdAndCourseId(teacherId, courseId);
    }
    //基于老师id和多个id删除多门课
    @Transactional(rollbackFor = XException.class)
    public void deleteCoursesByIds(String teacherId, List<String> ids) {
        StringBuilder errorMessage = new StringBuilder("以下课程存在问题：");
        boolean hasNonExistentCourse = false;
        boolean hasCourseWithAppointments = false;
        StringBuilder nonExistentCourseIds = new StringBuilder();
        StringBuilder courseIdsWithAppointments = new StringBuilder();

        for (String id : ids) {
            Course c = courseRepository.findById(id).orElse(null);
            if (c == null) {
                hasNonExistentCourse = true;
                if (nonExistentCourseIds.length() > 0) {
                    nonExistentCourseIds.append(" ");
                }
                nonExistentCourseIds.append(id);
                continue;
            }

            int count = courseRepository.findCountByTeacherIdAndCourseId(teacherId, id);
            if (count > 0) {
                hasCourseWithAppointments = true;
                if (courseIdsWithAppointments.length() > 0) {
                    courseIdsWithAppointments.append(", ");
                }
                courseIdsWithAppointments.append(id);
            }
        }

        if (hasNonExistentCourse) {
            errorMessage.append("不存在的课程ID：");
            errorMessage.append(nonExistentCourseIds.toString());
        }
        if (hasCourseWithAppointments) {
            if (hasNonExistentCourse) {
                errorMessage.append("；");
            }
            errorMessage.append("存在预约记录的课程ID,请先移除预约记录：");
            errorMessage.append(courseIdsWithAppointments.toString());
        }

        if (hasNonExistentCourse || hasCourseWithAppointments) {
            throw XException.builder()
                    .number(Code.ERROR)
                    .message(errorMessage.toString())
                    .build();
        }

        courseRepository.deleteCoursesByTeacherIdAndCourseIds(teacherId, ids);
    }
    //删除指定老师id的全部课程
    @Transactional
    public void deleteAllCoursesByTeacherId(String id) {
        User u  = userRepository.findByTeacherId(id);
        if(u == null) {
            throw XException.builder().number(Code.ERROR).message("老师不存在").build();
        }
        courseRepository.deleteCoursesByTeacherId(id);
    }
    //修改课程
    @Transactional
    public void updateCourse(Course course) {
        Course course1 = courseRepository.findById(course.getId()).orElse(null);
        if(course1 == null) {
            throw XException.builder().number(Code.ERROR).message("课程不存在").build();
        }else {
            course1.setName(course.getName());
            course1.setClazz(course.getClazz());
            course1.setQuantity(course.getQuantity());
            course1.setType(course.getType());
            course1.setSemester(course.getSemester());
            course1.setExperimentHour(course.getExperimentHour());

        }
        courseRepository.save(course1);
    }
    //基于老师id,课程id,获取已经选了多少学时
    @Transactional
    public int getHours(String teacherId, String courseId) {
        User u = userRepository.findByTeacherId(teacherId);
        Course c = courseRepository.findById(courseId).orElse(null);
        if(u == null || c == null) {
            throw new XException().builder().number(Code.ERROR).message("老师或课程不存在").build();
        }
        int count = courseRepository.findCountByTeacherIdAndCourseId(teacherId, courseId);
        return count*2;
    }
    @Transactional
    //基于老师id,课程id，获取人数可用和不可用的实验室
    public Map<String, List<Lab>> getLabs(String teacherId, String courseId) {
        User u = userRepository.findByTeacherId(teacherId);
        Course c = courseRepository.findById(courseId).orElse(null);
        if(u == null || c == null) {
            throw new XException().builder().number(Code.ERROR).message("老师或课程不存在").build();
        }
        Map<String, List<Lab>> map = new HashMap<>();
        List<Lab> labs1 = labRepository.findEnableLabs(teacherId,courseId);
        map.put("座位充足实验室",labs1);
        List<Lab> labs2 = labRepository.findUnableLabs(teacherId,courseId);
        map.put("座位不够实验室",labs2);
        return map;
    }
    //基于实验室id，查预约表
    @Transactional
    public List<Appointment> getAppointment(String labId) {
        Lab lab = labRepository.findById(labId).orElse(null);
        if(lab == null) {
            throw new XException().builder().number(Code.ERROR).message("实验室不存在").build();
        }
        return appointmentRepository.findAllByLabId(labId);
    }
    //预约课程
    @Transactional
    public void appointCourse(Appointment appointment) {
        String c = appointment.getCourse();
        log.debug("{}",c);
        appointmentRepository.save(appointment);
    }

    //基于老师id,课程id移除对应的预约信息
    @Transactional
    public void deleteAppointment(Appointment appointment) {
        Appointment a = appointmentRepository.findById(appointment.getId()).orElse(null);
        if(a == null) {
            throw new XException().builder().number(Code.ERROR).message("预约记录不存在").build();
        }
        appointmentRepository.deleteById(appointment.getId());
    }

    //指定用户改密码
    @Transactional
    public void updateUserPassword(String uid, String password) {
//        log.debug(uid);
//        log.debug(password);
        User user = userRepository.findByTeacherId(uid);
        if(user == null) {
            throw  XException.builder().number(Code.ERROR).message("用户不存在").build();
        }
        user.setPassword(passwordEncoder.encode(password));
        //保存
        userRepository.save(user);
    }

}
