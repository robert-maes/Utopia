package com.smoothstack.utopia.api.dto;

import com.smoothstack.utopia.api.SeatClass;
import java.util.Optional;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class UpdateSeatDto {

  private Optional<@Positive(
    message = "Flight ID must be a positive number"
  ) Long> flightId = Optional.empty();
  private Optional<SeatClass> seatClass = Optional.empty();

  public Optional<Long> getFlightId() {
    return flightId;
  }

  public Optional<SeatClass> getSeatClass() {
    return seatClass;
  }
}
