package com.smoothstack.utopia.api.airport;

import com.fasterxml.jackson.annotation.*;
import com.smoothstack.utopia.api.route.Route;
import java.util.List;
import javax.persistence.*;

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "iataId"
)
@Entity
@Table
public class Airport {

  @Id
  @Column(length = 3, columnDefinition = "char")
  private String iataId;

  private String city;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "destination")
  private List<Route> arrivals;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "origin")
  private List<Route> departures;

  public Airport() {}

  public Airport(String iataId, String city) {
    this.iataId = iataId;
    this.city = city;
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

  public List<Route> getArrivals() {
    return arrivals;
  }

  public List<Route> getDepartures() {
    return departures;
  }

  @Override
  public String toString() {
    return (
      "Airport{" + "iataId='" + iataId + '\'' + ", city='" + city + '\'' + '}'
    );
  }
}
