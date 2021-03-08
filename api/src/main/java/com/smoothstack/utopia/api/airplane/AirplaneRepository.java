package com.smoothstack.utopia.api.airplane;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rob Maes
 * Mon Mar 8 2021
 */
@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {}
