package com.smoothstack.utopia.api;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
  extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException exception,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request
  ) {
    List<ValidationError> validationErrors = exception
      .getBindingResult()
      .getFieldErrors()
      .stream()
      .map(
        fieldError ->
          new ValidationError(
            fieldError.getField(),
            fieldError.getRejectedValue(),
            fieldError.getDefaultMessage()
          )
      )
      .distinct()
      .collect(Collectors.toList());

    return handleExceptionInternal(
      exception,
      validationErrors,
      headers,
      HttpStatus.BAD_REQUEST,
      request
    );
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
    HttpMessageNotReadableException exception,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request
  ) {
    return handleExceptionInternal(
      exception,
      new CustomError("Unable to process input data"),
      headers,
      HttpStatus.UNPROCESSABLE_ENTITY,
      request
    );
  }
}
