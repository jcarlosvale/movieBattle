package com.letscode.moviesBattle.domain.service;

import com.letscode.moviesBattle.domain.dto.AnswerDto;
import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.exception.BusinessException;

public interface MoviesBattleService {

    GameDto startGame(final long userId) throws BusinessException;

    GameDto nextQuiz(AnswerDto answerDto) throws BusinessException;

    GameDto stopGame(long userId) throws BusinessException;
}
