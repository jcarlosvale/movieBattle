package com.letscode.moviesBattle.domain.repository.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;

import java.util.stream.Stream;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class QuizEntityTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validConstructor() {
        //GIVEN
        final var id = 1L;
        final var movieOne = new MovieEntity("movie one", "title movie one", 5.05, 55000);
        final var movieTwo = new MovieEntity("movie two", "title movie two", 7.05, 75000);
        final var entity = new QuizEntity(id, movieOne, movieTwo);

        //WHEN
        final var violations = validator.validate(entity);

        //THEN
        assertThat(violations.isEmpty()).isTrue();
        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getMovieOne()).isEqualTo(movieOne);
        assertThat(entity.getMovieTwo()).isEqualTo(movieTwo);
    }

    @ParameterizedTest
    @MethodSource("invalidFields")
    void invalidFieldsConstructor(final long id, final MovieEntity movieOne, final MovieEntity movieTwo) {
        //GIVEN
        final var entity = new QuizEntity(id, movieOne, movieTwo);

        //WHEN
        final var violations = validator.validate(entity);

        //THEN
        assertThat(violations.isEmpty()).isFalse();
    }

    private static Stream<Arguments> invalidFields() {

        final var movie = mock(MovieEntity.class);
        return Stream.of(
                arguments(1L, null, movie),
                arguments(1L, movie, null)
        );
    }

    @ParameterizedTest
    @MethodSource("equalSamples")
    void validateEquals(final QuizEntity tupleOne, final QuizEntity tupleTwo, final boolean expectedResult) {
        //GIVEN WHEN
        final var actualResult = tupleOne.equals(tupleTwo);

        //THEN
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> equalSamples() {

        final var movieA = new MovieEntity("movie A", "title movie A", 1.0, 1);
        final var movieB = new MovieEntity("movie B", "title movie B", 2.0, 2);
        final var movieC = new MovieEntity("movie C", "title movie C", 3.0, 3);

        final var tupleOfMoviesOne = new QuizEntity(1L, movieA, movieB);
        final var tupleOfMoviesTwo = new QuizEntity(2L, movieB, movieA);
        final var tupleOfMoviesThree = new QuizEntity(3L, movieA, movieC);

        return Stream.of(
                arguments(tupleOfMoviesOne, tupleOfMoviesOne, true),
                arguments(tupleOfMoviesOne, tupleOfMoviesTwo, true),
                arguments(tupleOfMoviesTwo, tupleOfMoviesOne, true),
                arguments(tupleOfMoviesOne, tupleOfMoviesThree, false),
                arguments(tupleOfMoviesThree, tupleOfMoviesOne, false)
        );
    }
}
