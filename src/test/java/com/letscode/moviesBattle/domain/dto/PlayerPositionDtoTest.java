package com.letscode.moviesBattle.domain.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PlayerPositionDtoTest {

    @Test
    void constructor() {
        //GIVEN

        final var user = "some user name";
        final var points = 100;

        //WHEN
        final var dto = new PlayerPositionDto(user, points);

        //THEN
        assertThat(dto.getUser())
                .isEqualTo(user);
        assertThat(dto.getPoints())
                .isEqualTo(points);
    }
}