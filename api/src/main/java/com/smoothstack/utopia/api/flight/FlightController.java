package com.smoothstack.utopia.api.flight;

import com.smoothstack.utopia.api.CustomException;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/flight")
public class FlightController {

  private final FlightService flightService;

  @Autowired
  public FlightController(FlightService flightService) {
    this.flightService = flightService;
  }

  @GetMapping
  public List<Flight> getFlights() {
    return flightService.getFlights();
  }

  @GetMapping(path = "{flightId}")
  public Flight getFlight(@PathVariable("flightId") Long flightId)
    throws CustomException {
    return flightService.getFlight(flightId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createFlight(@Valid @RequestBody FlightForm flightForm)
    throws CustomException {
    flightService.addNewFlight(flightForm);
  }

  @DeleteMapping(path = "{flightId}")
  public void deleteFlight(@PathVariable("flightId") Long flightId)
    throws CustomException {
    flightService.deleteFlight(flightId);
  }

  @PutMapping(path = "{flightId")
  public void updateFlight() throws CustomException {}
}
