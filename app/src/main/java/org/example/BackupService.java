package org.example;

import java.util.ArrayList;
import java.nio.file.Path;
import org.example.database.DatabaseProvider;


public class BackupService {
  private DatabaseProvider dbProvider;
  private FileStore store;

  public BackupService(DatabaseProvider dbProvider, FileStore store) {
    this.dbProvider = dbProvider;
    this.store = store;
  }

  public void BackupAllTables(String dbName, Path path) {
    Path folderPath = this.store.CreateFolder("tables", path);
    if (folderPath == null) {
      System.out.println("Folder Path is null");
      return;
    }

    ArrayList<String> tables = this.dbProvider.ShowTables(dbName);
    for (String table : tables) {
      String createSt = this.dbProvider.ShowCreateTable(table);
      String insertSt = this.dbProvider.ShowInsertInto(table);

      String content = String.format("%s \n \n %s", createSt, insertSt);

      this.store.CreateSQLFile(folderPath, table, content);
    }
  }

}
