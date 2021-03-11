package com.smoothstack.utopia.api.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smoothstack.utopia.api.SeatClass;
import com.smoothstack.utopia.api.Utils;
import com.smoothstack.utopia.api.dao.*;
import com.smoothstack.utopia.api.dao.SeatDao;
import com.smoothstack.utopia.api.dto.CreateSeatDto;
import com.smoothstack.utopia.api.dto.UpdateSeatDto;
import com.smoothstack.utopia.api.model.*;
import com.smoothstack.utopia.api.model.Seat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
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
public class SeatControllerIntTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  private SeatDao seatDao;

  @Autowired
  private FlightDao flightDao;

  @Autowired
  private AirportDao airportDao;

  @Autowired
  private TicketDao ticketDao;

  private Airport airportLAX = new Airport("LAX", "Los Angeles");
  private Airport airportJFK = new Airport("JFK", "New York");
  private Flight flight = new Flight(
    airportLAX,
    airportJFK,
    Instant.now(),
    Instant.now(),
    9.99f,
    1
  );

  private Seat createSeat(Flight flight) {
    Seat seat = new Seat();
    seat.setFlight(flight);
    seat.setSeatClass(SeatClass.FIRST);
    seatDao.save(seat);
    return seat;
  }

  @BeforeEach
  public void wipeSeatDb() {
    ticketDao.deleteAll();
    seatDao.deleteAll();
    flightDao.deleteAll();
    airportDao.deleteAll();
    airportDao.save(airportLAX);
    airportDao.save(airportJFK);
    flightDao.save(flight);
  }

  @Test
  public void canGetAllSeats_whenGetSeats_thenStatus200() throws Exception {
    createSeat(flight);
    mvc
      .perform(get("/seat").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(
        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
      )
      .andExpect(jsonPath("$[0].seatClass", is("FIRST")));
  }

  @Test
  public void canGetSpecificSeat_whenGetSeatWithId_thenStatus200()
    throws Exception {
    Seat seat = createSeat(flight);
    mvc
      .perform(
        get("/seat/" + seat.getId()).contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(
        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
      )
      .andExpect(jsonPath("$.seatClass", is("FIRST")));
  }

  @Test
  public void cannotGetSeat_whenGetSeatWithInvalidId_thenStatus404()
    throws Exception {
    mvc
      .perform(get("/seat/4").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }

  @Test
  public void canCreateSeat_whenPostSeatWithValidData_thenStatus201()
    throws Exception {
    CreateSeatDto createSeatDto = new CreateSeatDto();
    createSeatDto.setSeatClass(SeatClass.FIRST);
    createSeatDto.setFlightId(flight.getId());
    mvc
      .perform(
        post("/seat")
          .content(Utils.asJsonString(createSeatDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated());
  }

  @Test
  public void cannotCreateSeat_whenPostSeatWithInvalidFlightId_thenStatus404()
    throws Exception {
    CreateSeatDto createSeatDto = new CreateSeatDto();
    createSeatDto.setSeatClass(SeatClass.FIRST);
    createSeatDto.setFlightId(999L);
    mvc
      .perform(
        post("/seat")
          .content(Utils.asJsonString(createSeatDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  public void cannotCreateSeat_whenPostSeatWithFlightIdAtCapacity_thenStatus400()
    throws Exception {
    createSeat(flight);
    CreateSeatDto createSeatDto = new CreateSeatDto();
    createSeatDto.setFlightId(flight.getId());
    createSeatDto.setSeatClass(SeatClass.FIRST);
    mvc
      .perform(
        post("/seat")
          .content(Utils.asJsonString(createSeatDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
  }

  @Test
  public void canUpdateSeat_whenPutSeatWithValidData_thenStatus200()
    throws Exception {
    Seat seat = createSeat(flight);
    UpdateSeatDto updateSeatDto = new UpdateSeatDto();
    updateSeatDto.setSeatClass(Optional.of(SeatClass.ECONOMY));
    mvc
      .perform(
        put("/seat/" + seat.getId())
          .content(Utils.asJsonString(updateSeatDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk());
  }

  @Test
  public void cannotUpdateSeat_whenPutSeatWithInvalidId_thenStatus404()
    throws Exception {
    UpdateSeatDto updateSeatDto = new UpdateSeatDto();
    updateSeatDto.setSeatClass(Optional.of(SeatClass.ECONOMY));
    mvc
      .perform(
        put("/seat/4")
          .content(Utils.asJsonString(updateSeatDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  public void cannotUpdateSeat_whenPutSeatWithInvalidFlightId_thenStatus404()
    throws Exception {
    Seat seat = createSeat(flight);
    UpdateSeatDto updateSeatDto = new UpdateSeatDto();
    updateSeatDto.setFlightId(Optional.of(999L));
    mvc
      .perform(
        put("/seat/" + seat.getId())
          .content(Utils.asJsonString(updateSeatDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  public void canDeleteSeat_whenDeleteSeatWithValidId_thenStatus200()
    throws Exception {
    Seat seat = createSeat(flight);
    mvc
      .perform(
        delete("/seat/" + seat.getId()).contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk());
  }

  @Test
  public void cannotDeleteSeat_whenDeleteSeatWithInvalidId_thenStatus404()
    throws Exception {
    mvc
      .perform(delete("/seat/3").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }
}
