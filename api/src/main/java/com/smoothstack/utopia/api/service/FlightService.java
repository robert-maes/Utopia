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
    Optional<Flight> flightOptional = flightDao.findById(flightId);
    if (flightOptional.isEmpty()) {
      throw new FlightNotFoundException();
    }
    return flightOptional.get();
  }

  public void createFlight(CreateFlightDto createFlightDto) {
    String originAirportId = createFlightDto.getOriginAirportId();
    String destinationAirportId = createFlightDto.getDestinationAirportId();
    Instant departureTime = createFlightDto.getDepartureTime();
    Instant arrivalTime = createFlightDto.getArrivalTime();
    Float seatPrice = createFlightDto.getSeatPrice();
    Integer totalSeats = createFlightDto.getTotalSeats();
    if (originAirportId.equals(destinationAirportId)) {
      throw new SameOriginDestinationException();
    }
    Optional<Airport> originAirportOptional = airportDao.findAirportByIataId(
      originAirportId
    );
    if (originAirportOptional.isEmpty()) {
      throw new AirportNotFoundException();
    }
    Optional<Airport> destinationAirportOptional = airportDao.findAirportByIataId(
      destinationAirportId
    );
    if (destinationAirportOptional.isEmpty()) {
      throw new AirportNotFoundException();
    }
    if (departureTime.isAfter(arrivalTime)) {
      throw new InvalidDepartureArrivalTimeException();
    }
    Flight flight = new Flight();
    flight.setOriginAirport(originAirportOptional.get());
    flight.setDestinationAirport(destinationAirportOptional.get());
    flight.setArrivalTime(arrivalTime);
    flight.setDepartureTime(departureTime);
    flight.setSeatPrice(seatPrice);
    flight.setTotalSeats(totalSeats);
    flightDao.save(flight);
  }

  @Transactional
  public void updateFlight(Long flightId, UpdateFlightDto updateFlightDto) {
    Optional<Flight> flightToUpdateOptional = flightDao.findById(flightId);
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
    if (
      flightToUpdate.getDepartureTime().isAfter(flightToUpdate.getArrivalTime())
    ) {
      throw new InvalidDepartureArrivalTimeException();
    }
    if (updateFlightDto.getOriginAirportId().isPresent()) {
      Optional<Airport> originAirportOptional = airportDao.findAirportByIataId(
        updateFlightDto.getOriginAirportId().get()
      );
      if (originAirportOptional.isEmpty()) {
        throw new AirportNotFoundException();
      }
      flightToUpdate.setOriginAirport(originAirportOptional.get());
    }
    if (updateFlightDto.getDestinationAirportId().isPresent()) {
      Optional<Airport> destinationAirportOptional = airportDao.findAirportByIataId(
        updateFlightDto.getDestinationAirportId().get()
      );
      if (destinationAirportOptional.isEmpty()) {
        throw new AirportNotFoundException();
      }
      flightToUpdate.setDestinationAirport(destinationAirportOptional.get());
    }
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
    if (updateFlightDto.getTotalSeats().isPresent()) {
      if (
        updateFlightDto.getTotalSeats().get() <
        flightToUpdate.getReservedSeats()
      ) {
        throw new TotalSeatsLessThanReservedSeatsException();
      }
      flightToUpdate.setTotalSeats(updateFlightDto.getTotalSeats().get());
    }
    flightDao.save(flightToUpdate);
  }

  @Transactional
  public void deleteFlight(Long flightId) {
    Optional<Flight> flightOptional = flightDao.findById(flightId);
    if (flightOptional.isEmpty()) {
      throw new FlightNotFoundException();
    }
    Flight flight = flightOptional.get();
    if (!flight.getSeats().isEmpty()) {
      flight
        .getSeats()
        .forEach(
          seat -> {
            if (seat.getTicket() != null) {
              ticketDao.delete(seat.getTicket());
            }
            seatDao.delete(seat);
          }
        );
    }
    flightDao.delete(flight);
  }
}
