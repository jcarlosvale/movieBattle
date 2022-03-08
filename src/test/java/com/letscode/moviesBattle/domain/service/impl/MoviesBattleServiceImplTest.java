package com.letscode.moviesBattle.domain.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.letscode.moviesBattle.domain.dto.AnswerDto;
import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.dto.RankingOfPlayersDto;
import com.letscode.moviesBattle.domain.dto.UserDto;
import com.letscode.moviesBattle.domain.exception.BusinessException;
import com.letscode.moviesBattle.domain.exception.GameNotFinishedException;
import com.letscode.moviesBattle.domain.exception.GameNotFoundException;
import com.letscode.moviesBattle.domain.exception.InvalidValueException;
import com.letscode.moviesBattle.domain.exception.MaximumErrorReachedException;
import com.letscode.moviesBattle.domain.exception.UserNotFoundException;
import com.letscode.moviesBattle.domain.repository.GameRepository;
import com.letscode.moviesBattle.domain.repository.model.GameEntity;
import com.letscode.moviesBattle.domain.repository.model.QuizEntity;
import com.letscode.moviesBattle.domain.repository.model.UserEntity;
import com.letscode.moviesBattle.domain.repository.projection.RankingProjection;
import com.letscode.moviesBattle.domain.service.QuizService;
import com.letscode.moviesBattle.domain.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MoviesBattleServiceImplTest {

    private static final long USER_ID = 1L;

    @Mock
    private GameDto gameDto;
    @Mock
    private AnswerDto answerDto;
    @Mock
    private UserDto userDto;
    @Mock
    private RankingOfPlayersDto rankingOfPlayersDto;

    @Mock
    private GameEntity gameEntity;
    @Mock
    private UserEntity userEntity;
    @Mock
    private QuizEntity quizEntity;

    @Mock
    private UserService userService;
    @Mock
    private QuizService quizService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private Converter converter;


    @InjectMocks
    private MoviesBattleServiceImpl service;

    @Test
    void startGameNotAllowedUserNotFound() {
        //GIVEN
        given(userDto.getUserId())
                .willReturn(USER_ID);
        given(userService.retrieveUserById(USER_ID))
                .willReturn(Optional.empty());

        //WHEN THEN
        assertThatThrownBy(() -> service.startGame(userDto))
                .isInstanceOf(UserNotFoundException.class);

        verify(quizService, never()).save(any(QuizEntity.class));
        verify(gameRepository, never()).save(any(GameEntity.class));
    }

    @Test
    void startGameNotAllowedUserHasAnOpenGame() {
        //GIVEN
        given(userDto.getUserId())
                .willReturn(USER_ID);
        given(userService.retrieveUserById(USER_ID))
                .willReturn(Optional.of(userEntity));
        given(userEntity.getId())
                .willReturn(USER_ID);
        given(gameRepository.findGameEntityByUserEntityIdAndActiveTrue(USER_ID))
                .willReturn(Optional.of(mock(GameEntity.class)));

        //WHEN THEN
        assertThatThrownBy(() -> service.startGame(userDto))
                .isInstanceOf(GameNotFinishedException.class);

        verify(quizService, never()).save(any(QuizEntity.class));
        verify(gameRepository, never()).save(any(GameEntity.class));
    }

    @Test
    void startGameOk() throws BusinessException {
        //GIVEN
        given(userDto.getUserId())
                .willReturn(USER_ID);

        given(userService.retrieveUserById(USER_ID))
                .willReturn(Optional.of(userEntity));

        given(userEntity.getId())
                .willReturn(USER_ID);

        given(gameRepository.findGameEntityByUserEntityIdAndActiveTrue(USER_ID))
                .willReturn(Optional.empty());
        given(gameRepository.save(any(GameEntity.class)))
                .willReturn(gameEntity);

        given(quizService.generateQuizCandidate())
                .willReturn(quizEntity);

        given(converter.toDto(gameEntity))
                .willReturn(gameDto);

        //WHEN
        final var actualDto = service.startGame(userDto);

        //THEN
        verify(quizService, times(1))
                .save(quizEntity);
        verify(gameRepository, times(1))
                .save(any(GameEntity.class));
        assertThat(actualDto)
                .isEqualTo(gameDto);
    }

    @Test
    void nextQuizNotAllowedWithoutGameStarted() {
        //GIVEN
        given(answerDto.getUserId())
                .willReturn(USER_ID);
        given(gameRepository.findGameEntityByUserEntityIdAndActiveTrue(USER_ID))
                .willReturn(Optional.empty());

        //WHEN THEN
        assertThatThrownBy(() -> service.nextQuiz(answerDto))
                .isInstanceOf(GameNotFoundException.class);

        verify(quizService, never()).save(any(QuizEntity.class));
        verify(gameRepository, never()).save(any(GameEntity.class));
    }

    @Test
    void nextQuizNotAllowedMaximumErrorsReached() {
        //GIVEN
        given(answerDto.getUserId())
                .willReturn(USER_ID);
        given(gameRepository.findGameEntityByUserEntityIdAndActiveTrue(USER_ID))
                .willReturn(Optional.of(gameEntity));
        given(gameEntity.getWrongAnswers())
                .willReturn(3);

        //WHEN THEN
        assertThatThrownBy(() -> service.nextQuiz(answerDto))
                .isInstanceOf(MaximumErrorReachedException.class);

        verify(quizService, never()).save(any(QuizEntity.class));
        verify(gameRepository, never()).save(any(GameEntity.class));
    }

    @Test
    void nextQuizOkAndRightQuestion() throws BusinessException {
        //GIVEN
        final var rightQuestions = 5;
        final var imdbID = "someAnswer";
        final var quizzes = new HashSet<QuizEntity>();
        final var quiz = mock(QuizEntity.class);
        final var lastQuiz = quiz;

        given(answerDto.getUserId())
                .willReturn(USER_ID);
        given(answerDto.getImdbID())
                .willReturn(imdbID);

       given(gameRepository.findGameEntityByUserEntityIdAndActiveTrue(USER_ID))
                .willReturn(Optional.of(gameEntity));

        given(gameEntity.getRightAnswers())
                .willReturn(rightQuestions);
        given(gameEntity.getLastQuiz())
                .willReturn(quizEntity);
        given(gameEntity.getQuizzes())
                .willReturn(quizzes);

        given(quizService.evaluateWinnerId(quizEntity))
                .willReturn(imdbID);
        given(quizService.generateQuizCandidate())
                .willReturn(lastQuiz);

        given(converter.toDto(gameEntity))
                .willReturn(gameDto);

        //WHEN
        final var actualDto = service.nextQuiz(answerDto);

        //THEN
        verify(quizService, times(1))
                .save(quiz);
        verify(gameRepository, times(1))
                .save(gameEntity);
        assertThat(actualDto)
                .isEqualTo(gameDto);
        verify(gameEntity, times(1))
                .setRightAnswers(rightQuestions+1);
        verify(gameEntity, never())
                .setWrongAnswers(anyInt());
        assertThat(gameEntity.getQuizzes().size())
                .isEqualTo(1);
        assertThat(gameEntity.getQuizzes().contains(lastQuiz))
                .isTrue();
    }

    @Test
    void nextQuizOkAndWrongQuestion() throws BusinessException {
        //GIVEN
        final var wrongQuestions = 2;
        final var imdbID = "someAnswer";
        final var winnerImdbID = "otherAnswer";
        final var quizzes = new HashSet<QuizEntity>();
        final var quiz = mock(QuizEntity.class);
        final var lastQuiz = mock(QuizEntity.class);

        given(answerDto.getUserId())
                .willReturn(USER_ID);
        given(answerDto.getImdbID())
                .willReturn(imdbID);

        given(gameRepository.findGameEntityByUserEntityIdAndActiveTrue(USER_ID))
                .willReturn(Optional.of(gameEntity));

        given(gameEntity.getWrongAnswers())
                .willReturn(wrongQuestions);
        given(gameEntity.getLastQuiz())
                .willReturn(quizEntity);
        given(gameEntity.getQuizzes())
                .willReturn(quizzes);

        given(quizService.evaluateWinnerId(quizEntity))
                .willReturn(winnerImdbID);
        given(quizService.generateQuizCandidate())
                .willReturn(lastQuiz);

        given(converter.toDto(gameEntity))
                .willReturn(gameDto);

        //WHEN
        final var actualDto = service.nextQuiz(answerDto);

        //THEN
        verify(quizService, never())
                .save(quiz);
        verify(gameRepository, times(1))
                .save(gameEntity);
        assertThat(actualDto)
                .isEqualTo(gameDto);
        verify(gameEntity, times(1))
                .setWrongAnswers(wrongQuestions+1);
        verify(gameEntity, never())
                .setRightAnswers(anyInt());
        assertThat(gameEntity.getQuizzes().size())
                .isEqualTo(1);
        assertThat(gameEntity.getQuizzes().contains(lastQuiz))
                .isTrue();
    }

    @Test
    void stopGameNotAllowedWithoutGameStarted() {
        //GIVEN
        given(userDto.getUserId())
                .willReturn(USER_ID);
        given(gameRepository.findGameEntityByUserEntityIdAndActiveTrue(USER_ID))
                .willReturn(Optional.empty());

        //WHEN THEN
        assertThatThrownBy(() -> service.stopGame(userDto))
                .isInstanceOf(GameNotFoundException.class);

        verify(gameRepository, never()).save(any(GameEntity.class));
    }

    @Test
    void stopGameOk() throws BusinessException {
        //GIVEN
        given(userDto.getUserId())
                .willReturn(USER_ID);
        given(gameRepository.findGameEntityByUserEntityIdAndActiveTrue(USER_ID))
                .willReturn(Optional.of(gameEntity));
        given(gameRepository.save(gameEntity))
                .willReturn(gameEntity);
        given(converter.toDto(gameEntity))
                .willReturn(gameDto);

        //WHEN THEN
        final var actualDto =  service.stopGame(userDto);

        verify(gameRepository, times(1)).save(gameEntity);
        verify(gameEntity, never()).setActive(true);
        verify(gameEntity, times(1)).setActive(false);
        assertThat(actualDto).isEqualTo(gameDto);
    }

    @Test
    void getRankingNotAllowedUsingInvalidTopValue() {
        //GIVEN WHEN THEN
        assertThatThrownBy(() -> service.getRanking(0))
                .isInstanceOf(InvalidValueException.class);
    }

    @Test
    void getRankingOk() throws BusinessException {
        //GIVEN
        final var top = 2;
        final var rankingProjectionList = List.of(mock(RankingProjection.class));
        given(gameRepository.getRanking(top))
                .willReturn(rankingProjectionList);
        given(converter.toDto(rankingProjectionList))
                .willReturn(rankingOfPlayersDto);

        //WHEN
        final var dto = service.getRanking(top);

        //THEN
        assertThat(dto).isEqualTo(rankingOfPlayersDto);
    }
}
