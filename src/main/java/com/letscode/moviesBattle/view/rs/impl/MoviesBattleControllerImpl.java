package com.letscode.moviesBattle.view.rs.impl;

import static com.letscode.moviesBattle.view.rs.impl.Constants.MOVIES_BATTLE_MAPPING_PATH;

import com.letscode.moviesBattle.domain.dto.AnswerDto;
import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.dto.RankingOfPlayersDto;
import com.letscode.moviesBattle.domain.dto.UserDto;
import com.letscode.moviesBattle.domain.exception.BusinessException;
import com.letscode.moviesBattle.domain.service.MoviesBattleService;
import com.letscode.moviesBattle.view.rs.MoviesBattleController;
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
    @PostMapping(path = "/startGame", consumes = "application/json")
    public ResponseEntity<GameDto> startGame(@RequestBody final UserDto userDto) throws BusinessException {
        return new ResponseEntity<>(service.startGame(userDto), HttpStatus.CREATED);
    }

    @Override
    @PostMapping(path = "/nextQuiz")
    public ResponseEntity<GameDto> nextQuiz(@RequestBody final AnswerDto answerDto) throws BusinessException {
        return new ResponseEntity<>(service.nextQuiz(answerDto), HttpStatus.CREATED);
    }

    @Override
    @PostMapping(path = "/stopGame")
    public ResponseEntity<GameDto> stopGame(@RequestBody final UserDto userDto) throws BusinessException {
        return new ResponseEntity<>(service.stopGame(userDto), HttpStatus.CREATED);
    }

    @Override
    @GetMapping(path = "/ranking/{top}")
    public ResponseEntity<RankingOfPlayersDto> getRanking(@PathVariable final int top) throws BusinessException {
        return new ResponseEntity<>(service.getRanking(top), HttpStatus.OK);
    }
}
