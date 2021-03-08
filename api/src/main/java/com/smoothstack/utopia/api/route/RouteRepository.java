package com.smoothstack.utopia.api.route;

import com.smoothstack.utopia.api.airport.Airport;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rob Maes
 * Mon Mar 8 2021
 */
@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
  /**
   * Returns a list of routes that begin at the given airport
   * @param origin The starting airport
   * @return A list of routes that start at the origin airport
   */
  List<Route> findRoutesByOrigin(Airport origin);

  /**
   * Returns a list of routes that end at the given airport
   * @param destination The ending airport
   * @return A list of routes that end at the destination airport
   */
  List<Route> findRoutesByDestination(Airport destination);

  /**
   * Returns an optional Route that starts at origin and ends at destination
   * @param origin The starting airport
   * @param destination The ending airport
   * @return An optional route that starts at origin and ends at destination
   */
  Optional<Route> findRouteByOriginAndDestination(
    Airport origin,
    Airport destination
  );
}
