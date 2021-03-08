package com.smoothstack.utopia.api.airplane;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Rob Maes
 * Mon Mar 8 2021
 */
@Service
public class AirplaneService {

  private final AirplaneRepository airplaneRepository;

  @Autowired
  public AirplaneService(AirplaneRepository airplaneRepository) {
    this.airplaneRepository = airplaneRepository;
  }

  /**
   * Gets a list of all airplanes
   * @return A list of all airplanes
   */
  public List<Airplane> getAirplanes() {
    return this.airplaneRepository.findAll();
  }
}
