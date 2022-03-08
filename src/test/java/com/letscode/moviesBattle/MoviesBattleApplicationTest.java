package com.letscode.moviesBattle;

import static com.letscode.moviesBattle.view.rs.impl.Constants.MOVIES_BATTLE_MAPPING_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import com.letscode.moviesBattle.domain.dto.AnswerDto;
import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.dto.UserDto;
import com.letscode.moviesBattle.domain.repository.GameRepository;
import com.letscode.moviesBattle.domain.repository.MovieRepository;
import com.letscode.moviesBattle.domain.repository.model.GameEntity;
import com.letscode.moviesBattle.domain.repository.model.QuizEntity;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@Transactional
class MoviesBattleApplicationTest {

    private RestTemplate restTemplate;
    private String url;

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private MovieRepository movieRepository;

    @LocalServerPort
    private int randomServerPort = 0;

    @BeforeEach
    void beforeTest() {
        restTemplate = new RestTemplate();
        url = "http://localhost:" + randomServerPort + MOVIES_BATTLE_MAPPING_PATH;
    }

    @Test
    void startGameInvalidUser() {
        //GIVEN
        UserDto userDto =
                UserDto.builder()
                        .userId(-1)
                        .build();
        //WHEN
        Throwable throwable = catchThrowable(() -> restTemplate.postForEntity(url + "/startGame", userDto, GameDto.class));

        //THEN
        assertThat(((HttpClientErrorException) throwable).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void startGameNotFinishedPresent() {
        //GIVEN
        UserDto userDto =
                UserDto.builder()
                        .userId(111)  //USER ACTIVE GAME
                        .build();
        //WHEN
        Throwable throwable = catchThrowable(() -> restTemplate.postForEntity(url + "/startGame", userDto, GameDto.class));

        //THEN
        assertThat(((HttpClientErrorException) throwable).getStatusCode())
                .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void startGameOk() {
        //GIVEN
        UserDto userDto =
                UserDto.builder()
                        .userId(112)
                        .build();

        //WHEN
        ResponseEntity<GameDto> response = restTemplate.postForEntity(url + "/startGame", userDto, GameDto.class);

        //THEN
        GameDto actualGameDto = response.getBody();
        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
        assertThat(gameRepository.findGameEntityByUserEntityIdAndActiveTrue(userDto.getUserId()).isPresent())
                .isTrue();
        assertThat(actualGameDto.getWrongAnswers())
                .isEqualTo(0);
        assertThat(actualGameDto.getRightAnswers())
                .isEqualTo(0);
        assertThat(actualGameDto.getUserId())
                .isEqualTo(112);
        assertThat(actualGameDto.getMovieOne().getImdbID())
                .isNotEqualTo(actualGameDto.getMovieTwo().getImdbID());
    }

    @Test
    void nextQuizNotAllowedWithoutStartedGame() {
        //GIVEN
        AnswerDto answerDto =
                AnswerDto.builder()
                        .userId(113)
                        .imdbID("IMDB1")
                        .build();
        //WHEN
        Throwable throwable = catchThrowable(() -> restTemplate.postForEntity(url + "/nextQuiz", answerDto, GameDto.class));

        //THEN
        assertThat(((HttpClientErrorException) throwable).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void nextQuizNotAllowedErrorLimit() {
        //GIVEN
        AnswerDto answerDto =
                AnswerDto.builder()
                        .userId(114)
                        .imdbID("IMDB1")
                        .build();
        //WHEN
        Throwable throwable = catchThrowable(() -> restTemplate.postForEntity(url + "/nextQuiz", answerDto, GameDto.class));

        //THEN
        assertThat(((HttpClientErrorException) throwable).getStatusCode())
                .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void nextQuizRightAnswer() {
        //GIVEN
        AnswerDto answerDto =
                AnswerDto.builder()
                        .userId(115)
                        .imdbID("IMDB2")
                        .build();
        //WHEN
        ResponseEntity<GameDto> response = restTemplate.postForEntity(url + "/nextQuiz", answerDto, GameDto.class);

        //THEN
        GameEntity gameEntity = gameRepository.findById(115L).get();
        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
        assertThat(gameEntity.getUserEntity().getId())
                .isEqualTo(115);
        assertThat(gameEntity.getRightAnswers())
                .isEqualTo(5);
        assertThat(gameEntity.getWrongAnswers())
                .isEqualTo(2);
        assertThat(gameEntity.isActive())
                .isTrue();
    }

    @Test
    void nextQuizWrongAnswer() {
        //GIVEN
        AnswerDto answerDto =
                AnswerDto.builder()
                        .userId(116)
                        .imdbID("IMDB1")
                        .build();
        //WHEN
        ResponseEntity<GameDto> response = restTemplate.postForEntity(url + "/nextQuiz", answerDto, GameDto.class);

        //THEN
        GameEntity gameEntity = gameRepository.findById(116L).get();
        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
        assertThat(gameEntity.getUserEntity().getId())
                .isEqualTo(116);
        assertThat(gameEntity.getRightAnswers())
                .isEqualTo(4);
        assertThat(gameEntity.getWrongAnswers())
                .isEqualTo(3);
        assertThat(gameEntity.isActive())
                .isTrue();
    }

    @Test
    void nextQuizGenerateValid() {
        //GIVEN
        AnswerDto answerDto =
                AnswerDto.builder()
                        .userId(117)
                        .imdbID("IMDB3")
                        .build();

        QuizEntity expectedQuiz =
                QuizEntity.builder()
                        .movieOne(movieRepository.findById("IMDB1").get())
                        .movieTwo(movieRepository.findById("IMDB2").get())
                        .build();
        //WHEN
        ResponseEntity<GameDto> response = restTemplate.postForEntity(url + "/nextQuiz", answerDto, GameDto.class);

        //THEN
        GameEntity gameEntity = gameRepository.findById(117l).get();
        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
        assertThat(gameEntity.getUserEntity().getId())
                .isEqualTo(117);
        assertThat(gameEntity.getRightAnswers())
                .isEqualTo(5);
        assertThat(gameEntity.getWrongAnswers())
                .isEqualTo(2);
        assertThat(gameEntity.isActive())
                .isTrue();
        assertThat(gameEntity.getLastQuiz())
                .isEqualTo(expectedQuiz);
    }
}