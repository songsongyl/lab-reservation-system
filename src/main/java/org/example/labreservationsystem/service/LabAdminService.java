package org.example.labreservationsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.News;
import org.example.labreservationsystem.dox.User;
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
public class LabAdminService {

    private final LabAdminRepository labAdminRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final NewsRepository newsRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public  void addNews(String role, News news) {
        if(!role.equals(User.LABADMIN)) {
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
        if(!role.equals(User.LABADMIN)) {
            throw XException.builder()
                    .code(Code.FORBIDDEN)
                    .number(Code.FORBIDDEN.getCode())
                    .message(Code.FORBIDDEN.getMessage())
                    .build();
        }
        newsRepository.save(news);

    }
    @Transactional
    public void deleteNewsById(String role,String id) {
        News n = newsRepository.findById(id).orElse(null);
        if(n == null) {
            throw XException.builder().number(Code.ERROR).message("公告不存在").build();

        }
        if(!role.equals(User.LABADMIN)) {
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
    public void updateLabState(String id,int state) {
        labAdminRepository.updateState(id,state);
    }
}
