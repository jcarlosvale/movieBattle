package com.letscode.moviesBattle.view.rs.impl;

import static com.letscode.moviesBattle.view.rs.impl.Constants.MOVIES_BATTLE_MAPPING_PATH;

import com.letscode.moviesBattle.domain.dto.AnswerDto;
import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.dto.RankingOfPlayersDto;
import com.letscode.moviesBattle.domain.dto.UserDto;
import com.letscode.moviesBattle.domain.exception.BusinessException;
import com.letscode.moviesBattle.domain.service.MoviesBattleService;
import com.letscode.moviesBattle.view.rs.MoviesBattleController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(MOVIES_BATTLE_MAPPING_PATH)
public class MoviesBattleControllerImpl implements MoviesBattleController {

    private final MoviesBattleService service;

    @Override
    @Operation(summary = "Start a game if it does not exist an previous game started and not stopped. \nThe generated quiz is unique inside a game")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Game info containing the total right answers, wrong answers and the generated first quiz.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GameDto.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found, the provided user id is not valid.",
                    content = @Content),
            @ApiResponse(
                    responseCode = "409",
                    description = "Game not finished, exists a current game started, it must be stopped to start a new one.",
                    content = @Content)})
    @PostMapping(path = "/startGame", consumes = "application/json")
    public ResponseEntity<GameDto> startGame(@RequestBody final UserDto userDto)
            throws BusinessException {
        return new ResponseEntity<>(service.startGame(userDto), HttpStatus.CREATED);
    }

    @Override
    @Operation(summary = "This endpoint is used to answer the last quiz and ask a new one. \nThe new generated quiz is unique inside a game")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Game info containing the total right answers, wrong answers and the generated quiz.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GameDto.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found, the provided user id is not valid.",
                    content = @Content),
            @ApiResponse(
                    responseCode = "409",
                    description = "Max. errors reached: 3, it is not possible to generate a new quiz after 3 errors. It must to stop the current game.",
                    content = @Content)})
    @PostMapping(path = "/nextQuiz")
    public ResponseEntity<GameDto> nextQuiz(@RequestBody final AnswerDto answerDto)
            throws BusinessException {
        return new ResponseEntity<>(service.nextQuiz(answerDto), HttpStatus.CREATED);
    }

    @Override
    @Operation(summary = "Stop a game previously started.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Game info containing the total right answers, wrong answers and last quiz generated.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GameDto.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Game not found, it does not exist a started game",
                    content = @Content)})
    @PostMapping(path = "/stopGame")
    public ResponseEntity<GameDto> stopGame(@RequestBody final UserDto userDto) throws BusinessException {
        return new ResponseEntity<>(service.stopGame(userDto), HttpStatus.CREATED);
    }

    @Override
    @Operation(summary = "Get the top best players and their points")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The list of top players",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RankingOfPlayersDto.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid top value, less than equals 0",
                    content = @Content)})
    @GetMapping(path = "/ranking/{top}")
    public ResponseEntity<RankingOfPlayersDto> getRanking(
            @Parameter(description = "number of top players to retrieve") @PathVariable final int top)
            throws BusinessException {
        return new ResponseEntity<>(service.getRanking(top), HttpStatus.OK);
    }
}
