package com.smoothstack.utopia.api.airplane_type;

import com.fasterxml.jackson.annotation.*;
import com.smoothstack.utopia.api.airplane.Airplane;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "id"
)
@Entity
@Table
public class AirplaneType {

  @Id
  @Column(columnDefinition = "int")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer maxCapacity;

  @JsonIgnore
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
  private List<Airplane> airplanes;

  public AirplaneType() {}

  public AirplaneType(Integer maxCapacity) {
    this.maxCapacity = maxCapacity;
  }

  public AirplaneType(Long id, Integer maxCapacity) {
    this.id = id;
    this.maxCapacity = maxCapacity;
  }

  public List<Airplane> getAirplanes() {
    return airplanes;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getMaxCapacity() {
    return maxCapacity;
  }

  public void setMaxCapacity(Integer maxCapacity) {
    this.maxCapacity = maxCapacity;
  }

  @Override
  public String toString() {
    return (
      "AirplaneType{" +
      "id=" +
      id +
      ", maxCapacity=" +
      maxCapacity +
      ", airplanes=" +
      airplanes +
      '}'
    );
  }
}
