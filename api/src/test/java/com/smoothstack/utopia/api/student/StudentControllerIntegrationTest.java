//package com.smoothstack.utopia.api.student;
//
//import static org.hamcrest.Matchers.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import java.time.LocalDate;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestPropertySource(
//  locations = "classpath:application-integrationtest.properties"
//)
//public class StudentControllerIntegrationTest {
//
//  @Autowired
//  private MockMvc mvc;
//
//  @Autowired
//  private StudentRepository repository;
//
//  private void createTestStudent(String name) {
//    Student student = new Student(name, name + "@example.com", LocalDate.now());
//    repository.save(student);
//  }
//
//  @Test
//  public void givenStudents_whenGetStudents_thenStatus200() throws Exception {
//    createTestStudent("joe");
//    mvc
//      .perform(get("/api/v1/student").contentType(MediaType.APPLICATION_JSON))
//      .andExpect(status().isOk())
//      .andExpect(
//        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
//      )
//      .andExpect(jsonPath("$[2].name", is("joe")));
//  }
//}
