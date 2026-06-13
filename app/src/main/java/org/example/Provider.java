package org.example;

/**
 * DBProvider
 */
public enum Provider {
  MYSQL,
  POSTGRESQL,
  SQLITE;

  public static Provider fromString(String value) {
    return switch (value.toLowerCase()) {
      case "mysql" -> MYSQL;
      case "postgresql" -> POSTGRESQL;
      case "sqlite" -> SQLITE;
      default -> throw new IllegalArgumentException("Unknow DB: " + value);
    };
  }
}
