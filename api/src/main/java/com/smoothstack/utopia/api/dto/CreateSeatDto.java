package com.smoothstack.utopia.api.dto;

import com.smoothstack.utopia.api.SeatClass;
import javax.validation.constraints.*;

public class CreateSeatDto {

  @NotNull(message = "Flight ID is required")
  @Positive(message = "Flight ID must be a positive number")
  private Long flightId;

  @NotNull(message = "Seat class is required")
  private SeatClass seatClass;

  public Long getFlightId() {
    return flightId;
  }

  public SeatClass getSeatClass() {
    return seatClass;
  }
}