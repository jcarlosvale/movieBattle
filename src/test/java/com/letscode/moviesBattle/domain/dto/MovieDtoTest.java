package com.letscode.moviesBattle.domain.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MovieDtoTest {

    @Test
    void constructor() {
        //GIVEN

        final var imdb = "some imdb";
        final var title = "some title";

        //WHEN
        final var movieDto = new MovieDto(imdb, title);

        //THEN
        assertThat(movieDto.getImdbID())
                .isEqualTo(imdb);
        assertThat(movieDto.getTitle())
                .isEqualTo(title);
    }
}