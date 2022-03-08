package com.letscode.moviesBattle.view.rs.impl;

import static com.letscode.moviesBattle.view.rs.impl.Constants.MOVIES_BATTLE_MAPPING_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letscode.moviesBattle.config.MoviesLoader;
import com.letscode.moviesBattle.config.UsersLoader;
import com.letscode.moviesBattle.config.service.impl.UserDetailsServiceImpl;
import com.letscode.moviesBattle.domain.dto.AnswerDto;
import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.dto.MovieDto;
import com.letscode.moviesBattle.domain.dto.PlayerPositionDto;
import com.letscode.moviesBattle.domain.dto.RankingOfPlayersDto;
import com.letscode.moviesBattle.domain.dto.UserDto;
import com.letscode.moviesBattle.domain.exception.GameNotFinishedException;
import com.letscode.moviesBattle.domain.exception.GameNotFoundException;
import com.letscode.moviesBattle.domain.exception.InvalidValueException;
import com.letscode.moviesBattle.domain.exception.MaximumErrorReachedException;
import com.letscode.moviesBattle.domain.exception.UserNotFoundException;
import com.letscode.moviesBattle.domain.service.MoviesBattleService;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(MoviesBattleControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
class MoviesBattleControllerImplTest {

    private final String USER_DTO_JSON = "{\"userId\" : 1}";
    private final UserDto userDto = UserDto.builder().userId(1).build();

    private final String ANSWER_DTO_JSON = "{\"userId\": 1, \"imdbID\":\"answerImdbId\"}";
    private final AnswerDto answerDto = AnswerDto.builder() .userId(1).imdbID("answerImdbId").build();

    private final String RANKING_OF_PLAYERS_DTO_JSON = "{\"playerPositionDtoList\":[{\"position\":1, \"user\":\"user\",\"points\":100}]}";
    private final RankingOfPlayersDto rankingOfPlayersDto =
            RankingOfPlayersDto.builder()
                    .playerPositionDtoList(
                            List.of(
                                    PlayerPositionDto.builder().user("user").points(100).build()
                            ))
                    .build();

    private final GameDto gameDto =
            GameDto.builder()
                    .userId(1L)
                    .movieOne(MovieDto.builder().imdbID("movieOneID").title("movieOne title").build())
                    .movieTwo(MovieDto.builder().imdbID("movieTwoID").title("movieTwo title").build())
                    .rightAnswers(5)
                    .wrongAnswers(3)
                    .build();

    @MockBean
    private MoviesBattleService service;
    @MockBean
    private MoviesLoader moviesLoader;
    @MockBean
    private UsersLoader usersLoader;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void startGameOk() throws Exception {
        //GIVEN
        given(service.startGame(userDto))
                .willReturn(gameDto);

        //WHEN
        MvcResult result = mockMvc
                .perform(post(MOVIES_BATTLE_MAPPING_PATH + "/startGame").contentType(APPLICATION_JSON).content(USER_DTO_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        final var actual = objectMapper.readValue(result.getResponse().getContentAsString(), GameDto.class);

        //THEN
        assertThat(actual).isEqualTo(gameDto);
    }

    @Test
    void nextQuiz() throws Exception {
        //GIVEN
        given(service.nextQuiz(answerDto))
                .willReturn(gameDto);

        //WHEN
        MvcResult result = mockMvc
                .perform(post(MOVIES_BATTLE_MAPPING_PATH + "/nextQuiz").contentType(APPLICATION_JSON).content(ANSWER_DTO_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        final var actual = objectMapper.readValue(result.getResponse().getContentAsString(), GameDto.class);

        //THEN
        assertThat(actual).isEqualTo(gameDto);
    }

    @Test
    void stopGameOk() throws Exception {
        //GIVEN
        given(service.stopGame(userDto))
                .willReturn(gameDto);

        //WHEN
        MvcResult result = mockMvc
                .perform(post(MOVIES_BATTLE_MAPPING_PATH + "/stopGame").contentType(APPLICATION_JSON).content(USER_DTO_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        final var actual = objectMapper.readValue(result.getResponse().getContentAsString(), GameDto.class);

        //THEN
        assertThat(actual).isEqualTo(gameDto);
    }

    @Test
    void getRankingOk() throws Exception {
        //GIVEN
        final var top = 1;
        given(service.getRanking(top))
                .willReturn(rankingOfPlayersDto);

        //WHEN
        MvcResult result = mockMvc
                .perform(get(MOVIES_BATTLE_MAPPING_PATH + "/ranking/{top}", top)
                        .contentType(APPLICATION_JSON)
                        .content(RANKING_OF_PLAYERS_DTO_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        final var actual = objectMapper.readValue(result.getResponse().getContentAsString(), RankingOfPlayersDto.class);

        //THEN
        assertThat(actual).isEqualTo(rankingOfPlayersDto);
    }

    @ParameterizedTest
    @MethodSource("generateExceptions")
    void controllerThrowsException(final Exception exception, final int expectedHttpResponseCode) throws Exception {
        //GIVEN
        given(service.startGame(userDto))
                .willThrow(exception);

        //WHEN THEN
        mockMvc.perform(post(MOVIES_BATTLE_MAPPING_PATH + "/startGame").contentType(APPLICATION_JSON)
                        .content(USER_DTO_JSON))
                .andDo(print())
                .andExpect(status().is(expectedHttpResponseCode));

    }

    private static Stream<Arguments> generateExceptions() {
        return Stream.of(
                arguments(new UserNotFoundException(), HttpStatus.NOT_FOUND.value()),
                arguments(new GameNotFinishedException(), HttpStatus.CONFLICT.value()),
                arguments(new GameNotFoundException(), HttpStatus.NOT_FOUND.value()),
                arguments(new InvalidValueException(), HttpStatus.BAD_REQUEST.value()),
                arguments(new MaximumErrorReachedException(), HttpStatus.CONFLICT.value())
        );
    }

}