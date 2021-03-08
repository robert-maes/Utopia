package com.smoothstack.utopia.api.flight;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.smoothstack.utopia.api.airplane.Airplane;
import com.smoothstack.utopia.api.airplane.AirplaneRepository;
import com.smoothstack.utopia.api.airplane_type.AirplaneType;
import com.smoothstack.utopia.api.airplane_type.AirplaneTypeRepository;
import com.smoothstack.utopia.api.airport.Airport;
import com.smoothstack.utopia.api.airport.AirportRepository;
import com.smoothstack.utopia.api.route.Route;
import com.smoothstack.utopia.api.route.RouteRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Rob Maes
 * Mon Mar 8 2021
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application-integrationtest.properties"
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FlightControllerIntTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private AirportRepository airportRepository;

  @Autowired
  private AirplaneRepository airplaneRepository;

  @Autowired
  private FlightRepository flightRepository;

  @Autowired
  private RouteRepository routeRepository;

  @Autowired
  private AirplaneTypeRepository airplaneTypeRepository;

  /**
   * Serializes an Object to a JSON string
   * @param obj The object to serialize
   * @return A string containing the JSON representation of obj
   */
  private static String asJsonString(final Object obj) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      // needed to prevent Instant serializing error
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.configure(
        SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,
        true
      );
      objectMapper.configure(
        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
        true
      );
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private final AirplaneType type747 = new AirplaneType(467);
  private final AirplaneType type777 = new AirplaneType(301);
  private final Airport airportSFO = new Airport("SFO", "San Francisco");
  private final Airport airportLAX = new Airport("LAX", "Los Angeles");
  private final Airport airportIAH = new Airport("IAH", "Houston");
  private final Airport airportJFK = new Airport("JFK", "New York");
  private final Airplane airplane747 = new Airplane(type747);
  private final Airplane airplane777 = new Airplane(type777);
  private final Route routeLAX_JFK = new Route(airportLAX, airportJFK);
  private final Route routeSFO_IAH = new Route(airportSFO, airportIAH);

  /**
   * Creates a new flight and saves it to the in-mem DB
   * @param route The route for the flight
   * @param airplane The airplane for the flight
   * @param departureTime The time the flight departs
   * @param arrivalTime The time the flight arrives
   * @param reservedSeats The number of seats that are reserved on the flight
   * @param seatPrice The price for a seat on the flight
   * @return
   */
  private Flight createFlight(
    Route route,
    Airplane airplane,
    Instant departureTime,
    Instant arrivalTime,
    Integer reservedSeats,
    Float seatPrice
  ) {
    Flight flight = new Flight(
      route,
      airplane,
      departureTime,
      arrivalTime,
      reservedSeats,
      seatPrice
    );
    flightRepository.save(flight);
    return flight;
  }

  /**
   * Before running the tests, populate the in-mem DB with some dummy data
   */
  @BeforeAll
  private void saveAirportsAirplanesRoutes() {
    airportRepository.deleteAll();
    airplaneTypeRepository.save(type747);
    airplaneTypeRepository.save(type777);
    airplaneRepository.save(airplane747);
    airplaneRepository.save(airplane777);
    routeRepository.save(routeLAX_JFK);
    routeRepository.save(routeSFO_IAH);
  }

  /**
   * Before each test, wipe the flight table from the in-mem DB
   */
  @BeforeEach
  public void wipeFlightDb() {
    flightRepository.deleteAll();
  }

  //GET

  /**
   * Tests that GET /flight returns a list of all flights
   * @throws Exception
   */
  @Test
  public void givenFlights_whenGetFlights_thenStatus200() throws Exception {
    createFlight(
      routeLAX_JFK,
      airplane747,
      Instant.now(),
      Instant.now().plus(1, ChronoUnit.HOURS),
      50,
      100f
    );
    mvc
      .perform(get("/flight").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(
        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
      )
      .andExpect(jsonPath("$[0].route.origin.iataId", is("LAX")))
      .andExpect(jsonPath("$[0].route.destination.iataId", is("JFK")));
  }

  /**
   * Tests that GET /flight/{flightId} returns the correct flight
   * @throws Exception
   */
  @Test
  public void givenExistingFlight_whenGetFlight_thenStatus200()
    throws Exception {
    Flight flight = createFlight(
      routeLAX_JFK,
      airplane747,
      Instant.now(),
      Instant.now().plus(1, ChronoUnit.HOURS),
      50,
      100f
    );
    mvc
      .perform(
        get("/flight/" + flight.getId()).contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(
        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
      )
      .andExpect(jsonPath("$.route.origin.iataId", is("LAX")))
      .andExpect(jsonPath("$.route.destination.iataId", is("JFK")));
  }

  /**
   * Tests that GET /flight/{invalidId} returns an error
   * @throws Exception
   */
  @Test
  public void givenNonExistentFlight_whenGetFlight_thenStatus400()
    throws Exception {
    mvc
      .perform(get("/flight/1").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

  //POST

  /**
   * Tests that POST /flight with valid properties creates a new flight
   * @throws Exception
   */
  @Test
  public void givenValidFlight_whenCreateFlight_thenStatus201()
    throws Exception {
    FlightForm input = new FlightForm();
    input.setAirplane(1L);
    input.setArrivalTime(Instant.now().plus(1, ChronoUnit.HOURS));
    input.setDepartureTime(Instant.now());
    input.setReservedSeats(40);
    input.setSeatPrice(20f);
    input.setDestinationAirport("IAH");
    input.setOriginAirport("LAX");
    mvc
      .perform(
        post("/flight")
          .content(asJsonString(input))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated());
  }

  /**
   * Tests that trying to create a flight with the same airport for origin AND destination returns an error
   * @throws Exception
   */
  @Test
  public void givenFlightWithSameOriginAndDestination_whenCreateFlight_thenStatus400()
    throws Exception {
    FlightForm input = new FlightForm();
    input.setAirplane(1L);
    input.setArrivalTime(Instant.now().plus(1, ChronoUnit.HOURS));
    input.setDepartureTime(Instant.now());
    input.setReservedSeats(40);
    input.setSeatPrice(20f);
    input.setDestinationAirport("LAX");
    input.setOriginAirport("LAX");
    mvc
      .perform(
        post("/flight")
          .content(asJsonString(input))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
  }

  /**
   * Tests that trying to create a flight with an arrival time that
   * chronologically comes before departure time returns an error
   * @throws Exception
   */
  @Test
  public void givenFlightWithDepartureAfterArrival_whenCreateFlight_thenStatus400()
    throws Exception {
    FlightForm input = new FlightForm();
    input.setAirplane(1L);
    input.setArrivalTime(Instant.now().minus(1, ChronoUnit.HOURS));
    input.setDepartureTime(Instant.now());
    input.setReservedSeats(40);
    input.setSeatPrice(20f);
    input.setDestinationAirport("IAH");
    input.setOriginAirport("LAX");
    mvc
      .perform(
        post("/flight")
          .content(asJsonString(input))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
  }

  /**
   * Tests that trying to create a flight with an airplane that does not exist returns an error
   * @throws Exception
   */
  @Test
  public void givenFlightWithNonExistentAirplane_whenCreateFlight_thenStatus400()
    throws Exception {
    FlightForm input = new FlightForm();
    input.setAirplane(10L);
    input.setArrivalTime(Instant.now().plus(1, ChronoUnit.HOURS));
    input.setDepartureTime(Instant.now());
    input.setReservedSeats(40);
    input.setSeatPrice(20f);
    input.setDestinationAirport("IAH");
    input.setOriginAirport("LAX");
    mvc
      .perform(
        post("/flight")
          .content(asJsonString(input))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
  }

  /**
   * Tests that trying to create a flight with an airport that does not exist returns an error
   * @throws Exception
   */
  @Test
  public void givenFlightWithNonExistentAirport_whenCreateFlight_thenStatus400()
    throws Exception {
    FlightForm input = new FlightForm();
    input.setAirplane(1L);
    input.setArrivalTime(Instant.now().plus(1, ChronoUnit.HOURS));
    input.setDepartureTime(Instant.now());
    input.setReservedSeats(40);
    input.setSeatPrice(20f);
    input.setDestinationAirport("LOL");
    input.setOriginAirport("LAX");
    mvc
      .perform(
        post("/flight")
          .content(asJsonString(input))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
  }

  // DELETE

  /**
   * Tests that deleting an existing flight works
   * @throws Exception
   */
  @Test
  public void givenExistingFlight_whenDeleteFlight_thenStatus200()
    throws Exception {
    Flight flight = createFlight(
      routeLAX_JFK,
      airplane747,
      Instant.now(),
      Instant.now().plus(1, ChronoUnit.HOURS),
      50,
      100f
    );
    mvc
      .perform(
        delete("/flight/" + flight.getId())
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk());
  }

  /**
   * Tests that trying to delete a flight that does not exist returns an error
   * @throws Exception
   */
  @Test
  public void givenNonExistentFlight_whenDeleteFlight_thenStatus400()
    throws Exception {
    mvc
      .perform(delete("/flight/1").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }
  //PUT
}
