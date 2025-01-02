package org.example.labreservationsystem.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.dox.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
public class AdminRepositoryTest {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void countLabByState() {
        log.debug("countLabByState");
        log.debug(adminRepository.countLabByState().toString());
        log.debug("22");
        assertFalse(adminRepository.countLabByState().isEmpty(), "查询结果不应该为空");
    }
    @Test
    public void addLabAdmin(){
        User user = User.builder().role(User.LABADMIN).account("1234").name("12")
                .telephone("1234567891")
                .password(passwordEncoder.encode("1234"))
                .build();
        userRepository.save(user);
    }
}
