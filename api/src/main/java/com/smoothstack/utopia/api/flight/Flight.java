package com.smoothstack.utopia.api.flight;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.smoothstack.utopia.api.airplane.Airplane;
import com.smoothstack.utopia.api.booking.Booking;
import com.smoothstack.utopia.api.route.Route;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "id"
)
@Entity
@Table
public class Flight {

  @Id
  @Column(columnDefinition = "int")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "route_id", nullable = false)
  private Route route;

  @ManyToOne
  @JoinColumn(name = "airplane_id", nullable = false)
  private Airplane airplane;

  private LocalDateTime departureTime;
  private Integer reservedSeats;
  private Float seatPrice;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "flight_bookings",
    joinColumns = @JoinColumn(name = "flight_id"),
    inverseJoinColumns = @JoinColumn(name = "booking_id")
  )
  private List<Booking> bookings;

  public Flight() {}

  public Flight(
    Route route,
    Airplane airplane,
    LocalDateTime departureTime,
    Integer reservedSeats,
    Float seatPrice
  ) {
    this.route = route;
    this.airplane = airplane;
    this.departureTime = departureTime;
    this.reservedSeats = reservedSeats;
    this.seatPrice = seatPrice;
  }

  public Flight(
    Long id,
    Route route,
    Airplane airplane,
    LocalDateTime departureTime,
    Integer reservedSeats,
    Float seatPrice
  ) {
    this.id = id;
    this.route = route;
    this.airplane = airplane;
    this.departureTime = departureTime;
    this.reservedSeats = reservedSeats;
    this.seatPrice = seatPrice;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Route getRoute() {
    return route;
  }

  public void setRoute(Route route) {
    this.route = route;
  }

  public Airplane getAirplane() {
    return airplane;
  }

  public void setAirplane(Airplane airplane) {
    this.airplane = airplane;
  }

  public LocalDateTime getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(LocalDateTime departureTime) {
    this.departureTime = departureTime;
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

  public List<Booking> getBookings() {
    return bookings;
  }

  @Override
  public String toString() {
    return (
      "Flight{" +
      "id=" +
      id +
      ", route=" +
      route +
      ", airplane=" +
      airplane +
      ", departureTime=" +
      departureTime +
      ", reservedSeats=" +
      reservedSeats +
      ", seatPrice=" +
      seatPrice +
      '}'
    );
  }
}
