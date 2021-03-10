package com.smoothstack.utopia.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table
public class Ticket {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private UUID confirmationCode;

  @Column(nullable = false)
  private Boolean isActive;

  @ManyToOne(optional = false)
  private Traveler traveler;

  @OneToOne(optional = false)
  private Seat seat;

  public Ticket() {}

  public Ticket(
    UUID confirmationCode,
    Boolean isActive,
    Traveler traveler,
    Seat seat
  ) {
    this.confirmationCode = confirmationCode;
    this.isActive = isActive;
    this.traveler = traveler;
    this.seat = seat;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UUID getConfirmationCode() {
    return confirmationCode;
  }

  public void setConfirmationCode(UUID confirmationCode) {
    this.confirmationCode = confirmationCode;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public Traveler getTraveler() {
    return traveler;
  }

  public void setTraveler(Traveler traveler) {
    this.traveler = traveler;
  }

  public Seat getSeat() {
    return seat;
  }

  public void setSeat(Seat seat) {
    this.seat = seat;
  }

  @Override
  public String toString() {
    return (
      "Ticket{" +
      "id=" +
      id +
      ", confirmationCode=" +
      confirmationCode +
      ", isActive=" +
      isActive +
      ", traveler=" +
      traveler +
      ", seat=" +
      seat +
      '}'
    );
  }
}
