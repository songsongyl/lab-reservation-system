package org.example.labreservationsystem.repository;

import org.example.labreservationsystem.component.ULID;
import org.example.labreservationsystem.dox.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

@Autowired
private UserRepository userRepository;
@Autowired
private ULID ulid;
    @Test
    void save() {
        User user1 = User.builder().id(ulid.nextULID()).account("admin").password("123456").role("123").telephone("12345678901").build();
        userRepository.save(user1);
    }
}