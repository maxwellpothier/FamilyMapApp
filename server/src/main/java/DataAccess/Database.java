package DataAccess;

import java.io.File;
import java.sql.*;

public class Database {
  public Connection connection;
  final String dbName = "db" + File.separator + "familymapdb.db";
  final String CONNECTION_URL = "jdbc:sqlite:" + dbName;

  public Connection openConnection() throws DataAccessException {
    try {
      connection = DriverManager.getConnection(CONNECTION_URL);
      connection.setAutoCommit(false);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Unable to open connection to database");
    }

    return connection;
  }


  public Connection getConnection() throws DataAccessException {
    if (connection == null) {
      return openConnection();
    } else {
      return connection;
    }
  }


  public void closeConnection(boolean commit) throws DataAccessException {
    try {
      if (commit) {
        connection.commit();
      } else {
        connection.rollback();
      }

      connection.close();
      connection = null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Unable to close database connection");
    }
  }

  public void clearTables() throws DataAccessException {
    try (Statement stmt = connection.createStatement()) {
      String sql = "DELETE FROM Users";
      stmt.executeUpdate(sql);
      sql = "DELETE FROM Persons";
      stmt.executeUpdate(sql);
      sql = "DELETE FROM Events";
      stmt.executeUpdate(sql);
      sql = "DELETE FROM AuthToken";
      stmt.executeUpdate(sql);
    } catch (SQLException e) {
      throw new DataAccessException("SQL Error encountered while clearing tables");
    }
  }
}
