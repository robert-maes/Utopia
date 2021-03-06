package com.smoothstack.utopia.service;

import com.smoothstack.utopia.dao.FlightDao;
import com.smoothstack.utopia.entity.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightService {

  private ConnectionHelper connectionHelper = new ConnectionHelper();

  public List<Flight> getAllFlights() throws SQLException {
    //    List<Flight> flights = new ArrayList<Flight>();
    //    Flight flight1 = new Flight(
    //      0,
    //      new Route(
    //        0,
    //        new Airport("IAH", "Houston"),
    //        new Airport("LAX", "Los Angeles")
    //      ),
    //      new Airplane(0, new AirplaneType(0, 20)),
    //      LocalDateTime.now(),
    //      4,
    //      10.0f
    //    );
    //    Flight flight2 = new Flight(
    //      1,
    //      new Route(
    //        1,
    //        new Airport("LAX", "Los Angeles"),
    //        new Airport("IAH", "Houston")
    //      ),
    //      new Airplane(1, new AirplaneType(1, 200)),
    //      LocalDateTime.now(),
    //      42,
    //      25.0f
    //    );
    //    flights.add(flight1);
    //    flights.add(flight2);
    //    return fligh
    Connection connection = null;
    try {
      connection = connectionHelper.getConnection();
      FlightDao flightDao = new FlightDao(connection);
      List<Flight> flights = flightDao.getAllFlights();
      connection.commit();
      return flights;
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      connection.rollback();
    } finally {
      connection.close();
    }
    return new ArrayList<Flight>();
  }
}
