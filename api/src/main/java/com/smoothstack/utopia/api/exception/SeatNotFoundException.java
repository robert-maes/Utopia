package com.smoothstack.utopia.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
@ResponseStatus(
  value = HttpStatus.NOT_FOUND,
  reason = "The requested seat does not exist"
)
public class SeatNotFoundException extends RuntimeException {}
