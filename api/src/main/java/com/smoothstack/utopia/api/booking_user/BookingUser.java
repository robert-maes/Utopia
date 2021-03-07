package com.smoothstack.utopia.api.booking_user;

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
public class BookingUser {

  @Id
  @Column(columnDefinition = "int")
  private Long bookingId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @OneToOne
  @JoinColumn(name = "booking_id", referencedColumnName = "id")
  private Booking booking;

  public BookingUser() {}

  public BookingUser(Long bookingId, User user) {
    this.bookingId = bookingId;
    this.user = user;
  }

  public Long getBookingId() {
    return bookingId;
  }

  public void setBookingId(Long bookingId) {
    this.bookingId = bookingId;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Booking getBooking() {
    return booking;
  }

  @Override
  public String toString() {
    return "BookingUser{" + "bookingId=" + bookingId + ", user=" + user + '}';
  }
}
