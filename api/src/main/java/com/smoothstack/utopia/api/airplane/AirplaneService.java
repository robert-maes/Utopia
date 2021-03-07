package com.smoothstack.utopia.api.airplane;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirplaneService {

  private final AirplaneRepository airplaneRepository;

  @Autowired
  public AirplaneService(AirplaneRepository airplaneRepository) {
    this.airplaneRepository = airplaneRepository;
  }

  public List<Airplane> getAirplanes() {
    return this.airplaneRepository.findAll();
  }
}
