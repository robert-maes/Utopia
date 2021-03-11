package com.smoothstack.utopia.api.service;

import com.smoothstack.utopia.api.dao.SeatDao;
import com.smoothstack.utopia.api.dao.TicketDao;
import com.smoothstack.utopia.api.dao.TravelerDao;
import com.smoothstack.utopia.api.dto.CreateTicketDto;
import com.smoothstack.utopia.api.dto.UpdateTicketDto;
import com.smoothstack.utopia.api.exception.SeatNotFoundException;
import com.smoothstack.utopia.api.exception.SeatOccupiedException;
import com.smoothstack.utopia.api.exception.TicketNotFoundException;
import com.smoothstack.utopia.api.exception.TravelerNotFoundException;
import com.smoothstack.utopia.api.model.Seat;
import com.smoothstack.utopia.api.model.Ticket;
import com.smoothstack.utopia.api.model.Traveler;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
@Service
public class TicketService {

  private final TicketDao ticketDao;
  private final SeatDao seatDao;
  private final TravelerDao travelerDao;

  @Autowired
  public TicketService(
    TicketDao ticketDao,
    SeatDao seatDao,
    TravelerDao travelerDao
  ) {
    this.ticketDao = ticketDao;
    this.seatDao = seatDao;
    this.travelerDao = travelerDao;
  }

  public List<Ticket> getAllTickets() {
    return ticketDao.findAll();
  }

  public Ticket getTicket(Long ticketId) {
    Optional<Ticket> ticketOptional = ticketDao.findById(ticketId);
    if (ticketOptional.isEmpty()) {
      throw new TicketNotFoundException();
    }
    return ticketOptional.get();
  }

  public void createTicket(CreateTicketDto createTicketDto) {
    Long seatId = createTicketDto.getSeatId();
    Long travelerId = createTicketDto.getTravelerId();
    Optional<Seat> seatOptional = seatDao.findById(seatId);
    if (seatOptional.isEmpty()) {
      throw new SeatNotFoundException();
    }
    Optional<Traveler> travelerOptional = travelerDao.findById(travelerId);
    if (travelerOptional.isEmpty()) {
      throw new TravelerNotFoundException();
    }
    Seat seat = seatOptional.get();
    Traveler traveler = travelerOptional.get();
    if (seat.getTicket() != null) {
      throw new SeatOccupiedException();
    }
    Ticket ticket = new Ticket();
    ticket.setActive(true);
    ticket.setSeat(seat);
    ticket.setTraveler(traveler);
    ticket.setConfirmationCode(UUID.randomUUID());
    ticketDao.save(ticket);
  }

  @Transactional
  public void updateTicket(Long ticketId, UpdateTicketDto updateTicketDto) {
    Optional<Ticket> ticketToUpdateOptional = ticketDao.findById(ticketId);
    if (ticketToUpdateOptional.isEmpty()) {
      throw new TicketNotFoundException();
    }
    Ticket ticketToUpdate = ticketToUpdateOptional.get();
    ticketToUpdate.setActive(updateTicketDto.getIsActive());
    ticketDao.save(ticketToUpdate);
  }

  public void deleteTicket(Long ticketId) {
    Optional<Ticket> ticketOptional = ticketDao.findById(ticketId);
    if (ticketOptional.isEmpty()) {
      throw new TicketNotFoundException();
    }
    ticketDao.delete(ticketOptional.get());
  }
}
