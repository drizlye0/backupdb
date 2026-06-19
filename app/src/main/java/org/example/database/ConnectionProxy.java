package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ConnectionProxy
 */
public class ConnectionProxy {
  static public Connection getConnection(DBCredentials credentials, Provider provider) throws SQLException {
    return switch (provider) {
      case POSTGRESQL -> getPostgresSQLConnection(credentials);
      case MYSQL -> getMySQLConnection(credentials);
      case SQLITE -> null;
      default -> throw new SQLException("Invalid DB");
    };
  }

  private static Connection getPostgresSQLConnection(DBCredentials credentials) throws SQLException {
    String url = String.format("jdbc:postgresql://%s:%d/%s", credentials.host(), credentials.port(),
        credentials.dbName());

    return DriverManager.getConnection(url, credentials.username(), credentials.password());
  }

  private static Connection getMySQLConnection(DBCredentials credentials) throws SQLException {
    String url = String.format("jdbc:mysql://%s:%d/%s", credentials.host(), credentials.port(),
        credentials.dbName());

    return DriverManager.getConnection(url, credentials.username(), credentials.password());
  }
}
