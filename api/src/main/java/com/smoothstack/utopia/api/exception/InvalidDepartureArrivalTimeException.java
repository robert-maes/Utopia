package com.smoothstack.utopia.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
@ResponseStatus(
  value = HttpStatus.BAD_REQUEST,
  reason = "Arrival time cannot come before departure time"
)
public class InvalidDepartureArrivalTimeException extends RuntimeException {}
