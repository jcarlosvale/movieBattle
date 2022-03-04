package com.letscode.moviesBattle.view.rs.impl;

import static com.letscode.moviesBattle.view.rs.impl.Constants.MOVIES_BATTLE_MAPPING_PATH;

import com.letscode.moviesBattle.domain.dto.AnswerDto;
import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.view.rs.MoviesBattleController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MOVIES_BATTLE_MAPPING_PATH)
public class MoviesBattleControllerImpl implements MoviesBattleController {

    @Override
    @PostMapping(path = "/startGame")
    public ResponseEntity<GameDto> startGame() {
        return new ResponseEntity<>(GameDto.builder().build(), HttpStatus.CREATED);
    }

    @Override
    @PostMapping(path = "/nextQuizz")
    public ResponseEntity<GameDto> nextQuizz(@RequestBody final AnswerDto answerDto) {
        return new ResponseEntity<>(GameDto.builder().build(), HttpStatus.CREATED);
    }

    @Override
    @PostMapping(path = "/endGame")
    public ResponseEntity<GameDto> endGame() {
        return new ResponseEntity<>(GameDto.builder().build(), HttpStatus.CREATED);
    }
}
