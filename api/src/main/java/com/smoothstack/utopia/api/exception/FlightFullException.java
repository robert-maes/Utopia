package com.smoothstack.utopia.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
  value = HttpStatus.BAD_REQUEST,
  reason = "Cannot add seats to the flight. The flight is at capacity"
)
public class FlightFullException extends RuntimeException {}
