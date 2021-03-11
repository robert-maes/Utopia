package com.smoothstack.utopia.api.service;

import com.smoothstack.utopia.api.dao.AirportDao;
import com.smoothstack.utopia.api.dto.CreateAirportDto;
import com.smoothstack.utopia.api.dto.UpdateAirportDto;
import com.smoothstack.utopia.api.exception.AirportNotFoundException;
import com.smoothstack.utopia.api.exception.DuplicateAirportException;
import com.smoothstack.utopia.api.model.Airport;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirportService {

  private final AirportDao airportDao;

  @Autowired
  public AirportService(AirportDao airportDao) {
    this.airportDao = airportDao;
  }

  public List<Airport> getAllAirports() {
    return airportDao.findAll();
  }

  public Airport getAirport(String airportId) {
    Optional<Airport> airportOptional = airportDao.findAirportByIataId(
      airportId
    );
    if (airportOptional.isEmpty()) {
      throw new AirportNotFoundException();
    }
    return airportOptional.get();
  }

  public void createAirport(CreateAirportDto createAirportDto) {
    Optional<Airport> airportOptional = airportDao.findAirportByIataId(
      createAirportDto.getId()
    );
    if (airportOptional.isPresent()) {
      throw new DuplicateAirportException();
    }
    Airport airport = new Airport(
      createAirportDto.getId(),
      createAirportDto.getCity()
    );
    airportDao.save(airport);
  }

  @Transactional
  public void updateAirport(
    String airportId,
    UpdateAirportDto updateAirportDto
  ) {
    Optional<Airport> airportToUpdateOptional = airportDao.findAirportByIataId(
      airportId
    );
    if (airportToUpdateOptional.isEmpty()) {
      throw new AirportNotFoundException();
    }
    Airport airportToUpdate = airportToUpdateOptional.get();
    if (updateAirportDto.getId().isPresent()) {
      String newAirportId = updateAirportDto.getId().get();
      if (!newAirportId.equals(airportToUpdate.getIataId())) {
        Optional<Airport> newIdAirportOptional = airportDao.findAirportByIataId(
          newAirportId
        );
        if (newIdAirportOptional.isPresent()) {
          throw new DuplicateAirportException();
        }
      }
      airportToUpdate.setIataId(newAirportId);
    }
    if (updateAirportDto.getCity().isPresent()) {
      String newAirportCity = updateAirportDto.getCity().get();
      airportToUpdate.setCity(newAirportCity);
    }
    airportDao.save(airportToUpdate);
  }

  public void deleteAirport(String airportId) {
    Optional<Airport> airportOptional = airportDao.findAirportByIataId(
      airportId
    );
    if (airportOptional.isEmpty()) {
      throw new AirportNotFoundException();
    }
    airportDao.delete(airportOptional.get());
  }
}
