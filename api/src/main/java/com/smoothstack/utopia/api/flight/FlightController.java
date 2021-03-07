package com.smoothstack.utopia.api.flight;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
  public Flight getFlight(@PathVariable("flightId") Long flightId) {
    return flightService.getFlight(flightId);
  }

  @PostMapping(path = "{flightId}/seats")
  public void addSeatsToFlight(
    @PathVariable("flightId") Long flightId,
    @RequestBody AddSeatsInput addSeatsInput
  ) {
    flightService.addSeats(flightId, addSeatsInput);
  }

  @PutMapping(path = "{flightId}")
  public void updateFlight(
    @PathVariable("flightId") Long flightId,
    @RequestParam(required = false) String originAirport,
    @RequestParam(required = false) String originCity,
    @RequestParam(required = false) String destinationAirport,
    @RequestParam(required = false) String destinationCity,
    @RequestParam(required = false) String departureDate,
    @RequestParam(required = false) String departureTime
  ) {
    flightService.updateFlight(
      flightId,
      originAirport,
      originCity,
      destinationAirport,
      destinationCity,
      departureDate,
      departureTime
    );
  }
}
