package com.smoothstack.utopia.api.flight;

import com.smoothstack.utopia.api.CustomException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rob Maes
 * Mon Mar 8 2021
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/flight")
public class FlightController {

  private final FlightService flightService;

  @Autowired
  public FlightController(FlightService flightService) {
    this.flightService = flightService;
  }

  /**
   * Gets a list of all flights
   * @return A list of all flights in JSON
   */
  @GetMapping
  public List<Flight> getFlights() {
    return flightService.getFlights();
  }

  /**
   * Gets a specific flight
   * @param flightId The Id of the flight to get
   * @return The specified flight
   * @throws CustomException
   */
  @GetMapping(path = "{flightId}")
  public Flight getFlight(@PathVariable("flightId") Long flightId)
    throws CustomException {
    return flightService.getFlight(flightId);
  }

  /**
   * Creates a new flight
   * @param flightForm A POJO DTO for creating a new flight
   * @throws CustomException
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createFlight(@Valid @RequestBody FlightForm flightForm)
    throws CustomException {
    flightService.addNewFlight(flightForm);
  }

  /**
   * Deletes the specified flight
   * @param flightId The Id of the flight to delete
   * @throws CustomException
   */
  @DeleteMapping(path = "{flightId}")
  public void deleteFlight(@PathVariable("flightId") Long flightId)
    throws CustomException {
    flightService.deleteFlight(flightId);
  }

  @PutMapping(path = "{flightId")
  public void updateFlight() throws CustomException {}
}
