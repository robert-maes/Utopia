package com.smoothstack.utopia.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
@ResponseStatus(
  value = HttpStatus.CONFLICT,
  reason = "An airport with this ID already exists"
)
public class DuplicateAirportException extends RuntimeException {}
