package com.smoothstack.utopia.api.route;

import com.fasterxml.jackson.annotation.*;
import com.smoothstack.utopia.api.airport.Airport;
import com.smoothstack.utopia.api.flight.Flight;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table
public class Route {

  @Id
  @Column(columnDefinition = "int")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonManagedReference
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "origin_id", nullable = false)
  private Airport origin;

  @JsonManagedReference
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "destination_id", nullable = false)
  private Airport destination;

  @JsonBackReference
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "route")
  private Set<Flight> flights;

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

  public Set<Flight> getFlights() {
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
