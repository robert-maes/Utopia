package com.smoothstack.utopia.api.booking_payment;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.smoothstack.utopia.api.booking.Booking;
import javax.persistence.*;

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "booking_id"
)
@Entity
@Table
public class BookingPayment {

  @Id
  @Column(columnDefinition = "int")
  private Long bookingId;

  private String stripeId;

  @Column(columnDefinition = "tinyint")
  private Boolean refunded;

  @OneToOne
  @JoinColumn(name = "booking_id", referencedColumnName = "id")
  private Booking booking;

  public BookingPayment() {}

  public BookingPayment(Long bookingId, String stripeId, Boolean refunded) {
    this.bookingId = bookingId;
    this.stripeId = stripeId;
    this.refunded = refunded;
  }

  public Long getBookingId() {
    return bookingId;
  }

  public void setBookingId(Long bookingId) {
    this.bookingId = bookingId;
  }

  public String getStripeId() {
    return stripeId;
  }

  public void setStripeId(String stripeId) {
    this.stripeId = stripeId;
  }

  public Boolean getRefunded() {
    return refunded;
  }

  public void setRefunded(Boolean refunded) {
    this.refunded = refunded;
  }

  public Booking getBooking() {
    return booking;
  }

  @Override
  public String toString() {
    return (
      "BookingPayment{" +
      "bookingId=" +
      bookingId +
      ", stripeId='" +
      stripeId +
      '\'' +
      ", refunded=" +
      refunded +
      '}'
    );
  }
}
