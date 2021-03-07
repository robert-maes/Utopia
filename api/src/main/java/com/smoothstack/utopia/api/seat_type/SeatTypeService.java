package com.smoothstack.utopia.api.seat_type;

import com.smoothstack.utopia.api.airplane.Airplane;
import com.smoothstack.utopia.api.airplane.AirplaneRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatTypeService {

  private final SeatTypeRepository seatTypeRepository;

  @Autowired
  public SeatTypeService(SeatTypeRepository seatTypeRepository) {
    this.seatTypeRepository = seatTypeRepository;
  }

  public List<SeatType> getSeatTypes() {
    return this.seatTypeRepository.findAll();
  }
}
