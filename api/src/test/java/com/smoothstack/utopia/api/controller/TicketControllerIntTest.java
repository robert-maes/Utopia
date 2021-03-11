package com.smoothstack.utopia.api.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smoothstack.utopia.api.SeatClass;
import com.smoothstack.utopia.api.Utils;
import com.smoothstack.utopia.api.dao.*;
import com.smoothstack.utopia.api.dto.CreateTicketDto;
import com.smoothstack.utopia.api.dto.UpdateTicketDto;
import com.smoothstack.utopia.api.model.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application-integrationtest.properties"
)
public class TicketControllerIntTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  private TicketDao ticketDao;

  @Autowired
  private TravelerDao travelerDao;

  @Autowired
  private SeatDao seatDao;

  @Autowired
  private FlightDao flightDao;

  @Autowired
  private AirportDao airportDao;

  private Airport airportLAX = new Airport("LAX", "Los Angeles");
  private Airport airportJFK = new Airport("JFK", "New York");
  private Flight flight = new Flight(
    airportLAX,
    airportJFK,
    Instant.now(),
    Instant.now(),
    9.99f,
    100
  );
  private Seat seat = new Seat(flight, SeatClass.FIRST);
  private Traveler traveler = new Traveler(
    "John",
    "Smith",
    LocalDate.of(1996, Month.OCTOBER, 8),
    "Male",
    "Earth"
  );

  private Ticket createTicket(Traveler traveler, Seat seat) {
    Ticket ticket = new Ticket();
    ticket.setActive(true);
    ticket.setTraveler(traveler);
    ticket.setSeat(seat);
    ticket.setConfirmationCode(UUID.randomUUID());
    ticketDao.save(ticket);
    return ticket;
  }

  @BeforeEach
  public void wipeTicketDb() {
    ticketDao.deleteAll();
    travelerDao.deleteAll();
    seatDao.deleteAll();
    flightDao.deleteAll();
    airportDao.deleteAll();
    airportDao.save(airportLAX);
    airportDao.save(airportJFK);
    flightDao.save(flight);
    seatDao.save(seat);
    travelerDao.save(traveler);
  }

  @Test
  public void canGetAllTickets_whenGetTickets_thenStatus200() throws Exception {
    createTicket(traveler, seat);
    mvc
      .perform(get("/ticket").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(
        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
      )
      .andExpect(jsonPath("$[0].seat.seatClass", is("FIRST")));
  }

  @Test
  public void canGetSpecificTicket_whenGetTicketWithId_thenStatus200()
    throws Exception {
    Ticket ticket = createTicket(traveler, seat);
    mvc
      .perform(
        get("/ticket/" + ticket.getId()).contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(
        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
      )
      .andExpect(jsonPath("$.seat.seatClass", is("FIRST")));
  }

  @Test
  public void cannotGetTicket_whenGetTicketWithInvalidId_thenStatus404()
    throws Exception {
    mvc
      .perform(get("/ticket/4").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }

  @Test
  public void canCreateTicket_whenPostTicketWithValidData_thenStatus201()
    throws Exception {
    CreateTicketDto createTicketDto = new CreateTicketDto();
    createTicketDto.setSeatId(seat.getId());
    createTicketDto.setTravelerId(traveler.getId());
    mvc
      .perform(
        post("/ticket")
          .content(Utils.asJsonString(createTicketDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated());
  }

  @Test
  public void cannotCreateTicket_whenPostTicketWithInvalidSeatId_thenStatus404()
    throws Exception {
    CreateTicketDto createTicketDto = new CreateTicketDto();
    createTicketDto.setSeatId(999L);
    createTicketDto.setTravelerId(traveler.getId());
    mvc
      .perform(
        post("/ticket")
          .content(Utils.asJsonString(createTicketDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  public void cannotCreateTicket_whenPostTicketWithInvalidTravelerId_thenStatus404()
    throws Exception {
    CreateTicketDto createTicketDto = new CreateTicketDto();
    createTicketDto.setSeatId(seat.getId());
    createTicketDto.setTravelerId(999L);
    mvc
      .perform(
        post("/ticket")
          .content(Utils.asJsonString(createTicketDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  public void cannotCreateTicket_whenPostTicketWithOccupiedSeatId_thenStatus400()
    throws Exception {
    createTicket(traveler, seat);
    CreateTicketDto createTicketDto = new CreateTicketDto();
    createTicketDto.setSeatId(seat.getId());
    createTicketDto.setTravelerId(traveler.getId());
    mvc
      .perform(
        post("/ticket")
          .content(Utils.asJsonString(createTicketDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
  }

  @Test
  public void canUpdateTicket_whenPutTicketWithValidData_thenStatus200()
    throws Exception {
    Ticket ticket = createTicket(traveler, seat);
    UpdateTicketDto updateTicketDto = new UpdateTicketDto();
    updateTicketDto.setActive(false);
    mvc
      .perform(
        put("/ticket/" + ticket.getId())
          .content(Utils.asJsonString(updateTicketDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk());
  }

  @Test
  public void cannotUpdateTicket_whenPutTicketWithInvalidId_thenStatus404()
    throws Exception {
    UpdateTicketDto updateTicketDto = new UpdateTicketDto();
    updateTicketDto.setActive(true);
    mvc
      .perform(
        put("/ticket/4")
          .content(Utils.asJsonString(updateTicketDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  public void canDeleteTicket_whenDeleteTicketWithValidId_thenStatus200()
    throws Exception {
    Ticket ticket = createTicket(traveler, seat);
    mvc
      .perform(
        delete("/ticket/" + ticket.getId())
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk());
  }

  @Test
  public void cannotDeleteTicket_whenDeleteTicketWithInvalidId_thenStatus404()
    throws Exception {
    mvc
      .perform(delete("/ticket/3").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }
}
