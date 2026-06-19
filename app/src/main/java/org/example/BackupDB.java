package org.example;

import java.nio.file.Path;
import java.sql.Connection;
import java.util.concurrent.Callable;

import org.example.database.ConnectionProxy;
import org.example.database.DBCredentials;
import org.example.database.DatabaseProvider;
import org.example.database.Provider;
import org.example.database.providers.MySQLProvider;
import org.example.database.providers.PostgreSQLProvider;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "backupdb", header = "cli utility for create backups for your database schemas", version = "1.0.0", usageHelpAutoWidth = true)
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
  private String prov;

  @Parameters(defaultValue = ".", description = "path of result files")
  private Path path;

  @Override
  public Integer call() throws Exception {
    DBCredentials credentials = new DBCredentials(host, port, username, password, dbName);

    Provider provider = Provider.fromString(prov);
    if (provider == Provider.UNKNOWN_DB) {
      System.err.println("Unknow DB: " + prov);
      return 1;
    }

    try (Connection conn = ConnectionProxy.getConnection(credentials, provider)) {
      DatabaseProvider db = getDatabaseProvider(provider, conn);

      if (db == null) {
        System.err.println("Null Database Provider");
        return 1;
      }

      NioStorage store = new NioStorage();
      BackupService backupService = new BackupService(db, store);

      return backupService.BackupAllTables(dbName, path);

    } catch (Exception e) {
      System.err.println("Backup failed: " + e.getMessage());
      return 1;
    }
  }

  private DatabaseProvider getDatabaseProvider(Provider provider, Connection conn) {
    return switch (provider) {
      case POSTGRESQL -> new PostgreSQLProvider(conn);
      case MYSQL -> new MySQLProvider(conn);
      default -> null;
    };
  }
}
