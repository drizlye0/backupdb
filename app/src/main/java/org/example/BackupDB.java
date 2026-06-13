package org.example;

import java.sql.Connection;
import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command
public class BackupDB implements Callable<Integer> {

  @Option(names = { "--host", "-host", "-h" })
  private String host;

  @Option(names = { "--port", "-port" })
  private int port;

  @Option(names = { "--username", "-user", "-u" })
  private String username;

  @Option(names = { "--password", "-pass", "-p" })
  private String password;

  @Option(names = { "--database", "-db" })
  private String dbName;

  @Option(names = { "--destination", "-dest", "-d" })
  private String outputDir;

  @Option(names = { "--provider" })
  private String provider;

  @Override
  public Integer call() throws Exception {
    DBCredentials credentials = new DBCredentials(host, port, username, password, dbName);
    Connection dbConnection = ConnectionProxy.getConnection(credentials, provider);
    return 0;
  }
}
