package com.smoothstack.utopia.api;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

  @Bean
  public Hibernate5Module hibernate5Module() {
    return new Hibernate5Module();
  }
}
