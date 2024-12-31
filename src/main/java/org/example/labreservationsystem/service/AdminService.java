package org.example.labreservationsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.News;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.dto.*;
import org.example.labreservationsystem.repository.*;
import org.example.labreservationsystem.vo.ResultVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

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
        List<EnableEquipmentCount> enableEquipmentCountList = labRepository.countEnableEquipment();
        return Map.of("enableEquipmentCountList",enableEquipmentCountList);
    }

    @Transactional
    public  void addNews(News news) {
        newsRepository.save(news);
    }
    @Transactional
    public void updateNewsById(String id, News news){
    newsRepository.deleteById(id);
    newsRepository.save(news);

    }
    @Transactional
    public void deleteNewsById(String id) {
        newsRepository.deleteById(id);
    }
}
