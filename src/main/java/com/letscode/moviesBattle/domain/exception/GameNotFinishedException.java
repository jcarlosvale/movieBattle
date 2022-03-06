package com.letscode.moviesBattle.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "There is an active game for this user")
public class GameNotFinishedException extends BusinessException {
}
