package org.example.labreservationsystem.service;

import org.example.labreservationsystem.dox.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final List<User> USERS = create();
    private static List<User> create() {
        User user1 = User.builder().id("1001")
                .name("syl").account("100")
                .role(User.ADMIN)
                .password("$2a$10$XPz7Kp1kF8NU3vewqqPGn.feT7UPvhoZolvJ1JRi57s16XKMWz9OW")
                .build();
        User user2 = User.builder().id("1002")
                .name("syl2").account("101")
                .role(User.USER)
                .password("$2a$10$XPz7Kp1kF8NU3vewqqPGn.feT7UPvhoZolvJ1JRi57s16XKMWz9OW")
                .build();
        return List.of(user1, user2);
    }

    public List<User> listUsers() {
        return USERS;
    }

    public User getUserByAccount(String account) {
        return  USERS.stream()
                .filter(user -> user.getAccount().equals(account))
                .findFirst().orElse(null);
    }
}
