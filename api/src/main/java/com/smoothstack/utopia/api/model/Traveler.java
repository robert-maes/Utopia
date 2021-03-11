package com.smoothstack.utopia.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
@Entity
@Table
public class Traveler {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String givenName;

  @Column(nullable = false)
  private String familyName;

  @Column(nullable = false)
  private LocalDate dateOfBirth;

  @Column(nullable = false)
  private String gender;

  @Column(nullable = false)
  private String address;

  @JsonIgnoreProperties({ "traveler" })
  @OneToMany(mappedBy = "traveler")
  private List<Ticket> tickets;

  public Traveler() {}

  public Traveler(
    String givenName,
    String familyName,
    LocalDate dateOfBirth,
    String gender,
    String address
  ) {
    this.givenName = givenName;
    this.familyName = familyName;
    this.dateOfBirth = dateOfBirth;
    this.gender = gender;
    this.address = address;
  }

  public Traveler(
    String givenName,
    String familyName,
    LocalDate dateOfBirth,
    String gender,
    String address,
    List<Ticket> tickets
  ) {
    this.givenName = givenName;
    this.familyName = familyName;
    this.dateOfBirth = dateOfBirth;
    this.gender = gender;
    this.address = address;
    this.tickets = tickets;
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

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
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

  public List<Ticket> getTickets() {
    return tickets;
  }

  public void setTickets(List<Ticket> tickets) {
    this.tickets = tickets;
  }

  @Override
  public String toString() {
    return (
      "Traveler{" +
      "id=" +
      id +
      ", givenName='" +
      givenName +
      '\'' +
      ", familyName='" +
      familyName +
      '\'' +
      ", dateOfBirth=" +
      dateOfBirth +
      ", gender='" +
      gender +
      '\'' +
      ", address='" +
      address +
      '\'' +
      ", tickets=" +
      tickets +
      '}'
    );
  }
}
