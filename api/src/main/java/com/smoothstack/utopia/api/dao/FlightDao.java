package com.smoothstack.utopia.api.dao;

import com.smoothstack.utopia.api.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rob Maes
 * Mar 11 2021
 */
@Repository
public interface FlightDao extends JpaRepository<Flight, Long> {}
