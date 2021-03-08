package com.smoothstack.utopia.api.airplane;

import com.smoothstack.utopia.api.airport.Airport;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @GetMapping
  public List<Airplane> getAirplanes() {
    return airplaneService.getAirplanes();
  }
}
