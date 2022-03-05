package com.letscode.moviesBattle.domain.service.impl;

import com.letscode.moviesBattle.domain.dto.AnswerDto;
import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.dto.MovieDto;
import com.letscode.moviesBattle.domain.exception.*;
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

    private final int MAX_ERRORS = 3;

    @Override
    public GameDto startGame(final long userId) throws BusinessException {
        validateExclusiveGame(userId);
        final GameEntity gameEntity = createGame(userId);
        return toDto(gameEntity);
    }

    @Override
    public GameDto nextQuiz(final AnswerDto answerDto) throws BusinessException {
        GameEntity gameEntity =
                gameRepository
                        .findGameEntityByUserEntityId(answerDto.getUserId())
                        .orElseThrow(GameNotFoundException::new);
        validateErrorLimit(gameEntity);
        updateScore(gameEntity, answerDto.getImdbID());
        insertNewQuiz(gameEntity);
        return toDto(gameRepository.save(gameEntity));
    }

    @Override
    public GameDto stopGame(final long userId) throws BusinessException {
        GameEntity gameEntity =
                gameRepository
                        .findGameEntityByUserEntityIdAndActiveTrue(userId)
                        .orElseThrow(GameNotFoundException::new);
        gameEntity.setActive(false);
        return toDto(gameRepository.save(gameEntity));
    }

    private GameEntity insertNewQuiz(final GameEntity game) {
        QuizEntity quiz = quizService.generateQuizCandidate();
        while(game.getQuizzes().contains(quiz)) {
            quiz = quizService.generateQuizCandidate();
        }
        game.setLastQuizz(quiz);
        game.getQuizzes().add(quiz);
        return game;
    }

    private GameEntity updateScore(final GameEntity gameEntity, final String imdbID) {
        final String imdbWinner = evaluateWinnerId(gameEntity.getLastQuizz());
        if (imdbWinner.equals(imdbID)) {
            gameEntity.setRightAnswers(gameEntity.getRightAnswers()+1);
        } else {
            gameEntity.setWrongAnswers(gameEntity.getWrongAnswers()+1);
        }
        return gameEntity;
    }

    private String evaluateWinnerId(final QuizEntity quiz) {
        final double scoreOne = quiz.getMovieOne().getImdbRating() * quiz.getMovieOne().getImdbVotes();
        final double socreTwo = quiz.getMovieTwo().getImdbRating() * quiz.getMovieTwo().getImdbVotes();
        return scoreOne > socreTwo ? quiz.getMovieOne().getImdbID() : quiz.getMovieTwo().getImdbID();
    }

    private void validateErrorLimit(final GameEntity gameEntity) throws MaximumErrorReachedException {
        if (gameEntity.getWrongAnswers() >= MAX_ERRORS) {
            throw new MaximumErrorReachedException();
        }
    }

    private GameDto toDto(final GameEntity gameEntity) {
        return GameDto.builder()
                .id(gameEntity.getId())
                .movieOne(toDto(gameEntity.getLastQuizz().getMovieOne()))
                .movieTwo(toDto(gameEntity.getLastQuizz().getMovieTwo()))
                .rightAnswers(gameEntity.getRightAnswers())
                .wrongAnswers(gameEntity.getWrongAnswers())
                .build();
    }

    private MovieDto toDto(final MovieEntity movie) {
        return MovieDto.builder()
                .imdbID(movie.getImdbID())
                .title(movie.getTitle())
                .build();
    }

    private GameEntity createGame(final long userId) throws UserNotFoundException {
        final UserEntity userEntity = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        final QuizEntity quizEntity = quizService.generateQuizCandidate();
        quizService.save(quizEntity);
        final Set<QuizEntity> quizzes = new HashSet<>();
        quizzes.add(quizEntity);
        return gameRepository.save(GameEntity
                        .builder()
                        .userEntity(userEntity)
                        .active(true)
                        .lastQuizz(quizEntity)
                        .quizzes(quizzes)
                        .build()
        );
    }

    private void validateExclusiveGame(final long userId) throws GameNotFinishedException {
        final Optional<GameEntity> optionalGameEntity = gameRepository.findGameEntityByUserEntityIdAndActiveTrue(userId);
        if (optionalGameEntity.isPresent()) {
            throw new GameNotFinishedException();
        }
    }
}
