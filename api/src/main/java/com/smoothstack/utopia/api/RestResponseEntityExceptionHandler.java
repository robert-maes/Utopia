package com.smoothstack.utopia.api;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Rob Maes
 * Mon Mar 8 2021
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler
  extends ResponseEntityExceptionHandler {

  // This catches @Valid validation errors and returns them as a JSON list to the client
  @Override
  @ResponseStatus
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request
  ) {
    final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    Map<String, Set<String>> errorsMap = fieldErrors
      .stream()
      .collect(
        Collectors.groupingBy(
          FieldError::getField,
          Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())
        )
      );
    return new ResponseEntity(
      errorsMap.isEmpty() ? ex : errorsMap,
      headers,
      status
    );
  }

  // This catches CustomExceptions and returns the following JSON to the client
  /*
    {
      "error": [ "$errorMessage" ]
    }
   */
  @ExceptionHandler(CustomException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ResponseEntity<String> handleCustomException(CustomException e) {
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add("Content-Type", "application/json");
    return new ResponseEntity<String>(
      "{\"error\": [\"" + e.getMessage() + "\"]}",
      headers,
      HttpStatus.BAD_REQUEST
    );
  }
}
