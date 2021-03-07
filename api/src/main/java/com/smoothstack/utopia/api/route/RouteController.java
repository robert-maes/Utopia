package com.smoothstack.utopia.api.route;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/route")
public class RouteController {

  private final RouteService routeService;

  @Autowired
  public RouteController(RouteService routeService) {
    this.routeService = routeService;
  }

  @GetMapping
  public List<Route> getRoutes() {
    return routeService.getRoutes();
  }
}
