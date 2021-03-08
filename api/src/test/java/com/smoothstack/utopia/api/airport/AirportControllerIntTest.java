package com.smoothstack.utopia.api.airport;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.print.attribute.standard.Media;
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
public class AirportControllerIntTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private AirportRepository airportRepository;

  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void createAirport(String iataId, String city) {
    Airport airport = new Airport(iataId, city);
    airportRepository.save(airport);
  }

  @BeforeEach
  public void wipeAirportDb() {
    airportRepository.deleteAll();
  }

  // GET
  @Test
  public void givenAirports_whenGetAirports_thenStatus200() throws Exception {
    createAirport("IAH", "Houston");
    mvc
      .perform(get("/airport").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(
        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
      )
      .andExpect(jsonPath("$[0].iataId", is("IAH")));
  }

  @Test
  public void givenExistingAirport_whenGetAirport_thenStatus200()
    throws Exception {
    createAirport("IAH", "Houston");
    mvc
      .perform(get("/airport/IAH").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(
        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
      )
      .andExpect(jsonPath("$.iataId", is("IAH")));
  }

  @Test
  public void givenNonExistentAirport_whenGetAirport_thenStatus400()
    throws Exception {
    mvc
      .perform(get("/airport/IAH").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

  // POST
  @Test
  public void givenValidAirport_whenCreateAirport_thenStatus201()
    throws Exception {
    AirportForm input = new AirportForm();
    input.setIataId("SFO");
    input.setCity("San Francisco");
    mvc
      .perform(
        post("/airport")
          .content(asJsonString(input))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated());
  }

  @Test
  public void givenDuplicateAirport_whenCreateAirport_thenStatus400()
    throws Exception {
    createAirport("SFO", "San Francisco");
    AirportForm input = new AirportForm();
    input.setIataId("SFO");
    input.setCity("San Francisco");
    mvc
      .perform(
        post("/airport")
          .content(asJsonString(input))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
  }

  // DELETE
  @Test
  public void givenExistingAirport_whenDeleteAirport_thenStatus200()
    throws Exception {
    createAirport("LAX", "Los Angeles");
    mvc
      .perform(delete("/airport/LAX").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @Test
  public void givenNonExistentAirport_whenDeleteAirport_thenStatus400()
    throws Exception {
    mvc
      .perform(delete("/airport/LAX").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

  // PUT
  @Test
  public void givenNonExistentAirport_whenUpdateAirport_thenStatus400()
    throws Exception {
    AirportForm input = new AirportForm();
    input.setIataId("LAX");
    input.setCity("Los Angeles");
    mvc
      .perform(
        put("/airport/SFO")
          .content(asJsonString(input))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
  }

  @Test
  public void givenDuplicateAirport_whenUpdateAirport_thenStatus400()
    throws Exception {
    createAirport("SFO", "San Francisco");
    createAirport("LAX", "Los Angeles");
    AirportForm input = new AirportForm();
    input.setIataId("SFO");
    input.setCity("San Francisco");
    mvc
      .perform(
        put("/airport/LAX")
          .content(asJsonString(input))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
  }

  @Test
  public void givenValidAirport_whenUpdateAirport_thenStatus200()
    throws Exception {
    createAirport("SGO", "San Francisco");
    AirportForm input = new AirportForm();
    input.setIataId("SFO");
    input.setCity("San Francisco");
    mvc
      .perform(
        put("/airport/SGO")
          .content(asJsonString(input))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk());
  }
}
