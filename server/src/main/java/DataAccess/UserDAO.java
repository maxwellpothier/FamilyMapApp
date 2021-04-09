package DataAccess;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
  private final Connection connection;

  public UserDAO(Connection connection) {
    this.connection = connection;
  }

  public void insert(UserModel user) throws DataAccessException {
    String sql = "INSERT INTO Users (Username, Password, Email, First_Name, Last_Name, Gender, Person_ID)" +
            " VALUES(?,?,?,?,?,?,?)";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, user.getUsername());
      stmt.setString(2, user.getPassword());
      stmt.setString(3, user.getEmail());
      stmt.setString(4, user.getFirstName());
      stmt.setString(5, user.getLastName());
      stmt.setString(6, user.getGender());
      stmt.setString(7, user.getPersonID());

      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting user into the database");
    }
  }

  public UserModel find(String username) throws DataAccessException {
    UserModel user;
    ResultSet result = null;
    String sql = "SELECT * FROM Users WHERE Username = ?;";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, username);
      result = stmt.executeQuery();
      if (result.next()) {
        user = new UserModel(
                result.getString("Username"),
                result.getString("Password"),
                result.getString("Email"),
                result.getString("First_Name"),
                result.getString("Last_Name"),
                result.getString("Gender"),
                result.getString("Person_ID")
        );

        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding user: " + username);
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

  public void clear() throws DataAccessException {
    String sql = "DELETE FROM Users";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered when clearing the Users table");
    }
  }
}
