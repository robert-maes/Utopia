package com.smoothstack.utopia.api.service;

import com.smoothstack.utopia.api.dao.AirportDao;
import com.smoothstack.utopia.api.dao.FlightDao;
import com.smoothstack.utopia.api.dao.SeatDao;
import com.smoothstack.utopia.api.dao.TicketDao;
import com.smoothstack.utopia.api.dto.CreateAirportDto;
import com.smoothstack.utopia.api.dto.UpdateAirportDto;
import com.smoothstack.utopia.api.exception.AirportNotFoundException;
import com.smoothstack.utopia.api.exception.DuplicateAirportException;
import com.smoothstack.utopia.api.model.Airport;
import com.smoothstack.utopia.api.model.Flight;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
@Service
public class AirportService {

  private final AirportDao airportDao;
  private final FlightDao flightDao;
  private final SeatDao seatDao;
  private final TicketDao ticketDao;

  @Autowired
  public AirportService(
    AirportDao airportDao,
    FlightDao flightDao,
    SeatDao seatDao,
    TicketDao ticketDao
  ) {
    this.airportDao = airportDao;
    this.flightDao = flightDao;
    this.seatDao = seatDao;
    this.ticketDao = ticketDao;
  }

  public List<Airport> getAllAirports() {
    return airportDao.findAll();
  }

  public Airport getAirport(String airportId) {
    // try to find the requested airport
    Optional<Airport> airportOptional = airportDao.findAirportByIataId(
      airportId
    );
    // throw a 404 if it does not exist
    if (airportOptional.isEmpty()) {
      throw new AirportNotFoundException();
    }
    // otherwise return the airport
    return airportOptional.get();
  }

  public void createAirport(CreateAirportDto createAirportDto) {
    // try to find an airport with the id of the airport to be created
    Optional<Airport> airportOptional = airportDao.findAirportByIataId(
      createAirportDto.getId()
    );
    // if an airport with this id already exists, throw an error
    if (airportOptional.isPresent()) {
      throw new DuplicateAirportException();
    }
    // create new airport
    Airport airport = new Airport(
      createAirportDto.getId(),
      createAirportDto.getCity()
    );
    // save airport
    airportDao.save(airport);
  }

  @Transactional
  public void updateAirport(
    String airportId,
    UpdateAirportDto updateAirportDto
  ) {
    // try to find the airport to update
    Optional<Airport> airportToUpdateOptional = airportDao.findAirportByIataId(
      airportId
    );
    // if the airport to update does not exist, throw a 404
    if (airportToUpdateOptional.isEmpty()) {
      throw new AirportNotFoundException();
    }
    Airport airportToUpdate = airportToUpdateOptional.get();
    // if the user is updating the airport id
    if (updateAirportDto.getId().isPresent()) {
      String newAirportId = updateAirportDto.getId().get();
      // if the user is changing the airport id to a new id
      if (!newAirportId.equals(airportToUpdate.getIataId())) {
        // try to find an airport with this new id
        Optional<Airport> newIdAirportOptional = airportDao.findAirportByIataId(
          newAirportId
        );
        // if an airport exists, throw an error, airport id must be unique
        if (newIdAirportOptional.isPresent()) {
          throw new DuplicateAirportException();
        }
      }
      // otherwise, update the id
      airportToUpdate.setIataId(newAirportId);
    }
    // if the user provided a new city, update the city
    if (updateAirportDto.getCity().isPresent()) {
      String newAirportCity = updateAirportDto.getCity().get();
      airportToUpdate.setCity(newAirportCity);
    }
    // save the airport
    airportDao.save(airportToUpdate);
  }

  @Transactional
  public void deleteAirport(String airportId) {
    // try to find the airport to delete
    Optional<Airport> airportOptional = airportDao.findAirportByIataId(
      airportId
    );
    // if the airport does not exist, throw a 404
    if (airportOptional.isEmpty()) {
      throw new AirportNotFoundException();
    }
    Airport airport = airportOptional.get();
    // create a set to hold all of the airports flights
    Set<Flight> allFlights;
    // if the airport contains flights
    if (
      !airport.getDepartingFlights().isEmpty() ||
      !airport.getArrivingFlights().isEmpty()
    ) {
      allFlights = new HashSet<>();
      // if the airport has departing flights, add them to all flights container
      if (!airport.getDepartingFlights().isEmpty()) {
        allFlights.addAll(airport.getDepartingFlights());
      }
      // if the airport has arriving flights, add them to all flights container
      if (!airport.getArrivingFlights().isEmpty()) {
        allFlights.addAll(airport.getArrivingFlights());
      }
      // loop through each flight
      allFlights.forEach(
        flight -> {
          // if the flight has seats
          if (!flight.getSeats().isEmpty()) {
            // loop through all seats
            flight
              .getSeats()
              .forEach(
                seat -> {
                  // if the seat has a ticket, delete the ticket
                  if (seat.getTicket() != null) {
                    ticketDao.delete(seat.getTicket());
                  }
                  // delete the seat
                  seatDao.delete(seat);
                }
              );
          }
          // delete the flight
          flightDao.delete(flight);
        }
      );
    }
    // delete the airport
    airportDao.delete(airport);
  }
}
