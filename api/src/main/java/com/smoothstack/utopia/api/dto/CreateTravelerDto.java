package com.smoothstack.utopia.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
public class CreateTravelerDto {

  @NotBlank(message = "First name is required")
  @Size(max = 255, message = "First name must not exceed 255 characters")
  private String givenName;

  @NotBlank(message = "Last name is required")
  @Size(max = 255, message = "Last name must not exceed 255 characters")
  private String familyName;

  @NotBlank(message = "Birth date is required")
  @Pattern(
    regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$",
    message = "Birth date must match pattern YYYY-MM-DD"
  )
  private String dateOfBirth;

  @NotBlank(message = "Gender is required")
  @Size(max = 255, message = "Gender must not exceed 255 characters")
  private String gender;

  @NotBlank(message = "Address is required")
  @Size(max = 255, message = "Address must not exceed 255 characters")
  private String address;

  public String getGivenName() {
    return givenName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public String getGender() {
    return gender;
  }

  public String getAddress() {
    return address;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
