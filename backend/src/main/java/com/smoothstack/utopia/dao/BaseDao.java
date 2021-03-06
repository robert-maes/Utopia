package com.smoothstack.utopia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDao<T> {

  protected Connection connection = null;

  public BaseDao(Connection connection) {
    this.connection = connection;
  }

  public List<T> readAll(String sql)
    throws SQLException, ClassNotFoundException {
    PreparedStatement statement = connection.prepareStatement(sql);
    return extractData(statement.executeQuery());
  }

  public abstract List<T> extractData(ResultSet results)
    throws SQLException, ClassNotFoundException;
}
