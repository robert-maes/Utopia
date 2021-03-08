package com.smoothstack.utopia.api.flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Rob Maes
 * Mon Mar 8 2021
 */
public class FlightForm {

  @NotNull(message = "Origin airport is required")
  @Size(
    min = 3,
    max = 3,
    message = "Origin airport must be exactly 3 characters in length"
  )
  private String originAirport;

  @NotNull(message = "Destination airport is required")
  @Size(
    min = 3,
    max = 3,
    message = "Destination airport must be exactly 3 characters in length"
  )
  private String destinationAirport;

  @NotNull(message = "Airplane is required")
  @Min(value = 0, message = "Airplane ID cannot be negative")
  private Long airplane;

  @JsonFormat(shape = JsonFormat.Shape.NUMBER)
  @NotNull(message = "Departure time is required")
  private Instant departureTime;

  @JsonFormat(shape = JsonFormat.Shape.NUMBER)
  @NotNull(message = "Arrival time is required")
  private Instant arrivalTime;

  @NotNull(message = "Reserved seat number is required")
  @Min(value = 0, message = "Reserved seats cannot be a negative number")
  private Integer reservedSeats;

  @NotNull(message = "Seat price is required")
  @Min(value = 0, message = "Seat price cannot be a negative number")
  private Float seatPrice;

  public String getOriginAirport() {
    return originAirport;
  }

  public void setOriginAirport(String originAirport) {
    this.originAirport = originAirport;
  }

  public String getDestinationAirport() {
    return destinationAirport;
  }

  public void setDestinationAirport(String destinationAirport) {
    this.destinationAirport = destinationAirport;
  }

  public Instant getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(Instant departureTime) {
    this.departureTime = departureTime;
  }

  public Instant getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(Instant arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public Integer getReservedSeats() {
    return reservedSeats;
  }

  public void setReservedSeats(Integer reservedSeats) {
    this.reservedSeats = reservedSeats;
  }

  public Float getSeatPrice() {
    return seatPrice;
  }

  public void setSeatPrice(Float seatPrice) {
    this.seatPrice = seatPrice;
  }

  public Long getAirplane() {
    return airplane;
  }

  public void setAirplane(Long airplane) {
    this.airplane = airplane;
  }

  @Override
  public String toString() {
    return (
      "FlightForm{" +
      "originAirport='" +
      originAirport +
      '\'' +
      ", destinationAirport='" +
      destinationAirport +
      '\'' +
      ", departureTime=" +
      departureTime +
      ", arrivalTime=" +
      arrivalTime +
      ", reservedSeats=" +
      reservedSeats +
      ", seatPrice=" +
      seatPrice +
      '}'
    );
  }
}
