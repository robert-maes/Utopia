package com.smoothstack.utopia.api.controller;

import com.smoothstack.utopia.api.dto.CreateTicketDto;
import com.smoothstack.utopia.api.dto.UpdateTicketDto;
import com.smoothstack.utopia.api.model.Ticket;
import com.smoothstack.utopia.api.service.TicketService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ticket")
public class TicketController {

  private final TicketService ticketService;

  @Autowired
  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  @GetMapping
  public List<Ticket> getAllTickets() {
    return ticketService.getAllTickets();
  }

  @GetMapping(path = "{ticketId}")
  public Ticket getTicket(@PathVariable("ticketId") Long ticketId) {
    return ticketService.getTicket(ticketId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createTicket(
    @Valid @RequestBody CreateTicketDto createTicketDto
  ) {
    ticketService.createTicket(createTicketDto);
  }

  @PutMapping(path = "{ticketId}")
  public void updateTicket(
    @PathVariable("ticketId") Long ticketId,
    @Valid @RequestBody UpdateTicketDto updateTicketDto
  ) {
    ticketService.updateTicket(ticketId, updateTicketDto);
  }

  @DeleteMapping(path = "{ticketId}")
  public void deleteTicket(@PathVariable("ticketId") Long ticketId) {
    ticketService.deleteTicket(ticketId);
  }
}
