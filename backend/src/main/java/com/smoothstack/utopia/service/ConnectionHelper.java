package com.smoothstack.utopia.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionHelper {

  private final String DB_PROPERTIES_PATH = "database.properties";

  private String getProperty(String propertyName) {
    try (
      InputStream file = getClass()
        .getClassLoader()
        .getResourceAsStream(DB_PROPERTIES_PATH)
    ) {
      Properties dbProperties = new Properties();
      dbProperties.load(file);
      return dbProperties.getProperty(propertyName);
    } catch (FileNotFoundException e) {
      System.out.println(
        "Could not locate database properties file at: " + DB_PROPERTIES_PATH
      );
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Connection getConnection()
    throws ClassNotFoundException, SQLException {
    Class.forName(getProperty("driver"));
    Connection connection = DriverManager.getConnection(
      getProperty("url"),
      getProperty("username"),
      getProperty("password")
    );
    connection.setAutoCommit(false);
    return connection;
  }
}
