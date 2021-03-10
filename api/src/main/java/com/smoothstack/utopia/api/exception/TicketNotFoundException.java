package com.smoothstack.utopia.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
  value = HttpStatus.NOT_FOUND,
  reason = "The requested ticket does not exist"
)
public class TicketNotFoundException extends RuntimeException {}
