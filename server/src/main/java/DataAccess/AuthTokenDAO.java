package DataAccess;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthTokenDAO {
  private final Connection connection;

  public AuthTokenDAO(Connection connection) {
    this.connection = connection;
  }

  public void insert(AuthTokenModel token) throws DataAccessException {
    String sql = "INSERT INTO AuthToken (Username, Auth_Token) VALUES (?,?)";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, token.getUsername());
      stmt.setString(2, token.getToken());
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  public AuthTokenModel find(String searchToken) throws DataAccessException {
    AuthTokenModel token;
    ResultSet result = null;
    String sql = "SELECT * FROM AuthToken WHERE Auth_Token = ?;";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, searchToken);
      result = stmt.executeQuery();
      if (result.next()) {
        token = new AuthTokenModel(
                result.getString("Username"),
                result.getString("Auth_Token")
        );

        return token;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding token");
    } finally {
      if (result != null) {
        try {
          result.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }
}
