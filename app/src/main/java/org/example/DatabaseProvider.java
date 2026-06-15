package org.example;

import java.util.ArrayList;

/**
 * DatabaseProvider
 */

public interface DatabaseProvider {
  public ArrayList<String> ShowTables(String database);
  public void ShowCreateTable(String table);
  public void ShowInsertInto(String table);
}
