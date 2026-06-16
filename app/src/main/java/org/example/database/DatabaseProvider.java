package org.example.database;

import java.util.ArrayList;

/**
 * DatabaseProvider
 */

public interface DatabaseProvider {
  public ArrayList<String> ShowTables(String database);

  public String ShowCreateTable(String table);

  public String ShowInsertInto(String table);
}
