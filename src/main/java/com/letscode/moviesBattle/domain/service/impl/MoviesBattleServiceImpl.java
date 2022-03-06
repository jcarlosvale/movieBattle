package com.letscode.moviesBattle.domain.service.impl;

import com.letscode.moviesBattle.domain.dto.AnswerDto;
import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.dto.UserDto;
import com.letscode.moviesBattle.domain.exception.*;
import com.letscode.moviesBattle.domain.repository.GameRepository;
import com.letscode.moviesBattle.domain.repository.model.GameEntity;
import com.letscode.moviesBattle.domain.repository.model.QuizEntity;
import com.letscode.moviesBattle.domain.repository.model.UserEntity;
import com.letscode.moviesBattle.domain.service.MoviesBattleService;
import com.letscode.moviesBattle.domain.service.QuizService;
import com.letscode.moviesBattle.domain.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MoviesBattleServiceImpl implements MoviesBattleService {

    private final QuizService quizService;
    private final UserService userService;

    private final GameRepository gameRepository;

    private final Converter converter;

    private final int MAX_ERRORS = 3;

    @Override
    public GameDto startGame(final UserDto userDto) throws BusinessException {
        final UserEntity userEntity =
                userService
                        .retrieveUserById(userDto.getUserId())
                        .orElseThrow(UserNotFoundException::new);
        validateExclusiveGame(userEntity);
        final GameEntity gameEntity = createGame(userEntity);
        return converter.toDto(gameEntity);
    }

    @Override
    public GameDto nextQuiz(final AnswerDto answerDto) throws BusinessException {
        GameEntity gameEntity = getGameEntityByUserId(answerDto.getUserId());
        validateErrorLimit(gameEntity);
        updateScore(gameEntity, answerDto.getImdbID());
        insertNewQuiz(gameEntity);
        saveGameAndLastQuiz(gameEntity);
        return converter.toDto(gameEntity);
    }

    @Override
    public GameDto stopGame(final UserDto userDto) throws BusinessException {
        GameEntity gameEntity = getGameEntityByUserId(userDto.getUserId());
        gameEntity.setActive(false);
        return converter.toDto(gameRepository.save(gameEntity));
    }

    @Transactional
    protected GameEntity saveGameAndLastQuiz(GameEntity gameEntity) {
        quizService.save(gameEntity.getLastQuiz());
        return gameRepository.save(gameEntity);
    }

    private GameEntity getGameEntityByUserId(@NonNull final long userId) throws GameNotFoundException {
        return gameRepository
                .findGameEntityByUserEntityIdAndActiveTrue(userId)
                .orElseThrow(GameNotFoundException::new);
    }

    private GameEntity insertNewQuiz(final GameEntity game) {
        QuizEntity quiz = quizService.generateQuizCandidate();
        while(game.getQuizzes().contains(quiz)) {
            quiz = quizService.generateQuizCandidate();
        }
        game.setLastQuiz(quiz);
        game.getQuizzes().add(quiz);
        return game;
    }

    private GameEntity updateScore(final GameEntity gameEntity, final String imdbID) {
        final String imdbWinner = quizService.evaluateWinnerId(gameEntity.getLastQuiz());
        if (imdbWinner.equals(imdbID)) {
            gameEntity.setRightAnswers(gameEntity.getRightAnswers()+1);
        } else {
            gameEntity.setWrongAnswers(gameEntity.getWrongAnswers()+1);
        }
        return gameEntity;
    }

    private void validateErrorLimit(final GameEntity gameEntity) throws MaximumErrorReachedException {
        if (gameEntity.getWrongAnswers() >= MAX_ERRORS) {
            throw new MaximumErrorReachedException();
        }
    }

    private GameEntity createGame(final UserEntity userEntity) {
        final QuizEntity quizEntity = quizService.generateQuizCandidate();
        quizService.save(quizEntity);
        final Set<QuizEntity> quizzes = new HashSet<>();
        quizzes.add(quizEntity);
        return gameRepository.save(GameEntity
                        .builder()
                        .userEntity(userEntity)
                        .active(true)
                        .lastQuiz(quizEntity)
                        .quizzes(quizzes)
                        .build()
        );
    }

    private void validateExclusiveGame(final UserEntity userEntity) throws GameNotFinishedException {
        final Optional<GameEntity> optionalGameEntity =
                gameRepository.findGameEntityByUserEntityIdAndActiveTrue(userEntity.getId());
        if (optionalGameEntity.isPresent()) {
            throw new GameNotFinishedException();
        }
    }
}
