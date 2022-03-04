package com.letscode.moviesBattle.domain.service;

import com.letscode.moviesBattle.domain.dto.GameDto;
import com.letscode.moviesBattle.domain.exception.BusinessException;
import org.springframework.http.ResponseEntity;

public interface MoviesBattleService {

    GameDto startGame(final long userId) throws BusinessException;
}
