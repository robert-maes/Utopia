package com.smoothstack.utopia.api.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.smoothstack.utopia.api.booking_agent.BookingAgent;
import com.smoothstack.utopia.api.booking_user.BookingUser;
import com.smoothstack.utopia.api.user_role.UserRole;
import java.util.List;
import javax.persistence.*;

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "id"
)
@Entity
@Table
public class User {

  @Id
  @Column(columnDefinition = "int")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private UserRole role;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "agent")
  private List<BookingAgent> bookingAgents;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  private List<BookingUser> bookingUsers;

  private String givenName;
  private String familyName;
  private String username;
  private String email;
  private String password;
  private String phone;

  public User() {}

  public User(
    UserRole role,
    String givenName,
    String familyName,
    String username,
    String email,
    String password,
    String phone
  ) {
    this.role = role;
    this.givenName = givenName;
    this.familyName = familyName;
    this.username = username;
    this.email = email;
    this.password = password;
    this.phone = phone;
  }

  public User(
    Long id,
    UserRole role,
    String givenName,
    String familyName,
    String username,
    String email,
    String password,
    String phone
  ) {
    this.id = id;
    this.role = role;
    this.givenName = givenName;
    this.familyName = familyName;
    this.username = username;
    this.email = email;
    this.password = password;
    this.phone = phone;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public List<BookingAgent> getBookingAgents() {
    return bookingAgents;
  }

  public List<BookingUser> getBookingUsers() {
    return bookingUsers;
  }

  @Override
  public String toString() {
    return (
      "User{" +
      "id=" +
      id +
      ", role=" +
      role +
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
      ", password='" +
      password +
      '\'' +
      ", phone='" +
      phone +
      '\'' +
      '}'
    );
  }
}
