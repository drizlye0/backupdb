package org.example;

import picocli.CommandLine;;

public class App {
    public static void main(String[] args) {
      int exitCode = new CommandLine(new BackupDB()).execute(args);
      System.exit(exitCode);
    }
}
