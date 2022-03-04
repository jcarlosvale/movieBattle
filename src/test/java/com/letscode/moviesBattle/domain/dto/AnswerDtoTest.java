package com.letscode.moviesBattle.domain.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AnswerDtoTest {

    @Test
    void constructor() {
        //GIVEN
        final var userId = 20L;
        final var imdbID = "some id";

        //WHEN
        final var answer = new AnswerDto(userId, imdbID);

        //THEN
        assertThat(answer.getUserId())
                .isEqualTo(userId);
        assertThat(answer.getImdbID())
                .isEqualTo(imdbID);
    }
}