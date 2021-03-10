package com.smoothstack.utopia.api.controller;

import com.smoothstack.utopia.api.dto.CreateSeatDto;
import com.smoothstack.utopia.api.dto.UpdateSeatDto;
import com.smoothstack.utopia.api.model.Seat;
import com.smoothstack.utopia.api.service.SeatService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/seat")
public class SeatController {

  private final SeatService seatService;

  @Autowired
  public SeatController(SeatService seatService) {
    this.seatService = seatService;
  }

  @GetMapping
  public List<Seat> getAllSeats() {
    return seatService.getAllSeats();
  }

  @GetMapping(path = "{seatId}")
  public Seat getSeat(@PathVariable("seatId") Long seatId) {
    return seatService.getSeat(seatId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createSeat(@Valid @RequestBody CreateSeatDto createSeatDto) {
    seatService.createSeat(createSeatDto);
  }

  @PutMapping(path = "{seatId}")
  public void updateSeat(
    @PathVariable("seatId") Long seatId,
    @Valid @RequestBody UpdateSeatDto updateSeatDto
  ) {
    seatService.updateSeat(seatId, updateSeatDto);
  }

  @DeleteMapping(path = "{seatId}")
  public void deleteSeat(@PathVariable("seatId") Long seatId) {
    seatService.deleteSeat(seatId);
  }
}
