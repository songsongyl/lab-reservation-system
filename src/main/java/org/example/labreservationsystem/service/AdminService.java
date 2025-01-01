package org.example.labreservationsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.Lab;
import org.example.labreservationsystem.dox.News;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.dto.*;
import org.example.labreservationsystem.exception.Code;
import org.example.labreservationsystem.exception.XException;
import org.example.labreservationsystem.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final LabRepository labRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final NewsRepository newsRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public List<LabCountDTO> countLabByState() {
        log.debug(adminRepository.countLabByState().toString());
        return adminRepository.countLabByState();
    }
//    @Transactional
//    public void addSingleUser(User user) {
//        userRepository.save(user);
//    }
    @Transactional
    public List<LabCountByDayofweekDTO> countLabByDayofweek() {
        return appointmentRepository.countLabByDayofweek();
    }
    //管理员添加用户
    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getAccount()));
        userRepository.save(user);
    }
    @Transactional
    public Map<String, List<?>> getLabState() {
//        查看每个状态的实验室数
//        List<LabCountDTO>  labCountDTOList = labRepository.countLabByState();
//        查看每周每一天有多少个实验室使用（不看节次，只要有课就是使用）
        List<LabCountByDayofweekDTO> labCountByDayofweekDTOList = appointmentRepository.countLabByDayofweek();
//        查看每个实验室能用的设备数量
        List<EnableEquipmentCountDTO> enableEquipmentCountList = labRepository.countEnableEquipment();
        return Map.of("enableEquipmentCountList",enableEquipmentCountList);
    }

    @Transactional
    public  void addNews(String role,News news) {
        if(!role.equals("sqWf")) {
            throw XException.builder()
                    .code(Code.FORBIDDEN)
                    .number(Code.FORBIDDEN.getCode())
                    .message(Code.FORBIDDEN.getMessage())
                    .build();
        }
        newsRepository.save(news);
    }
    @Transactional
    public void updateNewsById(String role,News news){
//    newsRepository.deleteById(id);
        News n = newsRepository.findById(news.getId()).orElse(null);
        if(n == null) {
            throw XException.builder().number(Code.ERROR).message("公告不存在").build();
        }
        if(!role.equals("sqWf")) {
            throw XException.builder()
                    .code(Code.FORBIDDEN)
                    .number(Code.FORBIDDEN.getCode())
                    .message(Code.FORBIDDEN.getMessage())
                    .build();
        }
    newsRepository.save(news);

    }

    //列出全部用户
    public List<User> listUsers() {
        return userRepository.findAll();
    }
    //管理员重置密码
    @Transactional
    public void updateUserPassword(String account) {
        User user = userRepository.findByAccount(account);
        if(user == null) {
            throw  XException.builder().number(Code.ERROR).message("用户不存在").build();
        }
        user.setPassword(passwordEncoder.encode(account));
        //保存
        userRepository.save(user);
    }
    @Transactional
    public void deleteNewsById(String role,String id) {
        News n = newsRepository.findById(id).orElse(null);
        if(n == null) {
            throw XException.builder().number(Code.ERROR).message("公告不存在").build();

        }
        if(!role.equals("sqWf")) {
            throw XException.builder()
                    .code(Code.FORBIDDEN)
                    .number(Code.FORBIDDEN.getCode())
                    .message(Code.FORBIDDEN.getMessage())
                    .build();
        }
        newsRepository.deleteById(id);
    }
    @Transactional
    public void deleteNews(List<String> ids) {
        for (String id : ids) {
            newsRepository.deleteById(id);
        }

    }

    @Transactional
    public void addLab(String role,Lab lab) {
        if(!role.equals("sqWf")) {
            throw XException.builder()
                    .code(Code.FORBIDDEN)
                    .number(Code.FORBIDDEN.getCode())
                    .message(Code.FORBIDDEN.getMessage())
                    .build();
        }
        labRepository.save(lab);
    }
}
