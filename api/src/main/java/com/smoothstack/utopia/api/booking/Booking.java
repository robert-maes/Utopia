package com.smoothstack.utopia.api.booking;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.smoothstack.utopia.api.booking_agent.BookingAgent;
import com.smoothstack.utopia.api.booking_guest.BookingGuest;
import com.smoothstack.utopia.api.booking_payment.BookingPayment;
import com.smoothstack.utopia.api.booking_user.BookingUser;
import com.smoothstack.utopia.api.flight.Flight;
import com.smoothstack.utopia.api.passenger.Passenger;
import java.util.List;
import javax.persistence.*;

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "id"
)
@Entity
@Table
public class Booking {

  @Id
  @Column(columnDefinition = "int")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "tinyint")
  private Boolean isActive;

  private String confirmationCode;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "bookings")
  private List<Flight> flights;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "booking")
  private List<Passenger> passengers;

  @OneToOne(mappedBy = "booking")
  private BookingPayment bookingPayment;

  @OneToOne(mappedBy = "booking")
  private BookingGuest bookingGuest;

  @OneToOne(mappedBy = "booking")
  private BookingAgent bookingAgent;

  @OneToOne(mappedBy = "booking")
  private BookingUser bookingUser;

  public Booking() {}

  public Booking(Boolean isActive, String confirmationCode) {
    this.isActive = isActive;
    this.confirmationCode = confirmationCode;
  }

  public Booking(Long id, Boolean isActive, String confirmationCode) {
    this.id = id;
    this.isActive = isActive;
    this.confirmationCode = confirmationCode;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public String getConfirmationCode() {
    return confirmationCode;
  }

  public void setConfirmationCode(String confirmationCode) {
    this.confirmationCode = confirmationCode;
  }

  public List<Flight> getFlights() {
    return flights;
  }

  public List<Passenger> getPassengers() {
    return passengers;
  }

  public BookingPayment getBookingPayment() {
    return bookingPayment;
  }

  public BookingGuest getBookingGuest() {
    return bookingGuest;
  }

  public BookingAgent getBookingAgent() {
    return bookingAgent;
  }

  public BookingUser getBookingUser() {
    return bookingUser;
  }

  @Override
  public String toString() {
    return (
      "Booking{" +
      "id=" +
      id +
      ", isActive=" +
      isActive +
      ", confirmationCode='" +
      confirmationCode +
      '\'' +
      ", flights=" +
      flights +
      '}'
    );
  }
}
