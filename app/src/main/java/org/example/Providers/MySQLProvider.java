package org.example.Providers;

import java.sql.Statement;
import java.sql.Connection;

import org.example.DatabaseProvider;

/**
 * MySQLProvider
 */
public class MySQLProvider implements DatabaseProvider {
  private Connection db;

  public MySQLProvider(Connection db) {
    this.db = db;
  }

  public void ShowAllTables() {
    try {
      Statement st = db.createStatement();
    } catch (Exception e) {
    }
  }
}
