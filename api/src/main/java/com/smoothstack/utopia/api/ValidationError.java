package com.smoothstack.utopia.api;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
public class ValidationError {

  private String fieldName;
  private Object rejectedValue;
  private String message;

  public ValidationError() {}

  public ValidationError(
    String fieldName,
    Object rejectedValue,
    String message
  ) {
    this.fieldName = fieldName;
    this.rejectedValue = rejectedValue;
    this.message = message;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public Object getRejectedValue() {
    return rejectedValue;
  }

  public void setRejectedValue(Object rejectedValue) {
    this.rejectedValue = rejectedValue;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return (
      "ValidationError{" +
      "fieldName='" +
      fieldName +
      '\'' +
      ", rejectedValue=" +
      rejectedValue +
      ", message='" +
      message +
      '\'' +
      '}'
    );
  }
}
