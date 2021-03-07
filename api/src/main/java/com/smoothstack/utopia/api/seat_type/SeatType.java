package com.smoothstack.utopia.api.seat_type;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.smoothstack.utopia.api.seat.Seat;
import java.util.List;
import javax.persistence.*;

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "id"
)
@Entity
@Table
public class SeatType {

  @Id
  @Column(columnDefinition = "int")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String classification;

  @JsonIgnore
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
  private List<Seat> seats;

  public SeatType() {}

  public SeatType(String classification) {
    this.classification = classification;
  }

  public SeatType(Long id, String classification) {
    this.id = id;
    this.classification = classification;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getClassification() {
    return classification;
  }

  public void setClassification(String classification) {
    this.classification = classification;
  }

  public List<Seat> getSeats() {
    return seats;
  }

  @Override
  public String toString() {
    return (
      "SeatType{" +
      "id=" +
      id +
      ", classification='" +
      classification +
      '\'' +
      ", seats=" +
      seats +
      '}'
    );
  }
}
