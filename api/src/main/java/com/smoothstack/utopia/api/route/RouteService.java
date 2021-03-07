package com.smoothstack.utopia.api.route;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

  private final RouteRepository routeRepository;

  @Autowired
  public RouteService(RouteRepository routeRepository) {
    this.routeRepository = routeRepository;
  }

  public List<Route> getRoutes() {
    return routeRepository.findAll();
  }
}
