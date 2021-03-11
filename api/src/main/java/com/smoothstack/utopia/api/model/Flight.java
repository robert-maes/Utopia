package com.smoothstack.utopia.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.Instant;
import java.util.List;
import javax.persistence.*;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
@Entity
@Table
public class Flight {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne(optional = false)
  private Airport originAirport;

  @ManyToOne(optional = false)
  private Airport destinationAirport;

  @Column(nullable = false)
  private Instant departureTime;

  @Column(nullable = false)
  private Instant arrivalTime;

  @Transient
  private Float flightLengthInHours;

  @Column(nullable = false)
  private Float seatPrice;

  @Transient
  private Integer reservedSeats;

  @Column(nullable = false)
  private Integer totalSeats;

  @JsonIgnoreProperties({ "flight" })
  @OneToMany(mappedBy = "flight")
  private List<Seat> seats;

  public Flight() {}

  public Flight(
    Airport originAirport,
    Airport destinationAirport,
    Instant departureTime,
    Instant arrivalTime,
    Float seatPrice,
    Integer totalSeats
  ) {
    this.originAirport = originAirport;
    this.destinationAirport = destinationAirport;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.seatPrice = seatPrice;
    this.totalSeats = totalSeats;
  }

  public Flight(
    Airport originAirport,
    Airport destinationAirport,
    Instant departureTime,
    Instant arrivalTime,
    Float flightLengthInHours,
    Float seatPrice,
    Integer reservedSeats,
    Integer totalSeats,
    List<Seat> seats
  ) {
    this.originAirport = originAirport;
    this.destinationAirport = destinationAirport;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.flightLengthInHours = flightLengthInHours;
    this.seatPrice = seatPrice;
    this.reservedSeats = reservedSeats;
    this.totalSeats = totalSeats;
    this.seats = seats;
  }

  public Integer getReservedSeats() {
    return Math.toIntExact(
      seats
        .stream()
        .filter(
          seat -> {
            if (seat.getTicket() != null) {
              return seat.getTicket().getActive();
            }
            return false;
          }
        )
        .count()
    );
  }

  public Float getFlightLengthInHours() {
    long flightLengthInMillis =
      arrivalTime.toEpochMilli() - departureTime.toEpochMilli();
    return (Math.toIntExact(flightLengthInMillis) / (1000f * 60 * 60)) % 24;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Airport getOriginAirport() {
    return originAirport;
  }

  public void setOriginAirport(Airport originAirport) {
    this.originAirport = originAirport;
  }

  public Airport getDestinationAirport() {
    return destinationAirport;
  }

  public void setDestinationAirport(Airport destinationAirport) {
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

  public Float getSeatPrice() {
    return seatPrice;
  }

  public void setSeatPrice(Float seatPrice) {
    this.seatPrice = seatPrice;
  }

  public Integer getTotalSeats() {
    return totalSeats;
  }

  public void setTotalSeats(Integer totalSeats) {
    this.totalSeats = totalSeats;
  }

  public List<Seat> getSeats() {
    return seats;
  }

  public void setSeats(List<Seat> seats) {
    this.seats = seats;
  }

  @Override
  public String toString() {
    return (
      "Flight{" +
      "id=" +
      id +
      ", originAirport=" +
      originAirport +
      ", destinationAirport=" +
      destinationAirport +
      ", departureTime=" +
      departureTime +
      ", arrivalTime=" +
      arrivalTime +
      ", flightLengthInHours=" +
      flightLengthInHours +
      ", seatPrice=" +
      seatPrice +
      ", reservedSeats=" +
      reservedSeats +
      ", totalSeats=" +
      totalSeats +
      ", seats=" +
      seats +
      '}'
    );
  }
}
