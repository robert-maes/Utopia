package com.smoothstack.utopia.api.service;

import com.smoothstack.utopia.api.dao.TravelerDao;
import com.smoothstack.utopia.api.dto.CreateTravelerDto;
import com.smoothstack.utopia.api.dto.UpdateTravelerDto;
import com.smoothstack.utopia.api.exception.TravelerNotFoundException;
import com.smoothstack.utopia.api.model.Traveler;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelerService {

  private final TravelerDao travelerDao;

  @Autowired
  public TravelerService(TravelerDao travelerDao) {
    this.travelerDao = travelerDao;
  }

  public List<Traveler> getAllTravelers() {
    return travelerDao.findAll();
  }

  public Traveler getTraveler(Long travelerId) {
    Optional<Traveler> travelerOptional = travelerDao.findById(travelerId);
    if (travelerOptional.isEmpty()) {
      throw new TravelerNotFoundException();
    }
    return travelerOptional.get();
  }

  public void createTraveler(CreateTravelerDto createTravelerDto) {
    Traveler traveler = new Traveler();
    traveler.setAddress(createTravelerDto.getAddress());
    traveler.setFamilyName(createTravelerDto.getFamilyName());
    traveler.setGivenName(createTravelerDto.getGivenName());
    traveler.setGender(createTravelerDto.getGender());
    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd"
    );
    final LocalDate birthDate = LocalDate.parse(
      createTravelerDto.getDateOfBirth(),
      dateTimeFormatter
    );
    traveler.setDateOfBirth(birthDate);
    travelerDao.save(traveler);
  }

  @Transactional
  public void updateTraveler(
    Long travelerId,
    UpdateTravelerDto updateTravelerDto
  ) {
    Optional<Traveler> travelerToUpdateOptional = travelerDao.findById(
      travelerId
    );
    if (travelerToUpdateOptional.isEmpty()) {
      throw new TravelerNotFoundException();
    }
    Traveler travelerToUpdate = travelerToUpdateOptional.get();
    if (updateTravelerDto.getAddress().isPresent()) {
      travelerToUpdate.setAddress(updateTravelerDto.getAddress().get());
    }
    if (updateTravelerDto.getFamilyName().isPresent()) {
      travelerToUpdate.setFamilyName(updateTravelerDto.getFamilyName().get());
    }
    if (updateTravelerDto.getGivenName().isPresent()) {
      travelerToUpdate.setGivenName(updateTravelerDto.getGivenName().get());
    }
    if (updateTravelerDto.getGivenName().isPresent()) {
      travelerToUpdate.setGender(updateTravelerDto.getGender().get());
    }
    if (updateTravelerDto.getDateOfBirth().isPresent()) {
      final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd"
      );
      final LocalDate birthDate = LocalDate.parse(
        updateTravelerDto.getDateOfBirth().get(),
        dateTimeFormatter
      );
      travelerToUpdate.setDateOfBirth(birthDate);
    }
    travelerDao.save(travelerToUpdate);
  }

  public void deleteTraveler(Long travelerId) {
    Optional<Traveler> travelerOptional = travelerDao.findById(travelerId);
    if (travelerOptional.isEmpty()) {
      throw new TravelerNotFoundException();
    }
    travelerDao.delete(travelerOptional.get());
  }
}
