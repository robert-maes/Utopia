package com.smoothstack.utopia.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.smoothstack.utopia.api.SeatClass;
import javax.persistence.*;

@Entity
@Table
public class Seat {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne(optional = false)
  private Flight flight;

  @OneToOne(optional = true)
  private Ticket ticket;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false)
  private SeatClass seatClass;

  public Seat() {}

  public Seat(Flight flight, Ticket ticket, SeatClass seatClass) {
    this.flight = flight;
    this.ticket = ticket;
    this.seatClass = seatClass;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Flight getFlight() {
    return flight;
  }

  public void setFlight(Flight flight) {
    this.flight = flight;
  }

  public Ticket getTicket() {
    return ticket;
  }

  public void setTicket(Ticket ticket) {
    this.ticket = ticket;
  }

  public SeatClass getSeatClass() {
    return seatClass;
  }

  public void setSeatClass(SeatClass seatClass) {
    this.seatClass = seatClass;
  }

  @Override
  public String toString() {
    return (
      "Seat{" +
      "id=" +
      id +
      ", flight=" +
      flight +
      ", ticket=" +
      ticket +
      ", seatClass=" +
      seatClass +
      '}'
    );
  }
}