package com.smoothstack.utopia.api.flight;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService {

  private final FlightRepository flightRepository;

  @Autowired
  public FlightService(FlightRepository flightRepository) {
    this.flightRepository = flightRepository;
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
}
