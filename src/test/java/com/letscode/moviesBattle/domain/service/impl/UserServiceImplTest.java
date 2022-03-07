package com.letscode.moviesBattle.domain.service.impl;

import com.letscode.moviesBattle.domain.repository.UserRepository;
import com.letscode.moviesBattle.domain.repository.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;
    @Mock
    private UserEntity userEntity;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void retrieveUserById() {
        //GIVEN
        final var userId = 2L;

        given(repository.findById(userId))
                .willReturn(Optional.of(userEntity));

        //WHEN
        final var user = service.retrieveUserById(userId);

        //THEN
        assertThat(user).isEqualTo(Optional.of(userEntity));
    }

}