package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ConnectionProxy
 */
public class ConnectionProxy {
  static public Connection getConnection(DBCredentials credentials, String provider) throws SQLException {
    Provider dbProvider = Provider.fromString(provider);

    String connStr = switch (dbProvider) {
      case MYSQL -> "jdbc:%s://%s:%s@%s:%d/%s"
          .formatted(provider.toLowerCase(), credentials.username(), credentials.password(),
              credentials.host(), credentials.port(), credentials.dbName());

      case POSTGRESQL -> "jdbc:%s://%s:%s@%s:%d/%s"
          .formatted(provider.toLowerCase(), credentials.username(), credentials.password(),
              credentials.host(), credentials.port(), credentials.dbName());

      default -> "";
    };

    if (connStr == "") {
      throw new SQLException("Failed to connect: connection string is empty");
    }

    Connection conn = DriverManager.getConnection(connStr);
    return conn;
  }
}
