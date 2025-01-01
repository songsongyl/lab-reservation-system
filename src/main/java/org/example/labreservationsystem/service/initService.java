package org.example.labreservationsystem.service;
import lombok.RequiredArgsConstructor;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class initService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        String account = "admin";
        long  count = userRepository.count();
        if(count > 0) {
            return;
        }
        User u = User.builder()
                .name(account)
                .account(account)
                .password(passwordEncoder.encode(account))
                .role(User.ADMIN)
                .telephone("18346789032")
                .build();
        userRepository.save(u);
        System.out.println("Admin user saved");
    }

}
