package com.smoothstack.utopia.api.controller;

import com.smoothstack.utopia.api.dto.CreateTravelerDto;
import com.smoothstack.utopia.api.dto.UpdateTravelerDto;
import com.smoothstack.utopia.api.model.Traveler;
import com.smoothstack.utopia.api.service.TravelerService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/traveler")
public class TravelerController {

  private final TravelerService travelerService;

  @Autowired
  public TravelerController(TravelerService travelerService) {
    this.travelerService = travelerService;
  }

  @GetMapping
  public List<Traveler> getAllTravelers() {
    return travelerService.getAllTravelers();
  }

  @GetMapping(path = "{travelerId}")
  public Traveler getTraveler(@PathVariable("travelerId") Long travelerId) {
    return travelerService.getTraveler(travelerId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createTraveler(
    @Valid @RequestBody CreateTravelerDto createTravelerDto
  ) {
    travelerService.createTraveler(createTravelerDto);
  }

  @PutMapping(path = "{travelerId}")
  public void updateTraveler(
    @PathVariable("travelerId") Long travelerId,
    @Valid @RequestBody UpdateTravelerDto updateTravelerDto
  ) {
    travelerService.updateTraveler(travelerId, updateTravelerDto);
  }

  @DeleteMapping(path = "{travelerId}")
  public void deleteTraveler(@PathVariable("travelerId") Long travelerId) {
    travelerService.deleteTraveler(travelerId);
  }
}
