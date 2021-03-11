package com.smoothstack.utopia.api.controller;

import com.smoothstack.utopia.api.dto.CreateFlightDto;
import com.smoothstack.utopia.api.dto.UpdateFlightDto;
import com.smoothstack.utopia.api.model.Flight;
import com.smoothstack.utopia.api.service.FlightService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/flight")
public class FlightController {

  private final FlightService flightService;

  @Autowired
  public FlightController(FlightService flightService) {
    this.flightService = flightService;
  }

  @GetMapping
  public List<Flight> getAllFlights() {
    return flightService.getAllFlights();
  }

  @GetMapping(path = "{flightId}")
  public Flight getFlight(@PathVariable("flightId") Long flightId) {
    return flightService.getFlight(flightId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createFlight(
    @Valid @RequestBody CreateFlightDto createFlightDto
  ) {
    flightService.createFlight(createFlightDto);
  }

  @PutMapping(path = "{flightId}")
  public void updateFlight(
    @PathVariable("flightId") Long flightId,
    @Valid @RequestBody UpdateFlightDto updateFlightDto
  ) {
    flightService.updateFlight(flightId, updateFlightDto);
  }

  @DeleteMapping(path = "{flightId}")
  public void deleteFlight(@PathVariable("flightId") Long flightId) {
    flightService.deleteFlight(flightId);
  }
}
