package com.smoothstack.utopia.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
  value = HttpStatus.NOT_FOUND,
  reason = "The requested traveler does not exist"
)
public class TravelerNotFoundException extends RuntimeException {}
