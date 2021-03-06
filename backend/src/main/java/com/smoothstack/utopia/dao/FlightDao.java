package com.smoothstack.utopia.dao;

import com.smoothstack.utopia.entity.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightDao extends BaseDao<Flight> {

  public FlightDao(Connection connection) {
    super(connection);
  }

  public List<Flight> getAllFlights()
    throws ClassNotFoundException, SQLException {
    return readAll("SELECT * FROM flight");
  }

  @Override
  public List<Flight> extractData(ResultSet resultSet) throws SQLException {
    List<Flight> flights = new ArrayList<>();
    while (resultSet.next()) {
      int id = resultSet.getInt("id");
      LocalDateTime departureTime = resultSet
        .getTimestamp("departure_time")
        .toLocalDateTime();
      int reservedSeats = resultSet.getInt("reserved_seats");
      float seatPrice = resultSet.getFloat("seat_price");
      Flight flight = new Flight(
        id,
        new Route(
          1,
          new Airport("LAX", "Los Angeles"),
          new Airport("IAH", "Houston")
        ),
        new Airplane(1, new AirplaneType(1, 200)),
        departureTime,
        reservedSeats,
        seatPrice
      );
      flights.add(flight);
    }
    return flights;
  }
}
