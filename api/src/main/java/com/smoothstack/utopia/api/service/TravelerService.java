package com.smoothstack.utopia.api.service;

import com.smoothstack.utopia.api.dao.TicketDao;
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

/**
 * @author Rob Maes
 * Mar 11 2021
 */
@Service
public class TravelerService {

  private final TravelerDao travelerDao;
  private final TicketDao ticketDao;

  @Autowired
  public TravelerService(TravelerDao travelerDao, TicketDao ticketDao) {
    this.travelerDao = travelerDao;
    this.ticketDao = ticketDao;
  }

  public List<Traveler> getAllTravelers() {
    return travelerDao.findAll();
  }

  public Traveler getTraveler(Long travelerId) {
    // try to find the requested traveler
    Optional<Traveler> travelerOptional = travelerDao.findById(travelerId);
    // if the traveler does not exist, throw a 404
    if (travelerOptional.isEmpty()) {
      throw new TravelerNotFoundException();
    }
    // otherwise, return the requested traveler
    return travelerOptional.get();
  }

  public void createTraveler(CreateTravelerDto createTravelerDto) {
    // create a new traveler from the provided data
    Traveler traveler = new Traveler();
    traveler.setAddress(createTravelerDto.getAddress());
    traveler.setFamilyName(createTravelerDto.getFamilyName());
    traveler.setGivenName(createTravelerDto.getGivenName());
    traveler.setGender(createTravelerDto.getGender());
    // transform birthday string into LocalDate object
    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd"
    );
    final LocalDate birthDate = LocalDate.parse(
      createTravelerDto.getDateOfBirth(),
      dateTimeFormatter
    );
    traveler.setDateOfBirth(birthDate);
    // save the new traveler
    travelerDao.save(traveler);
  }

  @Transactional
  public void updateTraveler(
    Long travelerId,
    UpdateTravelerDto updateTravelerDto
  ) {
    // try to find the traveler to update
    Optional<Traveler> travelerToUpdateOptional = travelerDao.findById(
      travelerId
    );
    // if the traveler does not exist, throw a new 404
    if (travelerToUpdateOptional.isEmpty()) {
      throw new TravelerNotFoundException();
    }
    Traveler travelerToUpdate = travelerToUpdateOptional.get();
    // update the traveler
    if (updateTravelerDto.getAddress().isPresent()) {
      travelerToUpdate.setAddress(updateTravelerDto.getAddress().get());
    }
    if (updateTravelerDto.getFamilyName().isPresent()) {
      travelerToUpdate.setFamilyName(updateTravelerDto.getFamilyName().get());
    }
    if (updateTravelerDto.getGivenName().isPresent()) {
      travelerToUpdate.setGivenName(updateTravelerDto.getGivenName().get());
    }
    if (updateTravelerDto.getGender().isPresent()) {
      travelerToUpdate.setGender(updateTravelerDto.getGender().get());
    }
    if (updateTravelerDto.getDateOfBirth().isPresent()) {
      // transform the provided birthday string into a LocalDate
      final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd"
      );
      final LocalDate birthDate = LocalDate.parse(
        updateTravelerDto.getDateOfBirth().get(),
        dateTimeFormatter
      );
      travelerToUpdate.setDateOfBirth(birthDate);
    }
    // save the updated traveler
    travelerDao.save(travelerToUpdate);
  }

  @Transactional
  public void deleteTraveler(Long travelerId) {
    // try to find the traveler to delete
    Optional<Traveler> travelerOptional = travelerDao.findById(travelerId);
    // if the traveler does not exist, throw a 404
    if (travelerOptional.isEmpty()) {
      throw new TravelerNotFoundException();
    }
    Traveler traveler = travelerOptional.get();
    // if the traveler has tickets
    if (!traveler.getTickets().isEmpty()) {
      // delete each ticket
      traveler.getTickets().forEach(ticketDao::delete);
    }
    // delete the traveler
    travelerDao.delete(traveler);
  }
}
