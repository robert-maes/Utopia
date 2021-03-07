package com.smoothstack.utopia.api.booking_guest;

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
public class BookingGuest {

  @Id
  @Column(columnDefinition = "int")
  private Long bookingId;

  private String contactEmail;
  private String contactPhone;

  @OneToOne
  @JoinColumn(name = "booking_id", referencedColumnName = "id")
  private Booking booking;

  public BookingGuest() {}

  public BookingGuest(
    Long bookingId,
    String contactEmail,
    String contactPhone
  ) {
    this.bookingId = bookingId;
    this.contactEmail = contactEmail;
    this.contactPhone = contactPhone;
  }

  public Long getBookingId() {
    return bookingId;
  }

  public void setBookingId(Long bookingId) {
    this.bookingId = bookingId;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  @Override
  public String toString() {
    return (
      "BookingGuest{" +
      "bookingId=" +
      bookingId +
      ", contactEmail='" +
      contactEmail +
      '\'' +
      ", contactPhone='" +
      contactPhone +
      '\'' +
      '}'
    );
  }
}
