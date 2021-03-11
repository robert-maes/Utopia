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
    Optional<Seat> seatOptional = seatDao.findById(seatId);
    if (seatOptional.isEmpty()) {
      throw new SeatNotFoundException();
    }
    return seatOptional.get();
  }

  public void createSeat(CreateSeatDto createSeatDto) {
    Optional<Flight> flightOptional = flightDao.findById(
      createSeatDto.getFlightId()
    );
    if (flightOptional.isEmpty()) {
      throw new FlightNotFoundException();
    }
    Flight flight = flightOptional.get();
    if (flight.getSeats().size() >= flight.getTotalSeats()) {
      throw new FlightFullException();
    }
    Seat seat = new Seat();
    seat.setSeatClass(createSeatDto.getSeatClass());
    seat.setFlight(flight);
    seatDao.save(seat);
  }

  @Transactional
  public void updateSeat(Long seatId, UpdateSeatDto updateSeatDto) {
    Optional<Seat> seatToUpdateOptional = seatDao.findById(seatId);
    if (seatToUpdateOptional.isEmpty()) {
      throw new SeatNotFoundException();
    }
    Seat seatToUpdate = seatToUpdateOptional.get();
    if (updateSeatDto.getSeatClass().isPresent()) {
      seatToUpdate.setSeatClass(updateSeatDto.getSeatClass().get());
    }
    if (updateSeatDto.getFlightId().isPresent()) {
      Optional<Flight> flight = flightDao.findById(
        updateSeatDto.getFlightId().get()
      );
      if (flight.isEmpty()) {
        throw new FlightNotFoundException();
      }
      seatToUpdate.setFlight(flight.get());
    }
    seatDao.save(seatToUpdate);
  }

  @Transactional
  public void deleteSeat(Long seatId) {
    Optional<Seat> seatOptional = seatDao.findById(seatId);
    if (seatOptional.isEmpty()) {
      throw new SeatNotFoundException();
    }
    Seat seat = seatOptional.get();
    if (seat.getTicket() != null) {
      ticketDao.delete(seat.getTicket());
    }
    seatDao.delete(seat);
  }
}
