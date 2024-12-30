package org.example.labreservationsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.dto.LabDTO;
import org.example.labreservationsystem.dto.NewsDTO;
import org.example.labreservationsystem.repository.LabRepository;
import org.example.labreservationsystem.repository.NewsRepository;
import org.example.labreservationsystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final LabRepository labRepository;
    private final NewsRepository newsRepository;

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
}
