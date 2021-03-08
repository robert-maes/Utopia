package com.smoothstack.utopia.api.airport;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Rob Maes
 * Mon Mar 8
 */
public class AirportForm {

  @NotNull(message = "IATA ID is required")
  @Size(
    min = 3,
    max = 3,
    message = "IATA ID must be exactly 3 characters in length"
  )
  private String iataId;

  @NotNull(message = "City is required")
  @Size(
    min = 1,
    max = 45,
    message = "City must be between 1 - 45 characters in length"
  )
  private String city;

  public String getIataId() {
    return iataId;
  }

  public void setIataId(String iataId) {
    this.iataId = iataId;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public String toString() {
    return (
      "AirportForm{" +
      "iataId='" +
      iataId +
      '\'' +
      ", city='" +
      city +
      '\'' +
      '}'
    );
  }
}
