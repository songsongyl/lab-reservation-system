package org.example.labreservationsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //管理员添加用户
    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getAccount()));
        userRepository.save(user);
    }
    @Transactional
    public User getUserByAccount(String account) {
        return userRepository.findByAccount(account);
    }
}
