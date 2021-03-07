package com.smoothstack.utopia.api.flight;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.smoothstack.utopia.api.airplane.Airplane;
import com.smoothstack.utopia.api.booking.Booking;
import com.smoothstack.utopia.api.route.Route;
import com.smoothstack.utopia.api.seat.Seat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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

  private Instant departureTime;
  private Instant arrivalTime;
  private Integer reservedSeats;
  private Float seatPrice;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "flight_bookings",
    joinColumns = @JoinColumn(name = "flight_id"),
    inverseJoinColumns = @JoinColumn(name = "booking_id")
  )
  private List<Booking> bookings;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "flight_seats",
    joinColumns = @JoinColumn(name = "flight_id"),
    inverseJoinColumns = @JoinColumn(name = "seat_id")
  )
  private Set<Seat> seats;

  public Flight() {}

  public Flight(
    Route route,
    Airplane airplane,
    Instant departureTime,
    Instant arrivalTime,
    Integer reservedSeats,
    Float seatPrice
  ) {
    this.route = route;
    this.airplane = airplane;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.reservedSeats = reservedSeats;
    this.seatPrice = seatPrice;
  }

  public Flight(
    Long id,
    Route route,
    Airplane airplane,
    Instant departureTime,
    Instant arrivalTime,
    Integer reservedSeats,
    Float seatPrice
  ) {
    this.id = id;
    this.route = route;
    this.airplane = airplane;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
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

  public Instant getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(Instant departureTime) {
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

  public Set<Seat> getSeats() {
    return seats;
  }

  public Instant getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(Instant arrivalTime) {
    this.arrivalTime = arrivalTime;
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
