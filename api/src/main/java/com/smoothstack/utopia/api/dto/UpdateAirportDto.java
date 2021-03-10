package com.smoothstack.utopia.api.dto;

import java.util.Optional;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateAirportDto {

  private Optional<@Size(
    min = 3,
    max = 3,
    message = "Airport ID must be exactly 3 characters in length"
  ) @Pattern(
    regexp = "[A-Z]{3}",
    message = "Airport ID can only contain capital letters"
  ) String> id = Optional.empty();

  private Optional<@Size(
    min = 1,
    max = 255,
    message = "Airport city must be between 1 - 255 characters"
  ) String> city = Optional.empty();

  public Optional<String> getId() {
    return id;
  }

  public Optional<String> getCity() {
    return city;
  }
}
