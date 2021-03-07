package com.smoothstack.utopia.api.booking_agent;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.smoothstack.utopia.api.booking.Booking;
import com.smoothstack.utopia.api.user.User;
import javax.persistence.*;

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "booking_id"
)
@Entity
@Table
public class BookingAgent {

  @Id
  @Column(columnDefinition = "int")
  private Long bookingId;

  @ManyToOne
  @JoinColumn(name = "agent_id", nullable = false)
  private User agent;

  @OneToOne
  @JoinColumn(name = "booking_id", referencedColumnName = "id")
  private Booking booking;

  public BookingAgent() {}

  public BookingAgent(Long bookingId, User agent) {
    this.bookingId = bookingId;
    this.agent = agent;
  }

  public Long getBookingId() {
    return bookingId;
  }

  public void setBookingId(Long bookingId) {
    this.bookingId = bookingId;
  }

  public User getAgent() {
    return agent;
  }

  public void setAgent(User agent) {
    this.agent = agent;
  }

  public Booking getBooking() {
    return booking;
  }

  @Override
  public String toString() {
    return (
      "BookingAgent{" + "bookingId=" + bookingId + ", agent=" + agent + '}'
    );
  }
}
