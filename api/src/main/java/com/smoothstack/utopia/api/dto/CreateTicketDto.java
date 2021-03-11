package com.smoothstack.utopia.api.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
public class CreateTicketDto {

  @NotNull(message = "Traveler ID is required")
  @Positive(message = "Traveler ID must be a positive number")
  private Long travelerId;

  @NotNull(message = "Seat ID is required")
  @Positive(message = "Seat ID must be a positive number")
  private Long seatId;

  public Long getTravelerId() {
    return travelerId;
  }

  public Long getSeatId() {
    return seatId;
  }

  public void setTravelerId(Long travelerId) {
    this.travelerId = travelerId;
  }

  public void setSeatId(Long seatId) {
    this.seatId = seatId;
  }
}
