package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Domain.AuthTokenModel;
import Domain.UserModel;
import Results.*;
import Requests.*;

import java.sql.SQLException;

/**
 * Class that will handle Login business logic and determine if a request was successful.
 */
public class LoginService {
  private final Database db = new Database();

  public LoginResult login(LoginRequest r) throws DataAccessException, SQLException {
    AuthTokenModel token = new AuthTokenModel();
    AuthTokenDAO accessTokenData = new AuthTokenDAO(db.getConnection());
    UserDAO accessUserData = new UserDAO(db.getConnection());

    if (!isValid(r)) {
      db.closeConnection(false);
      return new LoginResult("Error: Invalid input.", false);
    }

    UserModel user = accessUserData.find(r.getUsername());

    if (user == null) {
      db.closeConnection(false);
      return new LoginResult("Error: User does not exist.", false);
    } else if (!user.getPassword().equals(r.getPassword())) {
      db.closeConnection(false);
      return new LoginResult("Error: Password is incorrect.", false);
    } else {
      token.setUsername(r.getUsername());
      accessTokenData.insert(token);
      db.closeConnection(true);
      return new LoginResult(token.getToken(), user.getUsername(), user.getPersonID());
    }
  }

  private boolean isValid(LoginRequest r) {
    return !(r.getPassword() == null || r.getUsername() == null);
  }
}

