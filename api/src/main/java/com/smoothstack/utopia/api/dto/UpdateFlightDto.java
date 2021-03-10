package com.smoothstack.utopia.api.dto;

import java.time.Instant;
import java.util.Optional;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

public class UpdateFlightDto {

  private Optional<@Size(
    min = 3,
    max = 3,
    message = "Origin airport ID must be exactly 3 characters in length"
  ) @Pattern(
    regexp = "[A-Z]{3}",
    message = "Origin airport ID can only contain capital letters"
  ) String> originAirportId = Optional.empty();
  private Optional<@Size(
    min = 3,
    max = 3,
    message = "Destination airport ID must be exactly 3 characters in length"
  ) @Pattern(
    regexp = "[A-Z]{3}",
    message = "Destination airport ID can only contain capital letters"
  ) String> destinationAirportId = Optional.empty();
  private Optional<@PastOrPresent(
    message = "Departure time must be a time"
  ) Instant> departureTime = Optional.empty();
  private Optional<@PastOrPresent(
    message = "Arrival time must be a time"
  ) Instant> arrivalTime = Optional.empty();
  private Optional<@PositiveOrZero(
    message = "Seat price must be 0 or greater"
  ) Float> seatPrice = Optional.empty();
  private Optional<@PositiveOrZero(
    message = "Total seats must be 0 or greater"
  ) Integer> totalSeats = Optional.empty();

  public Optional<String> getOriginAirportId() {
    return originAirportId;
  }

  public Optional<String> getDestinationAirportId() {
    return destinationAirportId;
  }

  public Optional<Instant> getDepartureTime() {
    return departureTime;
  }

  public Optional<Instant> getArrivalTime() {
    return arrivalTime;
  }

  public Optional<Float> getSeatPrice() {
    return seatPrice;
  }

  public Optional<Integer> getTotalSeats() {
    return totalSeats;
  }
}
