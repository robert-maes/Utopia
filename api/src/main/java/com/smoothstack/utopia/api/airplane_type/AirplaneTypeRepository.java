package com.smoothstack.utopia.api.airplane_type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplaneTypeRepository
  extends JpaRepository<AirplaneType, Long> {}
