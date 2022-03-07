package com.letscode.moviesBattle.domain.service.impl;

import com.letscode.moviesBattle.domain.repository.QuizRepository;
import com.letscode.moviesBattle.domain.repository.model.MovieEntity;
import com.letscode.moviesBattle.domain.repository.model.QuizEntity;
import com.letscode.moviesBattle.domain.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {

    @Mock
    private MovieService movieService;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private MovieEntity movieOne;
    @Mock
    private MovieEntity movieTwo;

    @Mock
    private QuizEntity quizEntity;

    @InjectMocks
    private QuizServiceImpl quizService;

    @Test
    void generateQuizCandidateValidatingNotAllowedEquals() {

        //GIVEN
        given(movieService.getRandomMovie())
                .willReturn(movieOne)
                .willReturn(movieOne)
                .willReturn(movieTwo);

        //WHEN
        final var entity = quizService.generateQuizCandidate();

        //THEN
        verify(movieService, times(3))
                .getRandomMovie();
        assertThat(entity.getMovieOne())
                .isEqualTo(movieOne);
        assertThat(entity.getMovieTwo())
                .isEqualTo(movieTwo);
    }

    @Test
    void save() {
        //GIVEN
        given(quizRepository.save(quizEntity))
                .willReturn(quizEntity);

        //WHEN
        final var entity = quizService.save(quizEntity);

        //THEN
        assertThat(entity).isEqualTo(quizEntity);
    }

    @ParameterizedTest
    @MethodSource("generateMovies")
    void evaluateWinnerMovie(final MovieEntity movieOne, final MovieEntity movieTwo, final String winner) {
        //GIVEN
        given(quizEntity.getMovieOne())
                .willReturn(movieOne);
        given(quizEntity.getMovieTwo())
                .willReturn(movieTwo);

        //WHEN
        final var score = quizService.evaluateWinnerId(quizEntity);

        //THEN
        assertThat(score).isEqualTo(winner);
    }

    private static Stream<Arguments> generateMovies() {
        return Stream.of(
                arguments(
                        MovieEntity.builder().imdbID("one").imdbRating(5.5).imdbVotes(7).build(),
                        MovieEntity.builder().imdbID("one").imdbRating(5.5).imdbVotes(7).build(),
                        "one"
                        ),
                arguments(
                        MovieEntity.builder().imdbID("one").imdbRating(5.5).imdbVotes(7).build(),
                        MovieEntity.builder().imdbID("two").imdbRating(5.4).imdbVotes(7).build(),
                        "one"
                ),
                arguments(
                        MovieEntity.builder().imdbID("one").imdbRating(5.5).imdbVotes(7).build(),
                        MovieEntity.builder().imdbID("two").imdbRating(5.5).imdbVotes(6).build(),
                        "one"
                ),
                arguments(
                        MovieEntity.builder().imdbID("one").imdbRating(5.5).imdbVotes(7).build(),
                        MovieEntity.builder().imdbID("two").imdbRating(5.6).imdbVotes(7).build(),
                        "two"
                ),
                arguments(
                        MovieEntity.builder().imdbID("one").imdbRating(5.5).imdbVotes(7).build(),
                        MovieEntity.builder().imdbID("two").imdbRating(5.5).imdbVotes(8).build(),
                        "two"
                ),
                arguments(
                        MovieEntity.builder().imdbID("one").imdbRating(6.5).imdbVotes(9).build(),
                        MovieEntity.builder().imdbID("two").imdbRating(5.5).imdbVotes(8).build(),
                        "one"
                ),
                arguments(
                        MovieEntity.builder().imdbID("one").imdbRating(5.5).imdbVotes(7).build(),
                        MovieEntity.builder().imdbID("two").imdbRating(5.6).imdbVotes(8).build(),
                        "two"
                )
        );
    }
}