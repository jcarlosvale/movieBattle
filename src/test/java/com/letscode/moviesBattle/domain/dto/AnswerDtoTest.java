package com.letscode.moviesBattle.domain.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AnswerDtoTest {

    @Test
    void constructor() {
        //GIVEN
        final var gameId = 20L;
        final var imdbID = "some id";

        //WHEN
        final var answer = new AnswerDto(gameId, imdbID);

        //THEN
        assertThat(answer.getGameId())
                .isEqualTo(gameId);
        assertThat(answer.getImdbID())
                .isEqualTo(imdbID);
    }
}