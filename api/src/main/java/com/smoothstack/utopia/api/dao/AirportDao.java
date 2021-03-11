package com.smoothstack.utopia.api.dao;

import com.smoothstack.utopia.api.model.Airport;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
@Repository
public interface AirportDao extends JpaRepository<Airport, String> {
  public Optional<Airport> findAirportByIataId(String iataId);
}
