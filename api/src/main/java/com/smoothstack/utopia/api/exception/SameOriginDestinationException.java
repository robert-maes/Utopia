package com.smoothstack.utopia.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
@ResponseStatus(
  value = HttpStatus.BAD_REQUEST,
  reason = "Origin airport and destination airport cannot be the same"
)
public class SameOriginDestinationException extends RuntimeException {}
