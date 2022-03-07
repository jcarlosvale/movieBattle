package com.letscode.moviesBattle;

import static com.letscode.moviesBattle.view.rs.impl.Constants.MOVIES_BATTLE_MAPPING_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.dto.UserDto;
import com.letscode.moviesBattle.domain.repository.GameRepository;
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
class MoviesBattleApplicationTest {

    private RestTemplate restTemplate;
    private String url;

    @Autowired
    private GameRepository gameRepository;

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
}