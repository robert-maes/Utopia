package com.smoothstack.utopia.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.*;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
@Entity
@Table
public class Airport {

  @Id
  @GeneratedValue
  private Long id;

  @Column(columnDefinition = "CHAR(3)", unique = true, nullable = false)
  private String iataId;

  @Column(nullable = false)
  private String city;

  @JsonIgnoreProperties({ "originAirport", "destinationAirport" })
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "originAirport")
  private List<Flight> departingFlights;

  @JsonIgnoreProperties({ "destinationAirport", "originAirport" })
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "destinationAirport")
  private List<Flight> arrivingFlights;

  public Airport() {}

  public Airport(String iataId, String city) {
    this.iataId = iataId;
    this.city = city;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIataId() {
    return iataId;
  }

  public void setIataId(String iataId) {
    this.iataId = iataId;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public List<Flight> getDepartingFlights() {
    return departingFlights;
  }

  public void setDepartingFlights(List<Flight> departingFlights) {
    this.departingFlights = departingFlights;
  }

  public List<Flight> getArrivingFlights() {
    return arrivingFlights;
  }

  public void setArrivingFlights(List<Flight> arrivingFlights) {
    this.arrivingFlights = arrivingFlights;
  }

  @Override
  public String toString() {
    return (
      "Airport{" +
      "id=" +
      id +
      ", iataId='" +
      iataId +
      '\'' +
      ", city='" +
      city +
      '\'' +
      ", departingFlights=" +
      departingFlights +
      ", arrivingFlights=" +
      arrivingFlights +
      '}'
    );
  }
}
