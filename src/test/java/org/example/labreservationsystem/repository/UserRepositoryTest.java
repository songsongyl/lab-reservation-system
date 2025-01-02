package org.example.labreservationsystem.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.component.PasswordEncodeConfig;
import org.example.labreservationsystem.component.ULID;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.dto.Appointment1DTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ULID ulid;

    @Test
    public void saveUser() {
        String account = "admin";
        User user = User.builder()
                .name("syl")
                .account(account)
                .password(passwordEncoder.encode(account))
                .role(User.USER)
                .telephone("18346789032")
//            .createTime(LocalDateTime.now())
//            .updateTime(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }

    @Test
    void findByAccount() {
        User u = userRepository.findByAccount("admin");
        log.debug("{}", u);
    }

    @Test
    void count() {
        log.debug("{}", userRepository.count());
    }


}