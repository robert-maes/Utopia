package com.smoothstack.utopia.api.airplane_type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rob Maes
 * Mon Mar 8 2021
 */
@Repository
public interface AirplaneTypeRepository
  extends JpaRepository<AirplaneType, Long> {}
