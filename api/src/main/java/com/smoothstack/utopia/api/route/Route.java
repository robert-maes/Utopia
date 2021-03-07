package com.smoothstack.utopia.api.route;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.smoothstack.utopia.api.airport.Airport;
import com.smoothstack.utopia.api.flight.Flight;
import java.util.List;
import javax.persistence.*;

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "id"
)
@Entity
@Table
public class Route {

  @Id
  @Column(columnDefinition = "int")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "origin_id", nullable = false)
  private Airport origin;

  @ManyToOne
  @JoinColumn(name = "destination_id", nullable = false)
  private Airport destination;

  @JsonIgnore
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "route")
  private List<Flight> flights;

  public Route() {}

  public Route(Airport origin, Airport destination) {
    this.origin = origin;
    this.destination = destination;
  }

  public Route(Long id, Airport origin, Airport destination) {
    this.id = id;
    this.origin = origin;
    this.destination = destination;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Airport getOrigin() {
    return origin;
  }

  public void setOrigin(Airport origin) {
    this.origin = origin;
  }

  public Airport getDestination() {
    return destination;
  }

  public void setDestination(Airport destination) {
    this.destination = destination;
  }

  public List<Flight> getFlights() {
    return flights;
  }

  @Override
  public String toString() {
    return (
      "Route{" +
      "id=" +
      id +
      ", origin=" +
      origin +
      ", destination=" +
      destination +
      '}'
    );
  }
}
