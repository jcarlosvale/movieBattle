package com.letscode.moviesBattle.domain.service;

import com.letscode.moviesBattle.domain.dto.AnswerDto;
import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.dto.UserDto;
import com.letscode.moviesBattle.domain.exception.BusinessException;

public interface MoviesBattleService {

    GameDto startGame(final UserDto userDto) throws BusinessException;

    GameDto nextQuiz(AnswerDto answerDto) throws BusinessException;

    GameDto stopGame(UserDto userDto) throws BusinessException;
}
