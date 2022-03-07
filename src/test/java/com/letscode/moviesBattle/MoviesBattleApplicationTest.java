package com.letscode.moviesBattle;

import static com.letscode.moviesBattle.view.rs.impl.Constants.MOVIES_BATTLE_MAPPING_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
class MoviesBattleApplicationTest {

    private RestTemplate restTemplate;
    private String url;

    @LocalServerPort
    private int randomServerPort = 0;

    @BeforeEach
    void beforeTest() {
        restTemplate = new RestTemplate();
        url = "http://localhost:" +  randomServerPort + MOVIES_BATTLE_MAPPING_PATH;
    }

    @Test
    void startGameInvalidUser() {
        //GIVEN
        UserDto userDto =
                UserDto.builder()
                        .userId(-1)
                        .build();
        //WHEN
        Throwable throwable = catchThrowable(() ->restTemplate.postForEntity(url + "/startGame", userDto, GameDto.class));

        //THEN
        assertThat(((HttpClientErrorException) throwable).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void startGameNotExclusive() {
        //GIVEN
        UserDto userDto =
                UserDto.builder()
                        .userId(111)  //USER ACTIVE GAME
                        .build();
        //WHEN
        Throwable throwable = catchThrowable(() ->restTemplate.postForEntity(url + "/startGame", userDto, GameDto.class));

        //THEN
        assertThat(((HttpClientErrorException) throwable).getStatusCode())
                .isEqualTo(HttpStatus.CONFLICT);
    }
}