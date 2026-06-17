package org.example;

import java.nio.file.Path;
import java.sql.Connection;
import java.util.concurrent.Callable;

import org.example.database.ConnectionProxy;
import org.example.database.DBCredentials;
import org.example.database.DatabaseProvider;
import org.example.database.providers.MySQLProvider;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command
public class BackupDB implements Callable<Integer> {

  @Option(names = { "--help", "-help" }, usageHelp = true, description = "display help message")
  private boolean usageHelpRequested;

  @Option(names = { "--version", "-version" }, versionHelp = true, description = "display version info")
  private boolean versionInfoRequested;

  @Option(names = { "--host", "-host" }, description = "database host")
  private String host;

  @Option(names = { "--port", "-port" }, description = "database port")
  private int port;

  @Option(names = { "--username", "-user" }, description = "database username")
  private String username;

  @Option(names = { "--password", "-pass" }, description = "database password")
  private String password;

  @Option(names = { "--database", "-db" }, description = "database name")
  private String dbName;

  @Option(names = { "--provider", "-prov" }, description = "database manager provider")
  private String provider;

  @Parameters(defaultValue = ".", description = "path of result files")
  private Path path;

  @Override
  public Integer call() throws Exception {
    DBCredentials credentials = new DBCredentials(host, port, username, password, dbName);

    try (Connection conn = ConnectionProxy.getConnection(credentials, provider)) {
      DatabaseProvider db = getDatabaseProvider(provider, conn);
      NioStorage store = new NioStorage();

      BackupService backupService = new BackupService(db, store);
      backupService.BackupAllTables(dbName, path);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return 1;
    }

    return 0;
  }

  private DatabaseProvider getDatabaseProvider(String provider, Connection conn) {
    switch (provider.toLowerCase()) {
      case "mysql" -> new MySQLProvider(conn);
    }

    return null;
  }
}
