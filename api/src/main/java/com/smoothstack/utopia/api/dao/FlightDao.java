package com.smoothstack.utopia.api.dao;

import com.smoothstack.utopia.api.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightDao extends JpaRepository<Flight, Long> {}
