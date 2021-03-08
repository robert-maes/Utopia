package com.smoothstack.utopia.api.airplane;

import com.smoothstack.utopia.api.route.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {}
