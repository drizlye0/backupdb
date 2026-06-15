package org.example.Providers;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.example.DatabaseProvider;

/**
 * MySQLProvider
 */
public class MySQLProvider implements DatabaseProvider {
  private Connection db;

  public MySQLProvider(Connection db) {
    this.db = db;
  }

  @Override
  public String ShowCreateTable(String table) {
    String sql = "SHOW CREATE TABLE " + table;
    try {
      PreparedStatement ps = db.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        String tableName = rs.getString(1);
        String createSt = rs.getString(2);

        System.out.println("Table name: " + tableName);
        System.out.println("Statement: " + createSt);
        return createSt;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(0);
    }

    return "";
  }

  @Override
  public String ShowInsertInto(String table) {
    try {
      DatabaseMetaData meta = db.getMetaData();
      ResultSet columnsRs = meta.getColumns(null, null, table, null);

      StringBuilder columns = new StringBuilder();
      int columnCount = 0;

      while (columnsRs.next()) {
        if (columns.length() > 0) {
          columns.append(", ");
        }
        columns.append(columnsRs.getString("COLUMN_NAME"));
        columnCount++;
      }
      columnsRs.close();

      String selectSql = "SELECT * FROM " + table;
      PreparedStatement ps = db.prepareStatement(selectSql);
      ResultSet dataRs = ps.executeQuery();

      StringBuilder values = new StringBuilder();
      while (dataRs.next()) {
        if (values.length() > 0) {
          values.append(" ");
        }
        values.append("(");
        for (int i = 1; i <= columnCount; i++) {
          if (i > 1) {
            values.append(", ");
          }
          String val = dataRs.getString(i);
          if (val == null) {
            values.append("NULL");
          } else {
            values.append("\"").append(val).append("\"");
          }
        }
        values.append(") \n");
      }
      dataRs.close();

      String resultQuery = String.format("INSERT INTO %s(%s) \n VALUES \n %s", table, columns, values);
      System.out.println(resultQuery);

      return resultQuery;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(0);
    }

    return "";
  }

  @Override
  public ArrayList<String> ShowTables(String database) {
    ArrayList<String> tables = new ArrayList<String>();

    String sql = "SELECT table_name AS tables FROM information_schema.tables WHERE table_schema = ?";

    try {
      PreparedStatement ps = db.prepareStatement(sql);
      ps.setString(1, database);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        String t = rs.getString("tables");
        tables.add(t);
      }

    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(0);
    }

    return tables;
  }
}
