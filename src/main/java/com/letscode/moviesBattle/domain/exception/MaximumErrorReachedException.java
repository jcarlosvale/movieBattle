package com.letscode.moviesBattle.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Maximum errors reached. End this game")
public class MaximumErrorReachedException extends BusinessException {
}
