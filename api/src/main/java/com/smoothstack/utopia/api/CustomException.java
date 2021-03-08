package com.smoothstack.utopia.api;

/**
 * @author Rob Maes
 * Mon Mar 8 2021
 * This is a custom exception thrown by controllers and returned to the client as JSON errors
 */
public class CustomException extends Exception {

  public CustomException(String errorMessage) {
    super(errorMessage);
  }
}
