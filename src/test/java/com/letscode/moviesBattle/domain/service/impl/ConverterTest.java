package com.letscode.moviesBattle.domain.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.letscode.moviesBattle.domain.dto.PlayerPositionDto;
import com.letscode.moviesBattle.domain.dto.RankingOfPlayersDto;
import com.letscode.moviesBattle.domain.repository.model.GameEntity;
import com.letscode.moviesBattle.domain.repository.model.MovieEntity;
import com.letscode.moviesBattle.domain.repository.model.QuizEntity;
import com.letscode.moviesBattle.domain.repository.model.UserEntity;
import com.letscode.moviesBattle.domain.repository.projection.RankingProjection;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConverterTest {

    @Mock
    private UserEntity userEntity;
    @Mock
    private QuizEntity quizEntity;

    @InjectMocks
    private Converter converter;

    @Test
    void convertGameEntity() {
        //GIVEN
        final var gameId = 1L;
        final var userId = 5L;
        final var rightAns = 10;
        final var wrongAns = 15;

        final var entity =
                GameEntity.builder()
                        .id(gameId)
                        .userEntity(userEntity)
                        .lastQuiz(quizEntity)
                        .rightAnswers(rightAns)
                        .wrongAnswers(wrongAns)
                        .build();

        given(userEntity.getId())
                .willReturn(userId);

        final var movieOne =
                MovieEntity.builder()
                        .imdbID("movie one id")
                        .title("movie one title")
                        .build();
        final var movieTwo =
                MovieEntity.builder()
                        .imdbID("movie two id")
                        .title("movie two title")
                        .build();
        given(quizEntity.getMovieOne())
                .willReturn(movieOne);
        given(quizEntity.getMovieTwo())
                .willReturn(movieTwo);

        //WHEN
        final var dto = converter.toDto(entity);

        //THEN
        assertThat(dto.getUserId())
                .isEqualTo(userId);
        assertThat(dto.getMovieOne().getImdbID())
                .isEqualTo(movieOne.getImdbID());
        assertThat(dto.getMovieOne().getTitle())
                .isEqualTo(movieOne.getTitle());
        assertThat(dto.getMovieTwo().getImdbID())
                .isEqualTo(movieTwo.getImdbID());
        assertThat(dto.getMovieTwo().getTitle())
                .isEqualTo(movieTwo.getTitle());
        assertThat(dto.getRightAnswers())
                .isEqualTo(rightAns);
        assertThat(dto.getWrongAnswers())
                .isEqualTo(wrongAns);
    }

    @Test
    void convertMovieEntity() {
        //GIVEN
        final var entity =
                MovieEntity.builder()
                        .imdbID("movie one id")
                        .title("movie one title")
                        .build();

        //WHEN
        final var dto = converter.toDto(entity);

        //THEN
        assertThat(dto.getImdbID())
                .isEqualTo(entity.getImdbID());
        assertThat(dto.getTitle())
                .isEqualTo(entity.getTitle());
    }

    @Test
    void convertListOfRankingProjection() {
        //GIVEN
        final var rankingProjection1 = mock(RankingProjection.class);
        given(rankingProjection1.getPosition())
                .willReturn(1);
        given(rankingProjection1.getUser())
                .willReturn("user1");
        given(rankingProjection1.getPoints())
                .willReturn(100);

        final var rankingProjection2 = mock(RankingProjection.class);
        given(rankingProjection2.getPosition())
                .willReturn(2);
        given(rankingProjection2.getUser())
                .willReturn("user2");
        given(rankingProjection2.getPoints())
                .willReturn(50);

        final var rankingProjectionList = List.of(rankingProjection1, rankingProjection2);

        final var player1 = PlayerPositionDto.builder().position(1).user("user1").points(100).build();
        final var player2 = PlayerPositionDto.builder().position(2).user("user2").points(50).build();
        final var playerPositionDtoList = List.of(player1, player2);
        final var rankingOfPlayersDto = RankingOfPlayersDto.builder().playerPositionDtoList(playerPositionDtoList).build();

        //WHEN
        final var dto = converter.toDto(rankingProjectionList);

        //THEN
        assertThat(dto)
                .isEqualTo(rankingOfPlayersDto);
    }
}