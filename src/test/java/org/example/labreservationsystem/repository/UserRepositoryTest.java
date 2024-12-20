package org.example.labreservationsystem.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.component.PasswordEncodeConfig;
import org.example.labreservationsystem.component.ULID;
import org.example.labreservationsystem.dox.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    String account = "2022222998";
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

}