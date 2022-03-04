package com.letscode.moviesBattle.view.rs;

import com.letscode.moviesBattle.domain.dto.AnswerDto;
import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.exception.BusinessException;
import org.springframework.http.ResponseEntity;

public interface MoviesBattleController {

    ResponseEntity<GameDto> startGame(long userId) throws BusinessException;

    ResponseEntity<GameDto> nextQuiz(AnswerDto answerDto) throws BusinessException;

    ResponseEntity<GameDto> stopGame(long userId) throws BusinessException;

}
