package com.letscode.moviesBattle.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super("USer not found.");
    }
}
