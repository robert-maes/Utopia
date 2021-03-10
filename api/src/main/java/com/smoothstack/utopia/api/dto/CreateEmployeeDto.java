package com.smoothstack.utopia.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateEmployeeDto {

  @NotBlank(message = "First name is required")
  @Size(max = 255, message = "First name must not exceed 255 characters")
  private String givenName;

  @NotBlank(message = "Last name is required")
  @Size(max = 255, message = "Last name must not exceed 255 characters")
  private String familyName;

  @NotBlank(message = "Username is required")
  @Size(min = 8, max = 255, message = "Username be betweem 8 - 255 characters")
  @Pattern(
    regexp = "^[a-zA-Z0-9_]*$",
    message = "Username may only contain letters, numbers and underscores"
  )
  private String username;

  @NotBlank(message = "Email is required")
  @Size(max = 255, message = "Email must not exceed 255 characters")
  @Email(message = "Email must be valid")
  private String email;

  @NotBlank(message = "Phone number is required")
  @Size(max = 255, message = "Phone number must not exceed 255 characters")
  @Pattern(
    regexp = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$",
    message = "Phone number must be valid"
  )
  private String phoneNumber;

  public String getGivenName() {
    return givenName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
}
