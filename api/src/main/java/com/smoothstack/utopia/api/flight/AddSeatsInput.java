package com.smoothstack.utopia.api.flight;

public class AddSeatsInput {

  private String seatType;
  private Integer numSeats;

  public AddSeatsInput() {}

  public AddSeatsInput(String seatType, Integer numSeats) {
    this.seatType = seatType;
    this.numSeats = numSeats;
  }

  public String getSeatType() {
    return seatType;
  }

  public void setSeatType(String seatType) {
    this.seatType = seatType;
  }

  public Integer getNumSeats() {
    return numSeats;
  }

  public void setNumSeats(Integer numSeats) {
    this.numSeats = numSeats;
  }

  @Override
  public String toString() {
    return (
      "AddSeatsInput{" +
      "seatType='" +
      seatType +
      '\'' +
      ", numSeats=" +
      numSeats +
      '}'
    );
  }
}
