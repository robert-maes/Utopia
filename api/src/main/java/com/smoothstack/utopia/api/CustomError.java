package com.smoothstack.utopia.api;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
public class CustomError {

  private String message;

  public CustomError() {}

  public CustomError(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "CustomError{" + "message='" + message + '\'' + '}';
  }
}
