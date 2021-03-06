package com.smoothstack.utopia.entity;

import java.time.LocalDateTime;

public class Flight {

  private int id;
  private Route route;
  private Airplane airplane;
  private LocalDateTime departureTime;
  private int reservedSeats;
  private float seatPrice;

  public Flight(
    int id,
    Route route,
    Airplane airplane,
    LocalDateTime departureTime,
    int reservedSeats,
    float seatPrice
  ) {
    this.id = id;
    this.route = route;
    this.airplane = airplane;
    this.departureTime = departureTime;
    this.reservedSeats = reservedSeats;
    this.seatPrice = seatPrice;
  }
}
