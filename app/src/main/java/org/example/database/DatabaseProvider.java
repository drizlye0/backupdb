package org.example.database;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DatabaseProvider {
  ArrayList<String> ShowTables(String database) throws SQLException;

  String ShowCreateTable(String table) throws SQLException;

  String ShowInsertInto(String table) throws SQLException;
}
