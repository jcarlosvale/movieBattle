package com.letscode.moviesBattle.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class GameNotFoundException extends BusinessException{

    public GameNotFoundException() {
        super("Game Not Found. It is necessary to start one.");
    }
}
