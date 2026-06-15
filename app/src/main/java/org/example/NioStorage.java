package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NioStorage implements FileStore {
  @Override
  public Path CreateFolder(String name, Path path) {
    try {
      Path baseDir = (path == null) ? Paths.get("").toAbsolutePath() : path;
      Path p = baseDir.resolve(name);
      if (Files.notExists(p)) {
        Files.createDirectory(p);
      }

      return p;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(0);
    }

    return null;
  }

  @Override
  public void CreateSQLFile(Path path, String name, String content) {
    String fileName = name + ".sql";

    try {
      Path baseDir = (path == null) ? Paths.get("").toAbsolutePath() : path;
      Path p = baseDir.resolve(fileName);
      Files.write(p, content.getBytes());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(0);
    }
  }

  @Override
  public void UpdateSQLFile(String name) {
    // TODO Auto-generated method stub
  }
}
