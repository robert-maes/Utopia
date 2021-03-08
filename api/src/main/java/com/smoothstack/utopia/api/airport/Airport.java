package com.smoothstack.utopia.api.airport;

import com.fasterxml.jackson.annotation.*;
import com.smoothstack.utopia.api.route.Route;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table
public class Airport {

  @Id
  @Column(length = 3, columnDefinition = "char")
  private String iataId;

  private String city;

  @JsonIgnoreProperties({ "origin", "destination" })
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "destination")
  private Set<Route> arrivals;

  @JsonIgnoreProperties({ "origin", "destination" })
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "origin")
  private Set<Route> departures;

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

  public Set<Route> getArrivals() {
    return arrivals;
  }

  public Set<Route> getDepartures() {
    return departures;
  }

  @Override
  public String toString() {
    return (
      "Airport{" + "iataId='" + iataId + '\'' + ", city='" + city + '\'' + '}'
    );
  }
}
