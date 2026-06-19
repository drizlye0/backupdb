package org.example;

import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import org.example.database.DatabaseProvider;

public class BackupService {
  private DatabaseProvider dbProvider;
  private FileStore store;

  public BackupService(DatabaseProvider dbProvider, FileStore store) {
    this.dbProvider = dbProvider;
    this.store = store;
  }

  public int BackupAllTables(String dbName, Path path) {
    try {
      Path folderPath = this.store.CreateFolder("tables", path);

      ArrayList<String> tables = this.dbProvider.ShowTables(dbName);

      for (String table : tables) {
        String createSt = this.dbProvider.ShowCreateTable(table);

        String content = String.format("%s", createSt);

        this.store.CreateSQLFile(folderPath, table, content);
      }
    } catch (IOException | SQLException e) {
      System.err.println("Failed to backup tables. " + e.getMessage());
      return 1;
    }

    return 0;
  }

}
