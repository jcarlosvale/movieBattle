package com.letscode.moviesBattle.domain.service.impl;

import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.dto.MovieDto;
import com.letscode.moviesBattle.domain.exception.BusinessException;
import com.letscode.moviesBattle.domain.exception.GameNotFinishedException;
import com.letscode.moviesBattle.domain.exception.UserNotFoundException;
import com.letscode.moviesBattle.domain.repository.GameRepository;
import com.letscode.moviesBattle.domain.repository.UserRepository;
import com.letscode.moviesBattle.domain.repository.model.GameEntity;
import com.letscode.moviesBattle.domain.repository.model.MovieEntity;
import com.letscode.moviesBattle.domain.repository.model.QuizEntity;
import com.letscode.moviesBattle.domain.repository.model.UserEntity;
import com.letscode.moviesBattle.domain.service.MoviesBattleService;
import com.letscode.moviesBattle.domain.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MoviesBattleServiceImpl implements MoviesBattleService {

    private final QuizService quizService;

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Override
    public GameDto startGame(final long userId) throws BusinessException {
        validateExclusiveGame(userId);
        final GameEntity gameEntity = createGame(userId);
        return toDto(gameEntity);
    }

    private GameDto toDto(GameEntity gameEntity) {
        return GameDto.builder()
                .id(gameEntity.getId())
                .movieOne(toDto(gameEntity.getLastQuizz().getMovieOne()))
                .movieTwo(toDto(gameEntity.getLastQuizz().getMovieTwo()))
                .rightAnswers(gameEntity.getRightAnswers())
                .wrongAnswers(gameEntity.getWrongAnswers())
                .build();
    }

    private MovieDto toDto(MovieEntity movie) {
        return MovieDto.builder()
                .imdbID(movie.getImdbID())
                .title(movie.getTitle())
                .build();
    }

    private GameEntity createGame(final long userId) throws UserNotFoundException {
        final UserEntity userEntity = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        final QuizEntity quizEntity = quizService.generateQuizCandidate();
        final Set<QuizEntity> quizzes = new HashSet<>();
        quizzes.add(quizEntity);
        return GameEntity
                .builder()
                .userEntity(userEntity)
                .isActive(true)
                .lastQuizz(quizEntity)
                .quizzes(quizzes)
                .build();
    }

    private void validateExclusiveGame(final long userId) throws GameNotFinishedException {
        final Optional<GameEntity> optionalGameEntity = gameRepository.findFirstByIsActiveTrue();
        if (optionalGameEntity.isPresent()) {
            throw new GameNotFinishedException();
        }
    }
}
