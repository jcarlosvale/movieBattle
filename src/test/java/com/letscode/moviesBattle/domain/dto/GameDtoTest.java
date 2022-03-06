package com.letscode.moviesBattle.domain.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class GameDtoTest {

    @Test
    void constructor() {
        //GIVEN
        final var id = 1L;
        final var movieOne = mock(MovieDto.class);
        final var movieTwo = mock(MovieDto.class);
        final var rightAnswers = 10;
        final var wrongAnswers = 15;

        //WHEN
        final var gameDto = new GameDto(id, movieOne, movieTwo, rightAnswers, wrongAnswers);

        //THEN
        assertThat(gameDto.getUserId())
                .isEqualTo(id);
        assertThat(gameDto.getMovieOne())
                .isEqualTo(movieOne);
        assertThat(gameDto.getMovieTwo())
                .isEqualTo(movieTwo);
        assertThat(gameDto.getRightAnswers())
                .isEqualTo(rightAnswers);
        assertThat(gameDto.getWrongAnswers())
                .isEqualTo(wrongAnswers);
    }

}