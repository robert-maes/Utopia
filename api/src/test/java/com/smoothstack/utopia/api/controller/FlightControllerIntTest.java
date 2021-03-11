package com.smoothstack.utopia.api.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smoothstack.utopia.api.Utils;
import com.smoothstack.utopia.api.dao.*;
import com.smoothstack.utopia.api.dao.FlightDao;
import com.smoothstack.utopia.api.dto.CreateFlightDto;
import com.smoothstack.utopia.api.dto.UpdateFlightDto;
import com.smoothstack.utopia.api.model.*;
import com.smoothstack.utopia.api.model.Flight;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
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
public class FlightControllerIntTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  private FlightDao flightDao;

  @Autowired
  private AirportDao airportDao;

  private Airport airportLAX = new Airport("LAX", "Los Angeles");
  private Airport airportJFK = new Airport("JFK", "New York");

  private Flight createFlight(
    Airport origin,
    Airport destination,
    Instant departureTime,
    Instant arrivalTime,
    Float seatPrice,
    Integer totalSeats
  ) {
    Flight flight = new Flight(
      origin,
      destination,
      departureTime,
      arrivalTime,
      seatPrice,
      totalSeats
    );
    flightDao.save(flight);
    return flight;
  }

  @BeforeEach
  public void wipeFlightDb() {
    flightDao.deleteAll();
    airportDao.deleteAll();
    airportDao.save(airportLAX);
    airportDao.save(airportJFK);
  }

  @Test
  public void canGetAllFlights_whenGetFlights_thenStatus200() throws Exception {
    createFlight(
      airportJFK,
      airportLAX,
      Instant.now(),
      Instant.now(),
      5.00f,
      10
    );
    mvc
      .perform(get("/flight").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(
        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
      )
      .andExpect(jsonPath("$[0].originAirport.iataId", is("JFK")));
  }

  @Test
  public void canGetSpecificFlight_whenGetFlightWithId_thenStatus200()
    throws Exception {
    Flight flight = createFlight(
      airportJFK,
      airportLAX,
      Instant.now(),
      Instant.now(),
      5.00f,
      10
    );
    mvc
      .perform(
        get("/flight/" + flight.getId()).contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(
        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
      )
      .andExpect(jsonPath("$.originAirport.iataId", is("JFK")));
  }

  @Test
  public void cannotGetFlight_whenGetFlightWithInvalidId_thenStatus404()
    throws Exception {
    mvc
      .perform(get("/flight/4").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }

  @Test
  public void canCreateFlight_whenPostFlightWithValidData_thenStatus201()
    throws Exception {
    CreateFlightDto createFlightDto = new CreateFlightDto();
    createFlightDto.setDepartureTime(Instant.now());
    createFlightDto.setArrivalTime(Instant.now());
    createFlightDto.setDestinationAirportId("JFK");
    createFlightDto.setOriginAirportId("LAX");
    createFlightDto.setSeatPrice(5.00f);
    createFlightDto.setTotalSeats(10);
    mvc
      .perform(
        post("/flight")
          .content(Utils.asJsonString(createFlightDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated());
  }

  @Test
  public void cannotCreateFlight_whenPostFlightWithSameOriginAndDestination_thenStatus400()
    throws Exception {
    CreateFlightDto createFlightDto = new CreateFlightDto();
    createFlightDto.setDepartureTime(Instant.now());
    createFlightDto.setArrivalTime(Instant.now());
    createFlightDto.setDestinationAirportId("JFK");
    createFlightDto.setOriginAirportId("JFK");
    createFlightDto.setSeatPrice(5.00f);
    createFlightDto.setTotalSeats(10);
    mvc
      .perform(
        post("/flight")
          .content(Utils.asJsonString(createFlightDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
  }

  @Test
  public void cannotCreateFlight_whenPostFlightWithInvalidOriginAirport_thenStatus404()
    throws Exception {
    CreateFlightDto createFlightDto = new CreateFlightDto();
    createFlightDto.setDepartureTime(Instant.now());
    createFlightDto.setArrivalTime(Instant.now());
    createFlightDto.setDestinationAirportId("JFK");
    createFlightDto.setOriginAirportId("LOL");
    createFlightDto.setSeatPrice(5.00f);
    createFlightDto.setTotalSeats(10);
    mvc
      .perform(
        post("/flight")
          .content(Utils.asJsonString(createFlightDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  public void cannotCreateFlight_whenPostFlightWithInvalidDestinationAirport_thenStatus404()
    throws Exception {
    CreateFlightDto createFlightDto = new CreateFlightDto();
    createFlightDto.setDepartureTime(Instant.now());
    createFlightDto.setArrivalTime(Instant.now());
    createFlightDto.setDestinationAirportId("LOL");
    createFlightDto.setOriginAirportId("JFK");
    createFlightDto.setSeatPrice(5.00f);
    createFlightDto.setTotalSeats(10);
    mvc
      .perform(
        post("/flight")
          .content(Utils.asJsonString(createFlightDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  public void cannotCreateFlight_whenPostFlightWithArrivalBeforeDeparture_thenStatus404()
    throws Exception {
    CreateFlightDto createFlightDto = new CreateFlightDto();
    createFlightDto.setDepartureTime(Instant.now());
    createFlightDto.setArrivalTime(Instant.now().minus(1, ChronoUnit.HOURS));
    createFlightDto.setDestinationAirportId("LAX");
    createFlightDto.setOriginAirportId("JFK");
    createFlightDto.setSeatPrice(5.00f);
    createFlightDto.setTotalSeats(10);
    mvc
      .perform(
        post("/flight")
          .content(Utils.asJsonString(createFlightDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
  }

  @Test
  public void canUpdateFlight_whenPutFlightWithValidData_thenStatus200()
    throws Exception {
    Flight flight = createFlight(
      airportJFK,
      airportLAX,
      Instant.now(),
      Instant.now(),
      5.00f,
      10
    );
    UpdateFlightDto updateFlightDto = new UpdateFlightDto();
    updateFlightDto.setSeatPrice(Optional.of(20.00f));
    mvc
      .perform(
        put("/flight/" + flight.getId())
          .content(Utils.asJsonString(updateFlightDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk());
  }

  @Test
  public void cannotUpdateFlight_whenPutFlightWithInvalidId_thenStatus404()
    throws Exception {
    UpdateFlightDto updateFlightDto = new UpdateFlightDto();
    updateFlightDto.setSeatPrice(Optional.of(20.00f));
    mvc
      .perform(
        put("/flight/4")
          .content(Utils.asJsonString(updateFlightDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  public void cannotUpdateFlight_whenPutFlightWithArrivalBeforeDeparture_thenStatus400()
    throws Exception {
    Flight flight = createFlight(
      airportJFK,
      airportLAX,
      Instant.now(),
      Instant.now(),
      5.00f,
      10
    );
    UpdateFlightDto updateFlightDto = new UpdateFlightDto();
    updateFlightDto.setDepartureTime(Optional.of(Instant.now()));
    updateFlightDto.setArrivalTime(
      Optional.of(Instant.now().minus(1, ChronoUnit.HOURS))
    );
    mvc
      .perform(
        put("/flight/" + flight.getId())
          .content(Utils.asJsonString(updateFlightDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
  }

  @Test
  public void cannotUpdateFlight_whenPutFlightWithInvalidOriginAirport_thenStatus404()
    throws Exception {
    Flight flight = createFlight(
      airportJFK,
      airportLAX,
      Instant.now(),
      Instant.now(),
      5.00f,
      10
    );
    UpdateFlightDto updateFlightDto = new UpdateFlightDto();
    updateFlightDto.setOriginAirportId(Optional.of("LOL"));
    mvc
      .perform(
        put("/flight/" + flight.getId())
          .content(Utils.asJsonString(updateFlightDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  public void cannotUpdateFlight_whenPutFlightWithInvalidDestinationAirport_thenStatus404()
    throws Exception {
    Flight flight = createFlight(
      airportJFK,
      airportLAX,
      Instant.now(),
      Instant.now(),
      5.00f,
      10
    );
    UpdateFlightDto updateFlightDto = new UpdateFlightDto();
    updateFlightDto.setDestinationAirportId(Optional.of("LOL"));
    mvc
      .perform(
        put("/flight/" + flight.getId())
          .content(Utils.asJsonString(updateFlightDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  public void cannotUpdateFlight_whenPutFlightWithSameOriginAndDestinationAirport_thenStatus400()
    throws Exception {
    Flight flight = createFlight(
      airportJFK,
      airportLAX,
      Instant.now(),
      Instant.now(),
      5.00f,
      10
    );
    UpdateFlightDto updateFlightDto = new UpdateFlightDto();
    updateFlightDto.setOriginAirportId(Optional.of("LAX"));
    updateFlightDto.setDestinationAirportId(Optional.of("LAX"));
    mvc
      .perform(
        put("/flight/" + flight.getId())
          .content(Utils.asJsonString(updateFlightDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
  }

  //  @Test
  //  public void cannotUpdateFlight_whenPutFlightWithShrinkingSeats_thenStatus400()
  //    throws Exception {
  //    Flight flight = createFlight(
  //      airportJFK,
  //      airportLAX,
  //      Instant.now(),
  //      Instant.now(),
  //      5.00f,
  //      10
  //    );
  //    UpdateFlightDto updateFlightDto = new UpdateFlightDto();
  //    updateFlightDto.setOriginAirportId(Optional.of("LOL"));
  //    mvc
  //      .perform(
  //        put("/flight/" + flight.getId())
  //          .content(Utils.asJsonString(updateFlightDto))
  //          .contentType(MediaType.APPLICATION_JSON)
  //          .accept(MediaType.APPLICATION_JSON)
  //      )
  //      .andExpect(status().isBadRequest());
  //  }

  @Test
  public void canDeleteFlight_whenDeleteFlightWithValidId_thenStatus200()
    throws Exception {
    Flight flight = createFlight(
      airportJFK,
      airportLAX,
      Instant.now(),
      Instant.now(),
      5.00f,
      10
    );
    mvc
      .perform(
        delete("/flight/" + flight.getId())
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk());
  }

  @Test
  public void cannotDeleteFlight_whenDeleteFlightWithInvalidId_thenStatus404()
    throws Exception {
    mvc
      .perform(delete("/flight/3").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }
}
