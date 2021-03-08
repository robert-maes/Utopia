package com.smoothstack.utopia.api.airport;

import com.smoothstack.utopia.api.CustomException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rob Maes
 * Mon Mar 8 2021
 */
@CrossOrigin(origins = "*")
@RestController
@Validated
@RequestMapping(path = "/airport")
public class AirportController {

  private final AirportService airportService;

  @Autowired
  public AirportController(AirportService airportService) {
    this.airportService = airportService;
  }

  /**
   * Gets a list of all airports
   * @return A list of all airports in JSON
   */
  @GetMapping
  public List<Airport> getAirports() {
    return airportService.getAirports();
  }

  /**
   * Gets the airport with the given Id
   * @param airportId The 3 character IATA Id
   * @return The specified airport in JSON if it exists
   * @throws CustomException
   */
  @GetMapping(path = "{airportId}")
  public Airport getAirport(@PathVariable("airportId") String airportId)
    throws CustomException {
    return airportService.getAirport(airportId);
  }

  /**
   * Creates a new airport
   * @param airportForm A POJO DTO for creating an airport
   * @throws CustomException
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createAirport(@Valid @RequestBody AirportForm airportForm)
    throws CustomException {
    airportService.addNewAirport(airportForm);
  }

  /**
   * Deletes the airport with the given Id
   * @param airportId The 3 character IATA Id
   * @throws CustomException
   */
  @DeleteMapping(path = "{airportId}")
  public void deleteAirport(@PathVariable("airportId") String airportId)
    throws CustomException {
    airportService.deleteAirport(airportId);
  }

  /**
   * Updates an existing airport
   * @param airportId The 3 character IATA Id
   * @param airportForm A POJO DTO for updating the airport
   * @throws CustomException
   */
  @PutMapping(path = "{airportId}")
  public void updateAirport(
    @PathVariable("airportId") String airportId,
    @Valid @RequestBody AirportForm airportForm
  ) throws CustomException {
    airportService.updateAirport(airportId, airportForm);
  }
}
