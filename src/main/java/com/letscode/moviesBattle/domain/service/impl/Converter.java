package com.letscode.moviesBattle.domain.service.impl;

import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.dto.MovieDto;
import com.letscode.moviesBattle.domain.dto.PlayerPositionDto;
import com.letscode.moviesBattle.domain.dto.RankingOfPlayersDto;
import com.letscode.moviesBattle.domain.repository.model.GameEntity;
import com.letscode.moviesBattle.domain.repository.model.MovieEntity;
import com.letscode.moviesBattle.domain.repository.projection.RankingProjection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class Converter {

    public GameDto toDto(final GameEntity gameEntity) {
        return GameDto.builder()
                .userId(gameEntity.getUserEntity().getId())
                .movieOne(toDto(gameEntity.getLastQuiz().getMovieOne()))
                .movieTwo(toDto(gameEntity.getLastQuiz().getMovieTwo()))
                .rightAnswers(gameEntity.getRightAnswers())
                .wrongAnswers(gameEntity.getWrongAnswers())
                .build();
    }

    public MovieDto toDto(final MovieEntity movie) {
        return MovieDto.builder()
                .imdbID(movie.getImdbID())
                .title(movie.getTitle())
                .build();
    }

    public RankingOfPlayersDto toDto(List<RankingProjection> rankingProjectionList) {
        final List<PlayerPositionDto> playerPositionDtoList =
                rankingProjectionList
                        .stream()
                        .map(rankingProjection ->
                            PlayerPositionDto
                                .builder()
                                    .position(rankingProjection.getPosition())
                                    .user(rankingProjection.getUser())
                                    .points(rankingProjection.getPoints())
                                    .build())
                        .collect(Collectors.toList());
        return RankingOfPlayersDto.builder()
                .playerPositionDtoList(playerPositionDtoList)
                .build();
    }
}
