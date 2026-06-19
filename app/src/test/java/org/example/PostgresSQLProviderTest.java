package org.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.example.database.ConnectionProxy;
import org.example.database.DBCredentials;
import org.example.database.Provider;
import org.example.database.providers.PostgreSQLProvider;
import org.junit.jupiter.api.Test;

public class PostgresSQLProviderTest {

  Connection getDBConnection() throws SQLException {
    DBCredentials credentials = new DBCredentials("localhost", 5432, "dbuser", "dbpassword", "backupdb");
    return ConnectionProxy.getConnection(credentials, Provider.POSTGRESQL);
  }

  @Test
  void testShowCreateTable() throws SQLException {
    try (Connection conn = getDBConnection()) {
      assertNotNull(conn);
      PostgreSQLProvider provider = new PostgreSQLProvider(conn);

      String query = provider.ShowCreateTable("users");
      assertNotNull(query);
      System.out.println(query);
    }
  }

  @Test
  void testShowTables() throws SQLException {
    try (Connection conn = getDBConnection()) {
      assertNotNull(conn);

      PostgreSQLProvider provider = new PostgreSQLProvider(conn);
      ArrayList<String> tables = provider.ShowTables("backupdb");

      assertNotNull(tables);
    }
  }
}
