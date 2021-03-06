package com.smoothstack.utopia.api.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

  @Bean
  CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
    return args -> {
      Student rob = new Student(
        1L,
        "Rob Maes",
        "remilmaes@gmail.com",
        LocalDate.of(1996, Month.OCTOBER, 8)
      );
      Student john = new Student(
        "John Smith",
        "johnsmith@example.com",
        LocalDate.of(1980, Month.MARCH, 6)
      );
      studentRepository.saveAll(List.of(rob, john));
    };
  }
}
