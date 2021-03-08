package com.smoothstack.utopia.api.airport;

import com.smoothstack.utopia.api.CustomException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping
  public List<Airport> getAirports() {
    return airportService.getAirports();
  }

  @GetMapping(path = "{airportId}")
  public Airport getAirport(@PathVariable("airportId") String airportId)
    throws CustomException {
    return airportService.getAirport(airportId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createAirport(@Valid @RequestBody AirportForm airportForm)
    throws CustomException {
    airportService.addNewAirport(airportForm);
  }

  @DeleteMapping(path = "{airportId}")
  public void deleteAirport(@PathVariable("airportId") String airportId)
    throws CustomException {
    airportService.deleteAirport(airportId);
  }

  @PutMapping(path = "{airportId}")
  public void updateAirport(
    @PathVariable("airportId") String airportId,
    @Valid @RequestBody AirportForm airportForm
  ) throws CustomException {
    airportService.updateAirport(airportId, airportForm);
  }
}
