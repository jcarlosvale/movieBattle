package com.letscode.moviesBattle.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class GameNotFinishedException extends BusinessException {

    public GameNotFinishedException() {
        super("There is an active game for this user");
    }
}
