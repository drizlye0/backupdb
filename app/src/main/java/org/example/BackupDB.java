package org.example;

import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command
public class BackupDB implements Callable<Integer> {

  @Option(names = { "--host", "-host", "-h"})
  private String host;

  @Option(names = { "--username", "-user", "-u" })
  private String username;

  @Option(names = { "--password", "-pass", "-p"})
  private String password;

  @Option(names = { "--database", "-db" })
  private String dbName;

  @Option(names = { "--destination", "-dest", "-d" })
  private String outputDir;

  @Override
  public Integer call() throws Exception {
    return 0;
  }
}
