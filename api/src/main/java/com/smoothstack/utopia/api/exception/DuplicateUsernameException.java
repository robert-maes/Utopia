package com.smoothstack.utopia.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
  value = HttpStatus.CONFLICT,
  reason = "An employee with this username already exists"
)
public class DuplicateUsernameException extends RuntimeException {}
