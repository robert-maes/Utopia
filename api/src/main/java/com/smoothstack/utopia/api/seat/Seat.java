package com.smoothstack.utopia.api.seat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.smoothstack.utopia.api.flight.Flight;
import com.smoothstack.utopia.api.seat_type.SeatType;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "id"
)
@Entity
@Table
public class Seat {

  @Id
  @Column(columnDefinition = "int")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "type_id", nullable = false)
  private SeatType type;

  @JsonIgnore
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "seats")
  private Set<Flight> flights;

  public Seat() {}

  public Seat(SeatType type) {
    this.type = type;
  }

  public Seat(Long id, SeatType type) {
    this.id = id;
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SeatType getType() {
    return type;
  }

  public void setType(SeatType type) {
    this.type = type;
  }

  public Set<Flight> getFlights() {
    return flights;
  }

  @Override
  public String toString() {
    return "Seat{" + "id=" + id + ", type=" + type + '}';
  }
}
