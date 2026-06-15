package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

class NioStorageTest {

  @TempDir
  Path tempDir;

  @Test
  void testCreateFolderWithValidPath() {
    NioStorage storage = new NioStorage();
    Path folder = storage.CreateFolder("testFolder", tempDir);

    assertNotNull(folder);
    assertTrue(Files.exists(folder));
    assertTrue(Files.isDirectory(folder));
    assertEquals(tempDir.resolve("testFolder"), folder);
  }

  @Test
  void testCreateFolderWithNullPath() throws IOException {
    NioStorage storage = new NioStorage();
    Path expectedFolder = Paths.get("").toAbsolutePath().resolve("tempTestFolderForUnitTests");

    try {
      Path folder = storage.CreateFolder("tempTestFolderForUnitTests", null);
      assertNotNull(folder);
      assertTrue(Files.exists(folder));
      assertTrue(Files.isDirectory(folder));
      assertEquals(expectedFolder, folder);
    } finally {
      Files.deleteIfExists(expectedFolder);
    }
  }

  @Test
  void testCreateSQLFileWithValidPath() throws IOException {
    NioStorage storage = new NioStorage();
    String content = "CREATE TABLE users (id INT);";
    storage.CreateSQLFile(tempDir, "testTable", content);

    Path expectedFile = tempDir.resolve("testTable.sql");
    assertTrue(Files.exists(expectedFile));
    assertEquals(content, Files.readString(expectedFile));
  }

  @Test
  void testCreateSQLFileWithNullPath() throws IOException {
    NioStorage storage = new NioStorage();
    String content = "CREATE TABLE test_null (id INT);";
    Path expectedFile = Paths.get("").toAbsolutePath().resolve("test_null.sql");

    try {
      storage.CreateSQLFile(null, "test_null", content);
      assertTrue(Files.exists(expectedFile));
      assertEquals(content, Files.readString(expectedFile));
    } finally {
      Files.deleteIfExists(expectedFile);
    }
  }
}
