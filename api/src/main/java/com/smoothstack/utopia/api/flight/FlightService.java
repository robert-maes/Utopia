package com.smoothstack.utopia.api.flight;

import com.smoothstack.utopia.api.CustomException;
import com.smoothstack.utopia.api.airplane.Airplane;
import com.smoothstack.utopia.api.airplane.AirplaneRepository;
import com.smoothstack.utopia.api.airport.Airport;
import com.smoothstack.utopia.api.airport.AirportRepository;
import com.smoothstack.utopia.api.route.Route;
import com.smoothstack.utopia.api.route.RouteRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Rob Maes
 * Mon Mar 8 2021
 */
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

  /**
   * Gets a list of all flights
   * @return A list of all flights
   */
  public List<Flight> getFlights() {
    return flightRepository.findAll();
  }

  /**
   * Gets a specific flight
   * @param flightId The Id of the flight to get
   * @return The specified flight
   * @throws CustomException
   */
  public Flight getFlight(Long flightId) throws CustomException {
    // check that the flight exists
    // if it doesn't, return an error
    Optional<Flight> flightOptional = flightRepository.findById(flightId);
    if (flightOptional.isEmpty()) {
      throw new CustomException("Flight " + flightId + " does not exist");
    }
    // else return the flight
    return flightOptional.get();
  }

  /**
   * Deletes a specific flight
   * @param flightId The Id of the flight to delete
   * @throws CustomException
   */
  public void deleteFlight(Long flightId) throws CustomException {
    // check that the flight exists
    // if it doesn't, return an error
    boolean exists = flightRepository.existsById(flightId);
    if (!exists) {
      throw new CustomException("Flight " + flightId + " does not exist");
    }
    // else delete the flight
    flightRepository.deleteById(flightId);
  }

  /**
   * Creates a new flight
   * @param flightForm A POJO DTO for creating a new flight
   * @throws CustomException
   */
  public void addNewFlight(FlightForm flightForm) throws CustomException {
    // if the origin and destination airports are the same, throw an error
    if (
      flightForm.getOriginAirport().equals(flightForm.getDestinationAirport())
    ) {
      throw new CustomException(
        "Origin and destination airports cannot be the same"
      );
    }
    // if the arrival time comes before the departure time chronologically, throw an error
    if (flightForm.getDepartureTime().isAfter(flightForm.getArrivalTime())) {
      throw new CustomException(
        "Arrival time cannot come before departure time"
      );
    }
    // if an airplane with the given ID does not exist, throw an error
    Optional<Airplane> airplaneOptional = airplaneRepository.findById(
      flightForm.getAirplane()
    );
    if (airplaneOptional.isEmpty()) {
      throw new CustomException(
        "Airplane " + flightForm.getAirplane() + " does not exist"
      );
    }
    // if one or both of the given airports do not exist, throw an error
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
    // see if the flight route currently exists
    Optional<Route> routeOptional = routeRepository.findRouteByOriginAndDestination(
      originAirportOptional.get(),
      destinationAirportOptional.get()
    );
    Route route;
    if (routeOptional.isPresent()) {
      // if the route already exists, use it
      route = routeOptional.get();
    } else {
      // if the route doesn't exist, create it
      route = new Route();
      route.setOrigin(originAirportOptional.get());
      route.setDestination(destinationAirportOptional.get());
      routeRepository.save(route);
    }
    // create the new flight
    Flight flight = new Flight(
      route,
      airplaneOptional.get(),
      flightForm.getDepartureTime(),
      flightForm.getArrivalTime(),
      flightForm.getReservedSeats(),
      flightForm.getSeatPrice()
    );
    // saave the flight
    flightRepository.save(flight);
  }
}
