package com.letscode.moviesBattle.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MaximumErrorReachedException extends BusinessException {

    public MaximumErrorReachedException() {
        super("Maximum errors reached. End this game.");
    }
}
