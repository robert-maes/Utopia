package com.smoothstack.utopia.api.airplane;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.smoothstack.utopia.api.airplane_type.AirplaneType;
import com.smoothstack.utopia.api.flight.Flight;
import java.util.List;
import javax.persistence.*;

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "id"
)
@Entity
@Table
public class Airplane {

  @Id
  @Column(columnDefinition = "int")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "type_id", nullable = false)
  private AirplaneType type;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "airplane")
  private List<Flight> flights;

  public Airplane() {}

  public Airplane(AirplaneType type) {
    this.type = type;
  }

  public Airplane(Long id, AirplaneType type) {
    this.id = id;
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public AirplaneType getType() {
    return type;
  }

  public void setType(AirplaneType type) {
    this.type = type;
  }

  public List<Flight> getFlights() {
    return flights;
  }

  @Override
  public String toString() {
    return "Airplane{" + "id=" + id + ", type=" + type + '}';
  }
}