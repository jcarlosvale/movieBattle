package com.letscode.moviesBattle.domain.service.impl;

import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.dto.MovieDto;
import com.letscode.moviesBattle.domain.repository.model.GameEntity;
import com.letscode.moviesBattle.domain.repository.model.MovieEntity;
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
}
