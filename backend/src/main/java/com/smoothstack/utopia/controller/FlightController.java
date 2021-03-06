package com.smoothstack.utopia.controller;

import com.google.gson.Gson;
import com.smoothstack.utopia.entity.AirplaneType;
import com.smoothstack.utopia.entity.Flight;
import com.smoothstack.utopia.service.FlightService;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
  name = "FlightController",
  urlPatterns = { "/flight/*", "/flights" }
)
public class FlightController extends HttpServlet {

  private FlightService flightService = new FlightService();

  @Override
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    String[] uriArray = UriHelper.parseUri(request);
    switch (uriArray[0]) {
      case "flights":
        List<Flight> allFlights = null;
        try {
          allFlights = flightService.getAllFlights();
        } catch (SQLException e) {
          response.setStatus(500);
          return;
        }
        try {
          JsonHelper.respondWithJson(response, allFlights);
        } catch (IOException e) {
          response.setStatus(500);
        }
        break;
      case "flight":
        break;
    }
  }
}
