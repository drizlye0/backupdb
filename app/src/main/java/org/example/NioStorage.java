package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NioStorage implements FileStore {
  @Override
  public Path CreateFolder(String name, Path path) throws IOException {
    Path baseDir = (path == null) ? Paths.get("").toAbsolutePath() : path;

    if (Files.notExists(baseDir)) {
      throw new IOException("Directory does not exist: " + baseDir);
    }

    Path p = baseDir.resolve(name);
    if (Files.notExists(p)) {
      Files.createDirectory(p);
    }

    return p;
  }

  @Override
  public void CreateSQLFile(Path path, String name, String content) throws IOException {
    String fileName = name + ".sql";

    Path baseDir = (path == null) ? Paths.get("").toAbsolutePath() : path;
    Path p = baseDir.resolve(fileName);
    Files.write(p, content.getBytes());
  }

  @Override
  public void UpdateSQLFile(String name) {
    // TODO Auto-generated method stub
  }
}
