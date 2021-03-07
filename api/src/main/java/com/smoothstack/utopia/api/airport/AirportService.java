package com.smoothstack.utopia.api.airport;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirportService {

  private final AirportRepository airportRepository;

  @Autowired
  public AirportService(AirportRepository airportRepository) {
    this.airportRepository = airportRepository;
  }

  public List<Airport> getAirports() {
    return airportRepository.findAll();
  }
}
