package com.smoothstack.utopia.api.flight;

import com.smoothstack.utopia.api.seat_type.SeatType;
import com.smoothstack.utopia.api.seat_type.SeatTypeRepository;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService {

  private final SeatTypeRepository seatTypeRepository;
  private final FlightRepository flightRepository;

  @Autowired
  public FlightService(
    FlightRepository flightRepository,
    SeatTypeRepository seatTypeRepository
  ) {
    this.flightRepository = flightRepository;
    this.seatTypeRepository = seatTypeRepository;
  }

  public List<Flight> getFlights() {
    List<Flight> flights = flightRepository.findAll();
    flights.forEach(
      flight -> System.out.println(flight.getRoute().getDestination())
    );
    return flights;
  }

  public Flight getFlight(Long flightId) {
    Optional<Flight> flightOptional = flightRepository.findById(flightId);
    if (flightOptional.isEmpty()) {
      throw new IllegalStateException(
        "flight with id " + flightId + " does not exist"
      );
    }
    return flightOptional.get();
  }

  public void updateFlight(
    Long flightId,
    String originAirport,
    String originCity,
    String destinationAirport,
    String destinationCity,
    String departureDate,
    String departureTime
  ) {
    System.out.println("test");
  }

  public void addSeats(Long flightId, AddSeatsInput addSeatsInput) {
    Optional<Flight> flightOptional = flightRepository.findById(flightId);
    if (flightOptional.isEmpty()) {
      throw new IllegalStateException("invalid flight");
    }
    Optional<SeatType> seatTypeOptional = seatTypeRepository.findSeatTypeByClassification(
      addSeatsInput.getSeatType()
    );
    if (seatTypeOptional.isEmpty()) {
      throw new IllegalStateException("invalid seat type");
    }
    Flight flight = flightOptional.get();
    SeatType seatType = seatTypeOptional.get();
  }
}
