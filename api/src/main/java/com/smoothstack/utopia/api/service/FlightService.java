package com.smoothstack.utopia.api.service;

import com.smoothstack.utopia.api.dao.AirportDao;
import com.smoothstack.utopia.api.dao.FlightDao;
import com.smoothstack.utopia.api.dao.SeatDao;
import com.smoothstack.utopia.api.dao.TicketDao;
import com.smoothstack.utopia.api.dto.CreateFlightDto;
import com.smoothstack.utopia.api.dto.UpdateFlightDto;
import com.smoothstack.utopia.api.exception.*;
import com.smoothstack.utopia.api.model.Airport;
import com.smoothstack.utopia.api.model.Flight;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
@Service
public class FlightService {

  private final FlightDao flightDao;
  private final AirportDao airportDao;
  private final SeatDao seatDao;
  private final TicketDao ticketDao;

  @Autowired
  public FlightService(
    FlightDao flightDao,
    AirportDao airportDao,
    SeatDao seatDao,
    TicketDao ticketDao
  ) {
    this.flightDao = flightDao;
    this.airportDao = airportDao;
    this.seatDao = seatDao;
    this.ticketDao = ticketDao;
  }

  public List<Flight> getAllFlights() {
    return flightDao.findAll();
  }

  public Flight getFlight(Long flightId) {
    // try to find the requested flight
    Optional<Flight> flightOptional = flightDao.findById(flightId);
    // if the requested flight does not exist, throw a 404
    if (flightOptional.isEmpty()) {
      throw new FlightNotFoundException();
    }
    // otherwise, return the requested flight
    return flightOptional.get();
  }

  public void createFlight(CreateFlightDto createFlightDto) {
    String originAirportId = createFlightDto.getOriginAirportId();
    String destinationAirportId = createFlightDto.getDestinationAirportId();
    Instant departureTime = createFlightDto.getDepartureTime();
    Instant arrivalTime = createFlightDto.getArrivalTime();
    Float seatPrice = createFlightDto.getSeatPrice();
    Integer totalSeats = createFlightDto.getTotalSeats();
    // if the arriving airport is the same as the departing airport, throw an error
    if (originAirportId.equals(destinationAirportId)) {
      throw new SameOriginDestinationException();
    }
    // try to find the specified origin airport
    Optional<Airport> originAirportOptional = airportDao.findAirportByIataId(
      originAirportId
    );
    // if the origin airport does not exist, throw a 404
    if (originAirportOptional.isEmpty()) {
      throw new AirportNotFoundException();
    }
    // try to find the destination airport
    Optional<Airport> destinationAirportOptional = airportDao.findAirportByIataId(
      destinationAirportId
    );
    // if the destination airport does not exist, throw a 404
    if (destinationAirportOptional.isEmpty()) {
      throw new AirportNotFoundException();
    }
    // if the departure time comes after the arrival time, throw an error, does not make sense
    if (departureTime.isAfter(arrivalTime)) {
      throw new InvalidDepartureArrivalTimeException();
    }
    // create a new flight
    Flight flight = new Flight();
    flight.setOriginAirport(originAirportOptional.get());
    flight.setDestinationAirport(destinationAirportOptional.get());
    flight.setArrivalTime(arrivalTime);
    flight.setDepartureTime(departureTime);
    flight.setSeatPrice(seatPrice);
    flight.setTotalSeats(totalSeats);
    // save the new flight
    flightDao.save(flight);
  }

  @Transactional
  public void updateFlight(Long flightId, UpdateFlightDto updateFlightDto) {
    // try to find the flight to update
    Optional<Flight> flightToUpdateOptional = flightDao.findById(flightId);
    // if the flight does not exist, throw a 404
    if (flightToUpdateOptional.isEmpty()) {
      throw new FlightNotFoundException();
    }
    Flight flightToUpdate = flightToUpdateOptional.get();
    if (updateFlightDto.getArrivalTime().isPresent()) {
      flightToUpdate.setArrivalTime(updateFlightDto.getArrivalTime().get());
    }
    if (updateFlightDto.getDepartureTime().isPresent()) {
      flightToUpdate.setDepartureTime(updateFlightDto.getDepartureTime().get());
    }
    // if the updated flight departure time comes after the arrival time, throw an error, makes no sense
    if (
      flightToUpdate.getDepartureTime().isAfter(flightToUpdate.getArrivalTime())
    ) {
      throw new InvalidDepartureArrivalTimeException();
    }
    // if updating the origin airport
    if (updateFlightDto.getOriginAirportId().isPresent()) {
      // try to find the new origin airport
      Optional<Airport> originAirportOptional = airportDao.findAirportByIataId(
        updateFlightDto.getOriginAirportId().get()
      );
      // if the new origin airport does not exist, throw a 404
      if (originAirportOptional.isEmpty()) {
        throw new AirportNotFoundException();
      }
      flightToUpdate.setOriginAirport(originAirportOptional.get());
    }
    // if updating the destination airport
    if (updateFlightDto.getDestinationAirportId().isPresent()) {
      // try to find the new destination airport
      Optional<Airport> destinationAirportOptional = airportDao.findAirportByIataId(
        updateFlightDto.getDestinationAirportId().get()
      );
      // if the new destination airport does not exist, throw a 404
      if (destinationAirportOptional.isEmpty()) {
        throw new AirportNotFoundException();
      }
      flightToUpdate.setDestinationAirport(destinationAirportOptional.get());
    }
    // if the new origin airport is the same as the departure airport, throw an error
    if (
      flightToUpdate
        .getOriginAirport()
        .equals(flightToUpdate.getDestinationAirport())
    ) {
      throw new SameOriginDestinationException();
    }
    if (updateFlightDto.getSeatPrice().isPresent()) {
      flightToUpdate.setSeatPrice(updateFlightDto.getSeatPrice().get());
    }
    // if updating total seats
    if (updateFlightDto.getTotalSeats().isPresent()) {
      // if shrinking total seats to below currently reserved seats, throw an error
      // must remove the reserved seats first
      if (
        updateFlightDto.getTotalSeats().get() <
        flightToUpdate.getReservedSeats()
      ) {
        throw new TotalSeatsLessThanReservedSeatsException();
      }
      flightToUpdate.setTotalSeats(updateFlightDto.getTotalSeats().get());
    }
    // save the updated flight
    flightDao.save(flightToUpdate);
  }

  @Transactional
  public void deleteFlight(Long flightId) {
    // try to find the flight to delete
    Optional<Flight> flightOptional = flightDao.findById(flightId);
    // if the flight does not exist, throw a 404
    if (flightOptional.isEmpty()) {
      throw new FlightNotFoundException();
    }
    Flight flight = flightOptional.get();
    // if the flight has seats
    if (!flight.getSeats().isEmpty()) {
      // loop through each seat
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
}
