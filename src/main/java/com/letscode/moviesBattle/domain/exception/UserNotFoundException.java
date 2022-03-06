package com.letscode.moviesBattle.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Ueer not found")
public class UserNotFoundException extends BusinessException {
}
