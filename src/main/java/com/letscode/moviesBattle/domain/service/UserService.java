package com.letscode.moviesBattle.domain.service;

import com.letscode.moviesBattle.domain.repository.model.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> retrieveUserById(long userId);
}
