package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NioStorage implements FileStore {
  @Override
  public Path CreateFolder(String name, Path path) {
    try {
      Path baseDir = (path == null) ? Paths.get("").toAbsolutePath() : path;
      if (Files.notExists(baseDir)) {
        throw new IllegalArgumentException("Invalid path: " + path);
      }
      Path p = baseDir.resolve(name);
      if (Files.notExists(p)) {
        Files.createDirectory(p);
      }

      return p;
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public void CreateSQLFile(Path path, String name, String content) {
    String fileName = name + ".sql";

    try {
      Path baseDir = (path == null) ? Paths.get("").toAbsolutePath() : path;
      Path p = baseDir.resolve(fileName);
      Files.write(p, content.getBytes());
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public void UpdateSQLFile(String name) {
    // TODO Auto-generated method stub
  }
}
