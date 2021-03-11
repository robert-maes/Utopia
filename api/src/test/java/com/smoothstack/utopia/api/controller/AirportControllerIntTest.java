package com.smoothstack.utopia.api.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smoothstack.utopia.api.Utils;
import com.smoothstack.utopia.api.dao.AirportDao;
import com.smoothstack.utopia.api.dto.CreateAirportDto;
import com.smoothstack.utopia.api.dto.UpdateAirportDto;
import com.smoothstack.utopia.api.model.Airport;
import java.util.Optional;
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
  MockMvc mvc;

  @Autowired
  private AirportDao airportDao;

  private void createAirport(String iataId, String city) {
    Airport airport = new Airport(iataId, city);
    airportDao.save(airport);
  }

  @BeforeEach
  public void wipeAirportDb() {
    airportDao.deleteAll();
  }

  @Test
  public void canGetAllAirports_whenGetAirports_thenStatus200()
    throws Exception {
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
  public void canGetSpecificAirport_whenGetAirportWithId_thenStatus200()
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
  public void cannotGetAirport_whenGetAirportWithInvalidId_thenStatus404()
    throws Exception {
    mvc
      .perform(get("/airport/IAH").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }

  @Test
  public void canCreateAirport_whenPostAirportWithValidData_thenStatus201()
    throws Exception {
    CreateAirportDto createAirportDto = new CreateAirportDto();
    createAirportDto.setCity("San Francisco");
    createAirportDto.setId("SFO");
    mvc
      .perform(
        post("/airport")
          .content(Utils.asJsonString(createAirportDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated());
  }

  @Test
  public void cannotCreateAirport_whenPostAirportWithDuplicateId_thenStatus409()
    throws Exception {
    createAirport("LAX", "Los Angeles");
    CreateAirportDto createAirportDto = new CreateAirportDto();
    createAirportDto.setId("LAX");
    createAirportDto.setCity("Houston");
    mvc
      .perform(
        post("/airport")
          .content(Utils.asJsonString(createAirportDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isConflict());
  }

  @Test
  public void canUpdateAirport_whenPutAirportWithValidData_thenStatus200()
    throws Exception {
    createAirport("JFK", "New York");
    UpdateAirportDto updateAirportDto = new UpdateAirportDto();
    updateAirportDto.setId(Optional.of("LAX"));
    mvc
      .perform(
        put("/airport/JFK")
          .content(Utils.asJsonString(updateAirportDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk());
  }

  @Test
  public void cannotUpdateAirport_whenPutAirportWithInvalidId_thenStatus404()
    throws Exception {
    UpdateAirportDto updateAirportDto = new UpdateAirportDto();
    updateAirportDto.setId(Optional.of("JFK"));
    mvc
      .perform(
        put("/airport/LAX")
          .content(Utils.asJsonString(updateAirportDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  public void cannotUpdateAirport_whenPutAirportWithDuplicateId_thenStatus409()
    throws Exception {
    createAirport("JFK", "New York");
    createAirport("LAX", "Los Angeles");
    UpdateAirportDto updateAirportDto = new UpdateAirportDto();
    updateAirportDto.setId(Optional.of("JFK"));
    mvc
      .perform(
        put("/airport/LAX")
          .content(Utils.asJsonString(updateAirportDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isConflict());
  }

  @Test
  public void canDeleteAirport_whenDeleteAirportWithValidId_thenStatus200()
    throws Exception {
    createAirport("DFW", "Dallas");
    mvc
      .perform(delete("/airport/DFW").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @Test
  public void cannotDeleteAirport_whenDeleteAirportWithInvalidId_thenStatus404()
    throws Exception {
    mvc
      .perform(delete("/airport/IAH").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }
}
