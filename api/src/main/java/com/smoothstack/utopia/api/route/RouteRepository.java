package com.smoothstack.utopia.api.route;

import com.smoothstack.utopia.api.airport.Airport;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
  List<Route> findRoutesByOrigin(Airport origin);
  List<Route> findRoutesByDestination(Airport destination);
  Optional<Route> findRouteByOriginAndDestination(
    Airport origin,
    Airport destination
  );
}
