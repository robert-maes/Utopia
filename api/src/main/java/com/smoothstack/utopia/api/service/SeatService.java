package com.smoothstack.utopia.api.service;

import com.smoothstack.utopia.api.dao.FlightDao;
import com.smoothstack.utopia.api.dao.SeatDao;
import com.smoothstack.utopia.api.dao.TicketDao;
import com.smoothstack.utopia.api.dto.CreateSeatDto;
import com.smoothstack.utopia.api.dto.UpdateSeatDto;
import com.smoothstack.utopia.api.exception.FlightFullException;
import com.smoothstack.utopia.api.exception.FlightNotFoundException;
import com.smoothstack.utopia.api.exception.SeatNotFoundException;
import com.smoothstack.utopia.api.model.Flight;
import com.smoothstack.utopia.api.model.Seat;
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
public class SeatService {

  private final SeatDao seatDao;
  private final FlightDao flightDao;
  private final TicketDao ticketDao;

  @Autowired
  public SeatService(
    SeatDao seatDao,
    FlightDao flightDao,
    TicketDao ticketDao
  ) {
    this.seatDao = seatDao;
    this.flightDao = flightDao;
    this.ticketDao = ticketDao;
  }

  public List<Seat> getAllSeats() {
    return seatDao.findAll();
  }

  public Seat getSeat(Long seatId) {
    // try to find the requested seat
    Optional<Seat> seatOptional = seatDao.findById(seatId);
    // if the seat does not exist, throw a 404
    if (seatOptional.isEmpty()) {
      throw new SeatNotFoundException();
    }
    // otherwise, return the requested seat
    return seatOptional.get();
  }

  public void createSeat(CreateSeatDto createSeatDto) {
    // try to find the parent flight
    Optional<Flight> flightOptional = flightDao.findById(
      createSeatDto.getFlightId()
    );
    // if the parent flight does not exist, throw a 404
    if (flightOptional.isEmpty()) {
      throw new FlightNotFoundException();
    }
    Flight flight = flightOptional.get();
    // if the flight is full, throw an error
    if (flight.getSeats().size() >= flight.getTotalSeats()) {
      throw new FlightFullException();
    }
    // create the new seat
    Seat seat = new Seat();
    seat.setSeatClass(createSeatDto.getSeatClass());
    seat.setFlight(flight);
    // save the new seat
    seatDao.save(seat);
  }

  @Transactional
  public void updateSeat(Long seatId, UpdateSeatDto updateSeatDto) {
    // try to find the seat to update
    Optional<Seat> seatToUpdateOptional = seatDao.findById(seatId);
    // if the seat does not exist, throw a 404
    if (seatToUpdateOptional.isEmpty()) {
      throw new SeatNotFoundException();
    }
    Seat seatToUpdate = seatToUpdateOptional.get();
    if (updateSeatDto.getSeatClass().isPresent()) {
      seatToUpdate.setSeatClass(updateSeatDto.getSeatClass().get());
    }
    // if updating the flight
    if (updateSeatDto.getFlightId().isPresent()) {
      // try to find the new parent flight
      Optional<Flight> flight = flightDao.findById(
        updateSeatDto.getFlightId().get()
      );
      // if the new parent flight does not exist, throw a 404
      if (flight.isEmpty()) {
        throw new FlightNotFoundException();
      }
      seatToUpdate.setFlight(flight.get());
    }
    // save the updated seat
    seatDao.save(seatToUpdate);
  }

  @Transactional
  public void deleteSeat(Long seatId) {
    // try to find the seat to delete
    Optional<Seat> seatOptional = seatDao.findById(seatId);
    // if the seat does not exist, throw a 404
    if (seatOptional.isEmpty()) {
      throw new SeatNotFoundException();
    }
    Seat seat = seatOptional.get();
    // if the seat has a ticket
    if (seat.getTicket() != null) {
      // delete the associated ticket
      ticketDao.delete(seat.getTicket());
    }
    // delete the seat
    seatDao.delete(seat);
  }
}
