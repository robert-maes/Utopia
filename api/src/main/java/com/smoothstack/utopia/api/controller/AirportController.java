package com.smoothstack.utopia.api.controller;

import com.smoothstack.utopia.api.dto.CreateAirportDto;
import com.smoothstack.utopia.api.dto.UpdateAirportDto;
import com.smoothstack.utopia.api.model.Airport;
import com.smoothstack.utopia.api.service.AirportService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/airport")
public class AirportController {

  private final AirportService airportService;

  @Autowired
  public AirportController(AirportService airportService) {
    this.airportService = airportService;
  }

  @GetMapping
  public List<Airport> getAllAirports() {
    return airportService.getAllAirports();
  }

  @GetMapping(path = "{airportId}")
  public Airport getAirport(@PathVariable("airportId") String airportId) {
    return airportService.getAirport(airportId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createAirport(
    @Valid @RequestBody CreateAirportDto createAirportDto
  ) {
    airportService.createAirport(createAirportDto);
  }

  @PutMapping(path = "{airportId}")
  public void updateAirport(
    @PathVariable("airportId") String airportId,
    @Valid @RequestBody UpdateAirportDto updateAirportDto
  ) {
    airportService.updateAirport(airportId, updateAirportDto);
  }

  @DeleteMapping(path = "{airportId}")
  public void deleteAirport(@PathVariable("airportId") String airportId) {
    airportService.deleteAirport(airportId);
  }
}
