package com.smoothstack.utopia.api.flight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rob Maes
 * Mon Mar 8 2021
 */
@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {}
