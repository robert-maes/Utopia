package com.smoothstack.utopia.api.airport;

import com.smoothstack.utopia.api.CustomException;
import com.smoothstack.utopia.api.route.Route;
import com.smoothstack.utopia.api.route.RouteRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public List<Airport> getAirports() {
    return airportRepository.findAll();
  }

  public void addNewAirport(AirportForm airportForm) throws CustomException {
    airportForm.setIataId(airportForm.getIataId().toUpperCase());

    Optional<Airport> airportOptional = airportRepository.findById(
      airportForm.getIataId()
    );
    if (airportOptional.isPresent()) {
      throw new CustomException("IATA ID must be unique");
    }
    Airport airport = new Airport(
      airportForm.getIataId(),
      airportForm.getCity()
    );
    airportRepository.save(airport);
  }

  public void deleteAirport(String airportId) throws CustomException {
    boolean exists = airportRepository.existsById(airportId);
    if (!exists) {
      throw new CustomException("Airport " + airportId + " does not exist");
    }
    airportRepository.deleteById(airportId);
  }

  @Transactional
  public void updateAirport(String airportId, AirportForm airportForm)
    throws CustomException {
    Optional<Airport> airportOptional = airportRepository.findById(airportId);
    if (airportOptional.isEmpty()) {
      throw new CustomException("Cannot update an airport that does not exist");
    }
    Airport airport = airportOptional.get();
    if (airport.getIataId().equals(airportForm.getIataId())) {
      airport.setCity(airportForm.getCity());
      airportRepository.save(airport);
      return;
    }
    boolean newIataIdExists = airportRepository.existsById(
      airportForm.getIataId()
    );
    if (newIataIdExists) {
      throw new CustomException("IATA ID must be unique");
    }
    if (airport.getDepartures().isEmpty() && airport.getArrivals().isEmpty()) {
      airportRepository.save(
        new Airport(airportForm.getIataId(), airportForm.getCity())
      );
      airportRepository.deleteById(airportId);
      return;
    }
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

  public Airport getAirport(String airportId) throws CustomException {
    Optional<Airport> airport = airportRepository.findById(airportId);
    if (airport.isEmpty()) {
      throw new CustomException("Airport " + airportId + " does not exist");
    }
    return airport.get();
  }
}
