package com.smoothstack.utopia.api.airplane;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rob Maes
 * Mon Mar 8 2021
 */
@CrossOrigin(origins = "*")
@RestController
@Validated
@RequestMapping(path = "/airplane")
public class AirplaneController {

  private final AirplaneService airplaneService;

  @Autowired
  public AirplaneController(AirplaneService airplaneService) {
    this.airplaneService = airplaneService;
  }

  /**
   * Gets all airplanes
   * @return A list of airplanes in JSON
   */
  @GetMapping
  public List<Airplane> getAirplanes() {
    return airplaneService.getAirplanes();
  }
}
