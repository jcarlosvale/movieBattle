package com.letscode.moviesBattle.domain.service.impl;

import com.letscode.moviesBattle.domain.repository.UserRepository;
import com.letscode.moviesBattle.domain.repository.model.UserEntity;
import com.letscode.moviesBattle.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<UserEntity> retrieveUserById(final long userId) {
        return userRepository.findById(userId);
    }
}
