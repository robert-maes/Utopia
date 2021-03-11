package com.smoothstack.utopia.api.model;

import javax.persistence.*;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
@Entity
@Table
public class Employee {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String givenName;

  @Column(nullable = false)
  private String familyName;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String phoneNumber;

  public Employee() {}

  public Employee(
    String givenName,
    String familyName,
    String username,
    String email,
    String phoneNumber
  ) {
    this.givenName = givenName;
    this.familyName = familyName;
    this.username = username;
    this.email = email;
    this.phoneNumber = phoneNumber;
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String toString() {
    return (
      "Employee{" +
      "id=" +
      id +
      ", givenName='" +
      givenName +
      '\'' +
      ", familyName='" +
      familyName +
      '\'' +
      ", username='" +
      username +
      '\'' +
      ", email='" +
      email +
      '\'' +
      ", phoneNumber='" +
      phoneNumber +
      '\'' +
      '}'
    );
  }
}
