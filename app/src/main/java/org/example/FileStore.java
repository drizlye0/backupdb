package org.example;

import java.io.IOException;
import java.nio.file.Path;

public interface FileStore {
  void CreateSQLFile(Path path, String name, String content) throws IOException;

  Path CreateFolder(String name, Path path) throws IOException;

  void UpdateSQLFile(String name);
}
