package com.letscode.moviesBattle.config;

import com.letscode.moviesBattle.domain.repository.UserRepository;
import com.letscode.moviesBattle.domain.repository.model.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UsersLoader {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void loadUsers() {
        log.info("INSERTING USERS TO DATABASE...");

        UserEntity user1 =
                UserEntity.builder()
                        .userName("user1")
                        .password(passwordEncoder.encode("pass1"))
                        .roles("USER")
                        .active(true)
                        .build();
        userRepository.save(user1);

        UserEntity user2 =
                UserEntity.builder()
                        .userName("user2")
                        .password(passwordEncoder.encode("pass2"))
                        .roles("USER")
                        .active(true)
                        .build();
        userRepository.save(user2);

        UserEntity user3 =
                UserEntity.builder()
                        .userName("test")
                        .password(passwordEncoder.encode("test"))
                        .roles("USER")
                        .active(true)
                        .build();
        userRepository.save(user3);

        log.info("USERS INSERTED.");

    }
}
