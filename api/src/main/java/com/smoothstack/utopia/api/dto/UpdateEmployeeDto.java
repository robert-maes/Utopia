package com.smoothstack.utopia.api.dto;

import java.util.Optional;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateEmployeeDto {

  private Optional<@Size(
    min = 1,
    max = 255,
    message = "First name must be between 1 - 255 characters"
  ) String> givenName = Optional.empty();

  private Optional<@Size(
    min = 1,
    max = 255,
    message = "Last name must be between 1 - 255 characters"
  ) String> familyName = Optional.empty();

  private Optional<@Size(
    min = 1,
    max = 255,
    message = "Email must be between 1 - 255 characters"
  ) @Email(message = "Email must be valid") String> email = Optional.empty();

  private Optional<@Size(
    min = 1,
    max = 255,
    message = "Phone number must be between 1 - 255 characters"
  ) @Pattern(
    regexp = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$",
    message = "Phone number must be valid"
  ) String> phoneNumber = Optional.empty();

  public Optional<String> getGivenName() {
    return givenName;
  }

  public Optional<String> getFamilyName() {
    return familyName;
  }

  public Optional<String> getEmail() {
    return email;
  }

  public Optional<String> getPhoneNumber() {
    return phoneNumber;
  }

  public void setGivenName(Optional<String> givenName) {
    this.givenName = givenName;
  }

  public void setFamilyName(Optional<String> familyName) {
    this.familyName = familyName;
  }

  public void setEmail(Optional<String> email) {
    this.email = email;
  }

  public void setPhoneNumber(Optional<String> phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
