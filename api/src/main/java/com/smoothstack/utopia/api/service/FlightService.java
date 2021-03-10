package com.smoothstack.utopia.api.service;

import com.smoothstack.utopia.api.dao.AirportDao;
import com.smoothstack.utopia.api.dao.FlightDao;
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

  @Autowired
  public FlightService(FlightDao flightDao, AirportDao airportDao) {
    this.flightDao = flightDao;
    this.airportDao = airportDao;
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

  public void deleteFlight(Long flightId) {
    Optional<Flight> flightOptional = flightDao.findById(flightId);
    if (flightOptional.isEmpty()) {
      throw new FlightNotFoundException();
    }
    flightDao.delete(flightOptional.get());
  }
}
