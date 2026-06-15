package org.example;

import java.nio.file.Path;

public interface FileStore {
  void CreateSQLFile(Path path, String name, String content);

  Path CreateFolder(String name, Path path);

  void UpdateSQLFile(String name);
}
