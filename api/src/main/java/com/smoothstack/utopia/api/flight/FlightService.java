package com.smoothstack.utopia.api.flight;

import com.smoothstack.utopia.api.CustomException;
import com.smoothstack.utopia.api.airplane.Airplane;
import com.smoothstack.utopia.api.airplane.AirplaneRepository;
import com.smoothstack.utopia.api.airport.Airport;
import com.smoothstack.utopia.api.airport.AirportForm;
import com.smoothstack.utopia.api.airport.AirportRepository;
import com.smoothstack.utopia.api.route.Route;
import com.smoothstack.utopia.api.route.RouteRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class FlightService {

  private final FlightRepository flightRepository;
  private final AirportRepository airportRepository;
  private final RouteRepository routeRepository;
  private final AirplaneRepository airplaneRepository;

  @Autowired
  public FlightService(
    FlightRepository flightRepository,
    AirportRepository airportRepository,
    RouteRepository routeRepository,
    AirplaneRepository airplaneRepository
  ) {
    this.flightRepository = flightRepository;
    this.airportRepository = airportRepository;
    this.routeRepository = routeRepository;
    this.airplaneRepository = airplaneRepository;
  }

  public List<Flight> getFlights() {
    return flightRepository.findAll();
  }

  public Flight getFlight(Long flightId) throws CustomException {
    Optional<Flight> flightOptional = flightRepository.findById(flightId);
    if (flightOptional.isEmpty()) {
      throw new CustomException("Flight " + flightId + " does not exist");
    }
    return flightOptional.get();
  }

  public void deleteFlight(Long flightId) throws CustomException {
    boolean exists = flightRepository.existsById(flightId);
    if (!exists) {
      throw new CustomException("Flight " + flightId + " does not exist");
    }
    flightRepository.deleteById(flightId);
  }

  public void addNewFlight(FlightForm flightForm) throws CustomException {
    if (
      flightForm.getOriginAirport().equals(flightForm.getDestinationAirport())
    ) {
      throw new CustomException(
        "Origin and destination airports cannot be the same"
      );
    }
    if (flightForm.getDepartureTime().isAfter(flightForm.getArrivalTime())) {
      throw new CustomException(
        "Arrival time cannot come before departure time"
      );
    }
    Optional<Airplane> airplaneOptional = airplaneRepository.findById(
      flightForm.getAirplane()
    );
    if (airplaneOptional.isEmpty()) {
      throw new CustomException(
        "Airplane " + flightForm.getAirplane() + " does not exist"
      );
    }
    Optional<Airport> originAirportOptional = airportRepository.findById(
      flightForm.getOriginAirport()
    );
    Optional<Airport> destinationAirportOptional = airportRepository.findById(
      flightForm.getDestinationAirport()
    );
    if (
      originAirportOptional.isEmpty() || destinationAirportOptional.isEmpty()
    ) {
      throw new CustomException("Origin or destination airport(s) are invalid");
    }
    Optional<Route> routeOptional = routeRepository.findRouteByOriginAndDestination(
      originAirportOptional.get(),
      destinationAirportOptional.get()
    );
    Route route;
    if (routeOptional.isPresent()) {
      System.out.println("ROUTE ALREADY EXISTS");
      route = routeOptional.get();
    } else {
      System.out.println("MAKING A NEW ROUTE");
      route = new Route();
      route.setOrigin(originAirportOptional.get());
      route.setDestination(destinationAirportOptional.get());
      routeRepository.save(route);
      System.out.println("Route saved");
    }
    Flight flight = new Flight(
      route,
      airplaneOptional.get(),
      flightForm.getDepartureTime(),
      flightForm.getArrivalTime(),
      flightForm.getReservedSeats(),
      flightForm.getSeatPrice()
    );
    flightRepository.save(flight);
  }
}
