package com.smoothstack.utopia.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
  value = HttpStatus.CONFLICT,
  reason = "Cannot set total seats to be less than reserved seats. Please remove seats first."
)
public class TotalSeatsLessThanReservedSeatsException
  extends RuntimeException {}
