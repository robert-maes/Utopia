package com.smoothstack.utopia.api.airport;

import com.smoothstack.utopia.api.CustomException;
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
@Service
public class AirportService {

  private final AirportRepository airportRepository;
  private final RouteRepository routeRepository;

  @Autowired
  public AirportService(
    AirportRepository airportRepository,
    RouteRepository routeRepository
  ) {
    this.airportRepository = airportRepository;
    this.routeRepository = routeRepository;
  }

  /**
   * Gets a list of all airports
   * @return A list of all airports
   */
  public List<Airport> getAirports() {
    return airportRepository.findAll();
  }

  /**
   * Creates a new airport
   * @param airportForm A POJO DTO for creating a new airport
   * @throws CustomException
   */
  public void addNewAirport(AirportForm airportForm) throws CustomException {
    // ensure given IataID is in All Caps
    airportForm.setIataId(airportForm.getIataId().toUpperCase());

    // ensure that the airport does not already exist
    Optional<Airport> airportOptional = airportRepository.findById(
      airportForm.getIataId()
    );
    if (airportOptional.isPresent()) {
      throw new CustomException("IATA ID must be unique");
    }
    // create the new airport
    Airport airport = new Airport(
      airportForm.getIataId(),
      airportForm.getCity()
    );
    // save the new airport
    airportRepository.save(airport);
  }

  /**
   * Deletes the airport with the given id
   * @param airportId The 3 character IATA Id
   * @throws CustomException
   */
  public void deleteAirport(String airportId) throws CustomException {
    // if the airport does not exist, return an error message
    boolean exists = airportRepository.existsById(airportId);
    if (!exists) {
      throw new CustomException("Airport " + airportId + " does not exist");
    }
    // otherwise delete the airport
    airportRepository.deleteById(airportId);
  }

  /**
   * Updates the airport with the given id
   * @param airportId The 3 character IATA id
   * @param airportForm A POJO DTO for updating the airport
   * @throws CustomException
   */
  @Transactional
  public void updateAirport(String airportId, AirportForm airportForm)
    throws CustomException {
    // check to see if the airport we are updating exists
    Optional<Airport> airportOptional = airportRepository.findById(airportId);
    if (airportOptional.isEmpty()) {
      throw new CustomException("Cannot update an airport that does not exist");
    }
    // if we are only changing the city then do so, save and return
    Airport airport = airportOptional.get();
    if (airport.getIataId().equals(airportForm.getIataId())) {
      airport.setCity(airportForm.getCity());
      airportRepository.save(airport);
      return;
    }
    // if we are changing the IATA ID make sure it is unique
    boolean newIataIdExists = airportRepository.existsById(
      airportForm.getIataId()
    );
    // if the ID is not unique, return an error
    if (newIataIdExists) {
      throw new CustomException("IATA ID must be unique");
    }
    // if the airport we are updating has no associated routes then save it and delete the old one
    if (airport.getDepartures().isEmpty() && airport.getArrivals().isEmpty()) {
      airportRepository.save(
        new Airport(airportForm.getIataId(), airportForm.getCity())
      );
      airportRepository.deleteById(airportId);
      return;
    }
    // if the airport we are updating does have associated routes
    // update the airport and then update each associated route
    Airport updatedAirport = new Airport(
      airportForm.getIataId(),
      airportForm.getCity()
    );
    List<Route> destRoutes = routeRepository.findRoutesByDestination(airport);
    destRoutes.forEach(route -> route.setDestination(updatedAirport));
    List<Route> originRoutes = routeRepository.findRoutesByOrigin(airport);
    originRoutes.forEach(route -> route.setOrigin(updatedAirport));
    destRoutes.forEach(routeRepository::save);
    originRoutes.forEach(routeRepository::save);
    airportRepository.delete(airport);
  }

  /**
   * Gets the specified airport
   * @param airportId The 3 character IATA id
   * @return The specified airport
   * @throws CustomException
   */
  public Airport getAirport(String airportId) throws CustomException {
    Optional<Airport> airport = airportRepository.findById(airportId);
    if (airport.isEmpty()) {
      throw new CustomException("Airport " + airportId + " does not exist");
    }
    return airport.get();
  }
}
