package org.example.database;

/**
 * DBCredentials
 */
public record DBCredentials(
    String host,
    int port,
    String username,
    String password,
    String dbName) {
}
