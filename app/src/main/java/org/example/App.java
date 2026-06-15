package org.example;

import picocli.CommandLine;

public class App {
  public static void main(String[] args) {
    CommandLine commandLine = new CommandLine(new BackupDB());

    commandLine.parseArgs(args);
    if (commandLine.isUsageHelpRequested()) {
      commandLine.usage(System.out);
    }

    if (commandLine.isVersionHelpRequested()) {
      commandLine.printVersionHelp(System.out);
    }

    commandLine.execute(args);
  }
}
