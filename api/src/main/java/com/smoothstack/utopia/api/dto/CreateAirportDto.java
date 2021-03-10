package com.smoothstack.utopia.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateAirportDto {

  @NotBlank(message = "Airport ID is required")
  @Size(
    min = 3,
    max = 3,
    message = "Airport ID must be exactly 3 characters in length"
  )
  @Pattern(
    regexp = "[A-Z]{3}",
    message = "Airport ID can only contain capital letters"
  )
  private String id;

  @NotBlank(message = "Airport city is required")
  @Size(max = 255, message = "Airport city must not exceed 255 characters")
  private String city;

  public String getId() {
    return id;
  }

  public String getCity() {
    return city;
  }
}
