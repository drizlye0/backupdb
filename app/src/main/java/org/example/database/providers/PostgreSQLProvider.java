package org.example.database.providers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.example.database.DatabaseProvider;

/**
 * PosgreSQLProvider
 */
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

    if (typeLength.equalsIgnoreCase("NULL")) {
      return String.format("%s %s %s", columnName, dataType, isNull);
    }

    return String.format("%s %s(%s) %s", columnName, dataType, typeLength, isNull);
  }

  @Override
  public String ShowCreateTable(String table) {
    String sql = """
        SELECT
            column_name,
            data_type,
            character_maximum_length AS max_length,
            is_nullable
        FROM
            information_schema.columns
        WHERE
            table_schema = 'public' -- Change if using a custom schema
            AND table_name = ?
        ORDER BY
            ordinal_position;
            """;

    StringBuilder query = new StringBuilder("CREATE TABLE " + table + "(");

    try {
      PreparedStatement st = conn.prepareStatement(sql);
      st.setString(1, table);
      ResultSet rs = st.executeQuery();

      while (rs.next()) {
        String columnQuery = getColumnQuery(rs);
        query.append(columnQuery).append(",");
      }

      query.deleteCharAt(query.length() - 1);
      query.append(");");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }

    return query.toString();
  }

  @Override
  public String ShowInsertInto(String table) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayList<String> ShowTables(String database) {
    // TODO Auto-generated method stub
    return null;
  }

}

// PostgresSQLProvider implementation placeholder
