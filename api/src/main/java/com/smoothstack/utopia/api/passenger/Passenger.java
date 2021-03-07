package com.smoothstack.utopia.api.passenger;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.smoothstack.utopia.api.booking.Booking;
import java.time.LocalDate;
import javax.persistence.*;

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "id"
)
@Entity
@Table
public class Passenger {

  @Id
  @Column(columnDefinition = "int")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String givenName;
  private String familyName;
  private LocalDate dob;
  private String gender;
  private String address;

  @ManyToOne
  @JoinColumn(name = "booking_id", nullable = false)
  private Booking booking;

  public Passenger() {}

  public Passenger(
    String givenName,
    String familyName,
    LocalDate dob,
    String gender,
    String address,
    Booking booking
  ) {
    this.givenName = givenName;
    this.familyName = familyName;
    this.dob = dob;
    this.gender = gender;
    this.address = address;
    this.booking = booking;
  }

  public Passenger(
    Long id,
    String givenName,
    String familyName,
    LocalDate dob,
    String gender,
    String address,
    Booking booking
  ) {
    this.id = id;
    this.givenName = givenName;
    this.familyName = familyName;
    this.dob = dob;
    this.gender = gender;
    this.address = address;
    this.booking = booking;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public LocalDate getDob() {
    return dob;
  }

  public void setDob(LocalDate dob) {
    this.dob = dob;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Booking getBooking() {
    return booking;
  }

  public void setBooking(Booking booking) {
    this.booking = booking;
  }

  @Override
  public String toString() {
    return (
      "Passenger{" +
      "id=" +
      id +
      ", givenName='" +
      givenName +
      '\'' +
      ", familyName='" +
      familyName +
      '\'' +
      ", dob=" +
      dob +
      ", gender='" +
      gender +
      '\'' +
      ", address='" +
      address +
      '\'' +
      ", booking=" +
      booking +
      '}'
    );
  }
}
