package org.example.database;

/**
 * DBProvider
 */
public enum Provider {
  MYSQL,
  POSTGRESQL,
  SQLITE,
  UNKNOWN_DB;

  public static Provider fromString(String value) {
    return switch (value.toLowerCase()) {
      case "mysql" -> MYSQL;
      case "postgresql" -> POSTGRESQL;
      case "postgres" -> POSTGRESQL;
      case "sqlite" -> SQLITE;
      default -> UNKNOWN_DB;
    };
  }
}
