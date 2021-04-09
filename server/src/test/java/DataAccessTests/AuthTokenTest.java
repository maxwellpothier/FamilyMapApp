package DataAccessTests;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Domain.AuthTokenModel;
import Service.ClearService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class AuthTokenTest {
  Database db = new Database();
  ClearService service = new ClearService();
  AuthTokenModel maxsToken;
  AuthTokenDAO aDAO;

  @BeforeEach
  void setUp() throws DataAccessException {
    service.clear();
    aDAO = new AuthTokenDAO(db.getConnection());
    maxsToken = new AuthTokenModel("maxpothier", "myauthtoken");
  }

  @AfterEach
  void tearDown() {
    try {
      db.closeConnection(false);
      service.clear();
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  @Test
  void insertPass() throws DataAccessException, SQLException {
    Assertions.assertNull(aDAO.find("myauthtoken"));
    aDAO.insert(maxsToken);
    db.getConnection().commit();

    AuthTokenModel t = aDAO.find("myauthtoken");
    Assertions.assertNotNull(t);
    Assertions.assertEquals("maxpothier", t.getUsername());
    Assertions.assertEquals("myauthtoken", t.getToken());
  }

  @Test
  void insertFail() throws DataAccessException, SQLException {
    Assertions.assertNull(aDAO.find("myauthtoken"));
    Assertions.assertNull(aDAO.find("MeggaBronze4321"));
    Assertions.assertNull(aDAO.find("BumbleRumble9000"));

    aDAO.insert(maxsToken);
    Assertions.assertThrows(DataAccessException.class, () -> aDAO.insert(maxsToken));
    db.getConnection().commit();

    AuthTokenModel tempOptimus = aDAO.find("myauthtoken");

    Assertions.assertNotNull(tempOptimus);

    Assertions.assertEquals("maxpothier", tempOptimus.getUsername());
    Assertions.assertEquals("myauthtoken", tempOptimus.getToken());
  }

  @Test
  void findPass() throws DataAccessException, SQLException {
    Assertions.assertNull(aDAO.find("myauthtoken"));
    aDAO.insert(maxsToken);
    db.getConnection().commit();

    AuthTokenModel tempOptimus = aDAO.find("myauthtoken");
    Assertions.assertNotNull(tempOptimus);
  }

  @Test
  void findFail() throws DataAccessException, SQLException {
    Assertions.assertNull(aDAO.find("myauthtoken"));
    aDAO.insert(maxsToken);
    db.getConnection().commit();
    AuthTokenModel t = aDAO.find("myauthtoken");
    Assertions.assertNotNull(t);
  }
}
