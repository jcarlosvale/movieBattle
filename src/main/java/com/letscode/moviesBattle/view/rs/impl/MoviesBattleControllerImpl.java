package com.letscode.moviesBattle.view.rs.impl;

import com.letscode.moviesBattle.domain.dto.AnswerDto;
import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.exception.BusinessException;
import com.letscode.moviesBattle.domain.service.MoviesBattleService;
import com.letscode.moviesBattle.view.rs.MoviesBattleController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.letscode.moviesBattle.view.rs.impl.Constants.MOVIES_BATTLE_MAPPING_PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping(MOVIES_BATTLE_MAPPING_PATH)
public class MoviesBattleControllerImpl implements MoviesBattleController {

    private final MoviesBattleService service;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> ping(@RequestBody(required = false) final String user) throws BusinessException {
      return new ResponseEntity<>("PING " + user, HttpStatus.OK);

    }

    @Override
    @PostMapping(path = "/startGame", consumes = "application/json")
    public ResponseEntity<GameDto> startGame(@RequestBody final long userId) throws BusinessException {
        return new ResponseEntity<>(service.startGame(userId), HttpStatus.CREATED);
    }

    @Override
    @PostMapping(path = "/nextQuiz")
    public ResponseEntity<GameDto> nextQuiz(@RequestBody final AnswerDto answerDto) throws BusinessException {
        return new ResponseEntity<>(service.nextQuiz(answerDto), HttpStatus.CREATED);
    }

    @Override
    @PostMapping(path = "/endGame")
    public ResponseEntity<GameDto> stopGame(@RequestBody final long userId) throws BusinessException {
        return new ResponseEntity<>(service.stopGame(userId), HttpStatus.CREATED);
    }
}
