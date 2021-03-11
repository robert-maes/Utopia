package com.smoothstack.utopia.api.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smoothstack.utopia.api.Utils;
import com.smoothstack.utopia.api.dao.EmployeeDao;
import com.smoothstack.utopia.api.dto.CreateEmployeeDto;
import com.smoothstack.utopia.api.dto.UpdateEmployeeDto;
import com.smoothstack.utopia.api.model.Employee;
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
public class EmployeeControllerIntTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  private EmployeeDao employeeDao;

  private Employee createEmployee(
    String firstName,
    String lastName,
    String username,
    String email,
    String phoneNumber
  ) {
    Employee employee = new Employee(
      firstName,
      lastName,
      username,
      email,
      phoneNumber
    );
    employeeDao.save(employee);
    return employee;
  }

  @BeforeEach
  public void wipeEmployeeDb() {
    employeeDao.deleteAll();
  }

  @Test
  public void canGetAllEmployees_whenGetEmployees_thenStatus200()
    throws Exception {
    createEmployee(
      "John",
      "Smith",
      "jsmith1996",
      "johnsmith@example.com",
      "111-867-5309"
    );
    mvc
      .perform(get("/employee").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(
        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
      )
      .andExpect(jsonPath("$[0].givenName", is("John")));
  }

  @Test
  public void canGetSpecificEmployee_whenGetEmployeeWithId_thenStatus200()
    throws Exception {
    Employee employee = createEmployee(
      "John",
      "Smith",
      "jsmith1996",
      "johnsmith@example.com",
      "111-867-5309"
    );
    mvc
      .perform(
        get("/employee/" + employee.getId())
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(
        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
      )
      .andExpect(jsonPath("$.givenName", is("John")));
  }

  @Test
  public void cannotGetEmployee_whenGetEmployeeWithInvalidId_thenStatus404()
    throws Exception {
    mvc
      .perform(get("/employee/4").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }

  @Test
  public void canCreateEmployee_whenPostEmployeeWithValidData_thenStatus201()
    throws Exception {
    CreateEmployeeDto createEmployeeDto = new CreateEmployeeDto();
    createEmployeeDto.setEmail("johnsmith@example.com");
    createEmployeeDto.setFamilyName("Smith");
    createEmployeeDto.setGivenName("John");
    createEmployeeDto.setPhoneNumber("111-867-5309");
    createEmployeeDto.setUsername("jsmith1996");
    mvc
      .perform(
        post("/employee")
          .content(Utils.asJsonString(createEmployeeDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated());
  }

  @Test
  public void cannotCreateEmployee_whenPostEmployeeWithDuplicateUsername_thenStatus409()
    throws Exception {
    Employee employee = createEmployee(
      "John",
      "Smith",
      "jsmith1996",
      "johnsmith@example.com",
      "111-867-5309"
    );
    CreateEmployeeDto createEmployeeDto = new CreateEmployeeDto();
    createEmployeeDto.setEmail("joebob@example.com");
    createEmployeeDto.setFamilyName("Bob");
    createEmployeeDto.setGivenName("Joe");
    createEmployeeDto.setPhoneNumber("222-333-4545");
    createEmployeeDto.setUsername("jsmith1996");
    mvc
      .perform(
        post("/employee")
          .content(Utils.asJsonString(createEmployeeDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isConflict());
  }

  @Test
  public void canUpdateEmployee_whenPutEmployeeWithValidData_thenStatus200()
    throws Exception {
    Employee employee = createEmployee(
      "John",
      "Smith",
      "jsmith1996",
      "johnsmith@example.com",
      "111-867-5309"
    );
    UpdateEmployeeDto updateEmployeeDto = new UpdateEmployeeDto();
    updateEmployeeDto.setGivenName(Optional.of("Joe"));
    mvc
      .perform(
        put("/employee/" + employee.getId())
          .content(Utils.asJsonString(updateEmployeeDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk());
  }

  @Test
  public void cannotUpdateEmployee_whenPutEmployeeWithInvalidId_thenStatus404()
    throws Exception {
    UpdateEmployeeDto updateEmployeeDto = new UpdateEmployeeDto();
    updateEmployeeDto.setGivenName(Optional.of("Joe"));
    mvc
      .perform(
        put("/employee/4")
          .content(Utils.asJsonString(updateEmployeeDto))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  public void canDeleteEmployee_whenDeleteEmployeeWithValidId_thenStatus200()
    throws Exception {
    Employee employee = createEmployee(
      "John",
      "Smith",
      "jsmith1996",
      "johnsmith@example.com",
      "111-867-5309"
    );
    mvc
      .perform(
        delete("/employee/" + employee.getId())
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk());
  }

  @Test
  public void cannotDeleteEmployee_whenDeleteEmployeeWithInvalidId_thenStatus404()
    throws Exception {
    mvc
      .perform(delete("/employee/3").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }
}
