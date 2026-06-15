package org.example;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.example.Providers.MySQLProvider;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command
public class BackupDB implements Callable<Integer> {

  @Option(names = { "--help", "-help" }, usageHelp = true, description = "display help message")
  private boolean usageHelpRequested;

  @Option(names = { "--version", "-version" }, versionHelp = true, description = "display version info")
  private boolean versionInfoRequested;

  @Option(names = { "--host", "-host" })
  private String host;

  @Option(names = { "--port", "-port" })
  private int port;

  @Option(names = { "--username", "-user" })
  private String username;

  @Option(names = { "--password", "-pass" })
  private String password;

  @Option(names = { "--database", "-db" })
  private String dbName;

  @Option(names = { "--destination", "-dest" })
  private String outputDir;

  @Option(names = { "--provider", "-prov" })
  private String provider;

  @Override
  public Integer call() throws Exception {
    DBCredentials credentials = new DBCredentials(host, port, username, password, dbName);
    try {
      Connection dbConnection = ConnectionProxy.getConnection(credentials, provider);

      if (dbConnection instanceof Connection) {
        System.out.println("Valid connection");
      }

      MySQLProvider mysql = new MySQLProvider(dbConnection);
      mysql.ShowCreateTable("user");
      mysql.ShowInsertInto("user");
      ArrayList<String> tables = mysql.ShowTables(dbName);
      for (String table : tables ) {
        System.out.println(table);
      }

      dbConnection.close();

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return 0;
    }
    return 0;
  }
}
