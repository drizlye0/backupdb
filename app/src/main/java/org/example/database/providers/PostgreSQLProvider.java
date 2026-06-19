package org.example.database.providers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.example.database.DatabaseProvider;

public class PostgreSQLProvider implements DatabaseProvider {
  private final Connection conn;

  public PostgreSQLProvider(Connection conn) {
    this.conn = conn;
  }

  private String getColumnQuery(ResultSet rs) throws SQLException {
    String columnName = rs.getString("column_name");
    String dataType = rs.getString("data_type");
    String typeLength = rs.getString("max_length");
    String isNull = rs.getString("is_nullable").equalsIgnoreCase("YES") ? "NULL" : "NOT NULL";

    if (typeLength != null &&
        typeLength.length() > 0 &&
        typeLength.equalsIgnoreCase("NULL")) {
      return String.format("%s %s %s", columnName, dataType, isNull);
    }

    return String.format("%s %s(%s) %s", columnName, dataType, typeLength, isNull);
  }

  @Override
  public String ShowCreateTable(String table) throws SQLException {
    String sql = """
        SELECT
            column_name,
            data_type,
            character_maximum_length AS max_length,
            is_nullable
        FROM
            information_schema.columns
        WHERE
            table_schema = 'public'
            AND table_name = ?
        ORDER BY
            ordinal_position;
            """;

    StringBuilder query = new StringBuilder("CREATE TABLE " + table + "(");

    try (PreparedStatement st = conn.prepareStatement(sql)) {
      st.setString(1, table);
      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          String columnQuery = getColumnQuery(rs);
          query.append(columnQuery).append(",");
        }
      }
      query.deleteCharAt(query.length() - 1);
      query.append(");");
    }

    return query.toString();
  }

  @Override
  public String ShowInsertInto(String table) {
    throw new UnsupportedOperationException("ShowInsertInto is not yet implemented for PostgreSQL");
  }

  @Override
  public ArrayList<String> ShowTables(String database) throws SQLException {
    String sql = """
        SELECT table_name
        FROM information_schema.tables
        WHERE table_schema = 'public'
        AND table_type = 'BASE TABLE';
            """;

    ArrayList<String> tables = new ArrayList<String>();

    try (PreparedStatement st = conn.prepareStatement(sql);
         ResultSet rs = st.executeQuery()) {
      while (rs.next()) {
        tables.add(rs.getString("table_name"));
      }
    }

    return tables;
  }

}
// PostgresSQLProvider implementation placeholder
