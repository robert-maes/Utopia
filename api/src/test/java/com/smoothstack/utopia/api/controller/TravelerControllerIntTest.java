package com.smoothstack.utopia.api.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smoothstack.utopia.api.Utils;
import com.smoothstack.utopia.api.dao.TravelerDao;
import com.smoothstack.utopia.api.dao.TravelerDao;
import com.smoothstack.utopia.api.dto.CreateTravelerDto;
import com.smoothstack.utopia.api.dto.UpdateTravelerDto;
import com.smoothstack.utopia.api.model.Traveler;
import com.smoothstack.utopia.api.model.Traveler;
import java.time.LocalDate;
import java.time.Month;
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
public class TravelerControllerIntTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  private TravelerDao travelerDao;

  private Traveler createTraveler(
    String firstName,
    String lastName,
    LocalDate birthday,
    String gender,
    String address
  ) {
    Traveler traveler = new Traveler(
      firstName,
      lastName,
      birthday,
      gender,
      address
    );
    travelerDao.save(traveler);
    return traveler;
  }

  @BeforeEach
  public void wipeTravelerDb() {
    travelerDao.deleteAll();
  }

  @Test
  public void canGetAllTravelers_whenGetTravelers_thenStatus200()
    throws Exception {
    createTraveler(
      "John",
      "Smith",
      LocalDate.of(1996, Month.OCTOBER, 8),
      "Male",
      "Earth"
    );
    mvc
      .perform(get("/traveler").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(
        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
      )
      .andExpect(jsonPath("$[0].givenName", is("John")));
  }

  @Test
  public void canGetSpecificTraveler_whenGetTravelerWithId_thenStatus200()
    throws Exception {
    Traveler traveler = createTraveler(
      "John",
      "Smith",
      LocalDate.of(1996, Month.OCTOBER, 8),
      "Male",
      "Earth"
    );
    mvc
      .perform(
        get("/traveler/" + traveler.getId())
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(
        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
      )
      .andExpect(jsonPath("$.givenName", is("John")));
  }

  @Test
  public void cannotGetTraveler_whenGetTravelerWithInvalidId_thenStatus404()
    throws Exception {
    mvc
      .perform(get("/traveler/4").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }

  @Test
  public void canCreateTraveler_whenPostTravelerWithValidData_thenStatus201()
    throws Exception {
    CreateTravelerDto createTravelerDto = new CreateTravelerDto();
    createTravelerDto.setFamilyName("Smith");
    createTravelerDto.setGivenName("John");
    createTravelerDto.setDateOfBirth("1996-10-08");
    createTravelerDto.setGender("Male");
    createTravelerDto.setAddress("Earth");
    mvc
      .perform(
        post("/traveler")
          .content(Utils.asJsonString(createTravelerDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated());
  }

  @Test
  public void canUpdateTraveler_whenPutTravelerWithValidData_thenStatus200()
    throws Exception {
    Traveler traveler = createTraveler(
      "John",
      "Smith",
      LocalDate.of(1996, Month.OCTOBER, 8),
      "Male",
      "Earth"
    );
    UpdateTravelerDto updateTravelerDto = new UpdateTravelerDto();
    updateTravelerDto.setGivenName(Optional.of("Joe"));
    mvc
      .perform(
        put("/traveler/" + traveler.getId())
          .content(Utils.asJsonString(updateTravelerDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk());
  }

  @Test
  public void cannotUpdateTraveler_whenPutTravelerWithInvalidId_thenStatus404()
    throws Exception {
    UpdateTravelerDto updateTravelerDto = new UpdateTravelerDto();
    updateTravelerDto.setGivenName(Optional.of("Joe"));
    mvc
      .perform(
        put("/traveler/4")
          .content(Utils.asJsonString(updateTravelerDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  public void canDeleteTraveler_whenDeleteTravelerWithValidId_thenStatus200()
    throws Exception {
    Traveler traveler = createTraveler(
      "John",
      "Smith",
      LocalDate.of(1996, Month.OCTOBER, 8),
      "Male",
      "Earth"
    );
    mvc
      .perform(
        delete("/traveler/" + traveler.getId())
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk());
  }

  @Test
  public void cannotDeleteTraveler_whenDeleteTravelerWithInvalidId_thenStatus404()
    throws Exception {
    mvc
      .perform(delete("/traveler/3").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }
}
