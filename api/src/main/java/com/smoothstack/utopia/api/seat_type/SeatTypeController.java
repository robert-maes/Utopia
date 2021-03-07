package com.smoothstack.utopia.api.seat_type;

import com.smoothstack.utopia.api.airplane.Airplane;
import com.smoothstack.utopia.api.airplane.AirplaneService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/seat_type")
public class SeatTypeController {

  private final SeatTypeService seatTypeService;

  @Autowired
  public SeatTypeController(SeatTypeService seatTypeService) {
    this.seatTypeService = seatTypeService;
  }

  @GetMapping
  public List<SeatType> getSeatTypes() {
    return seatTypeService.getSeatTypes();
  }
}
