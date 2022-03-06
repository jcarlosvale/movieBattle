package com.letscode.moviesBattle.domain.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserDtoTest {

    @Test
    void constructor() {
        //GIVEN

        final var userId = 1L;

        //WHEN
        final var userDto = new UserDto(userId);

        //THEN
        assertThat(userDto.getUserId())
                .isEqualTo(userId);
    }
}