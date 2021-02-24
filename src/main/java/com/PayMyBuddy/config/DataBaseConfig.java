package com.PayMyBuddy.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataBaseConfig {

  private static final Logger logger = LogManager.getLogger("DataBaseConfig");

  /**
   * connect to the database.
   * 
   */
  public Connection getConnection() throws ClassNotFoundException, SQLException {
    ResourceBundle rb = ResourceBundle.getBundle("data");
    String user = rb.getString("username");
    String pass = rb.getString("password");
    String port = rb.getString("port");
    System.out.println("");
    logger.info("Create DB connection");
    Class.forName("com.postgresql.Driver");
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:" + port + "/prod", user, pass);
  }

}
