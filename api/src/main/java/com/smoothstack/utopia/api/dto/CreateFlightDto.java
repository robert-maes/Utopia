package com.smoothstack.utopia.api.dto;

import java.time.Instant;
import javax.validation.constraints.*;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
public class CreateFlightDto {

  @NotBlank(message = "Origin airport ID is required")
  @Size(
    min = 3,
    max = 3,
    message = "Origin airport ID must be exactly 3 characters in length"
  )
  @Pattern(
    regexp = "[A-Z]{3}",
    message = "Origin airport ID can only contain capital letters"
  )
  private String originAirportId;

  @NotBlank(message = "Destination airport ID is required")
  @Size(
    min = 3,
    max = 3,
    message = "Destination airport ID must be exactly 3 characters in length"
  )
  @Pattern(
    regexp = "[A-Z]{3}",
    message = "Destination airport ID can only contain capital letters"
  )
  private String destinationAirportId;

  @NotNull(message = "Departure time is required")
  //  @PastOrPresent(message = "Departure time must be a time")
  private Instant departureTime;

  @NotNull(message = "Arrival time is required")
  //  @PastOrPresent(message = "Arrival time must be a time")
  private Instant arrivalTime;

  @NotNull(message = "Seat price is required")
  @PositiveOrZero(message = "Seat price must be 0 or greater")
  private Float seatPrice;

  @NotNull(message = "Total seats is required")
  @PositiveOrZero(message = "Total seats must be 0 or greater")
  private Integer totalSeats;

  public String getOriginAirportId() {
    return originAirportId;
  }

  public String getDestinationAirportId() {
    return destinationAirportId;
  }

  public Instant getDepartureTime() {
    return departureTime;
  }

  public Instant getArrivalTime() {
    return arrivalTime;
  }

  public Float getSeatPrice() {
    return seatPrice;
  }

  public Integer getTotalSeats() {
    return totalSeats;
  }

  public void setOriginAirportId(String originAirportId) {
    this.originAirportId = originAirportId;
  }

  public void setDestinationAirportId(String destinationAirportId) {
    this.destinationAirportId = destinationAirportId;
  }

  public void setDepartureTime(Instant departureTime) {
    this.departureTime = departureTime;
  }

  public void setArrivalTime(Instant arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public void setSeatPrice(Float seatPrice) {
    this.seatPrice = seatPrice;
  }

  public void setTotalSeats(Integer totalSeats) {
    this.totalSeats = totalSeats;
  }
}
