package com.letscode.moviesBattle.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO: implements a ControllerAdvice
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Game Not Found. It is necessary to start one.")
public class GameNotFoundException extends BusinessException{
}
