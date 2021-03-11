package com.smoothstack.utopia.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Utils {

  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static boolean initialized = false;

  private Utils() {}

  private static void init() {
    objectMapper.registerModule(new Jdk8Module());
    objectMapper.registerModule(new JavaTimeModule());
    initialized = true;
  }

  public static String asJsonString(final Object obj) {
    try {
      if (!initialized) init();
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
