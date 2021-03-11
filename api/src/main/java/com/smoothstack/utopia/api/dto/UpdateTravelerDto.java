package com.smoothstack.utopia.api.dto;

import java.util.Optional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateTravelerDto {

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

  private Optional<@Pattern(
    regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$",
    message = "Birth date must match pattern YYYY-MM-DD"
  ) String> dateOfBirth = Optional.empty();

  private Optional<@Size(
    min = 1,
    max = 255,
    message = "Gender must be between 1 - 255 characters"
  ) String> gender = Optional.empty();

  private Optional<@Size(
    min = 1,
    max = 255,
    message = "Address must be between 1 - 255 characters"
  ) String> address = Optional.empty();

  public Optional<String> getGivenName() {
    return givenName;
  }

  public Optional<String> getFamilyName() {
    return familyName;
  }

  public Optional<String> getDateOfBirth() {
    return dateOfBirth;
  }

  public Optional<String> getGender() {
    return gender;
  }

  public Optional<String> getAddress() {
    return address;
  }

  public void setGivenName(Optional<String> givenName) {
    this.givenName = givenName;
  }

  public void setFamilyName(Optional<String> familyName) {
    this.familyName = familyName;
  }

  public void setDateOfBirth(Optional<String> dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public void setGender(Optional<String> gender) {
    this.gender = gender;
  }

  public void setAddress(Optional<String> address) {
    this.address = address;
  }
}
