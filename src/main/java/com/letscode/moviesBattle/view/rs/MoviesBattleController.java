package com.letscode.moviesBattle.view.rs;

import com.letscode.moviesBattle.domain.dto.AnswerDto;
import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.dto.RankingOfPlayersDto;
import com.letscode.moviesBattle.domain.dto.UserDto;
import com.letscode.moviesBattle.domain.exception.BusinessException;
import org.springframework.http.ResponseEntity;

public interface MoviesBattleController {

    ResponseEntity<GameDto> startGame(UserDto userDto) throws BusinessException;

    ResponseEntity<GameDto> nextQuiz(AnswerDto answerDto) throws BusinessException;

    ResponseEntity<GameDto> stopGame(UserDto userDto) throws BusinessException;

    ResponseEntity<RankingOfPlayersDto> getRanking(int top) throws BusinessException;

}
