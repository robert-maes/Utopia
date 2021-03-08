package com.smoothstack.utopia.api;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Rob Maes
 * Mon Mar 8
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  // This prevents CORS errors for the JS frontend
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("*"));
    configuration.setAllowedMethods(
      List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
    );
    configuration.setAllowCredentials(true);
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setExposedHeaders(
      List.of(
        "X-Auth-Token",
        "Authorization",
        "Access-Control-Allow-Origin",
        "Access-Control-Allow-Credentials"
      )
    );
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  // This was added due to some annoying JSON serialization bugs and may or may not be needed now
  @Bean
  public Module hibernate5Module() {
    return new Hibernate5Module();
  }
}
