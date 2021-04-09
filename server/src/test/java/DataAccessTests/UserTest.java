package DataAccessTests;

import DataAccess.*;
import Domain.UserModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
  private Database db;
  private UserModel user;
  private UserDAO accessUserData;

  @BeforeEach
  public void setUp() throws DataAccessException {
    db = new Database();
    Connection conn = db.getConnection();
    user = new UserModel("maxpothier", "mypassword", "fake@gmail.com",
            "Max", "Pothier", "M", "fake123");

    db.clearTables();
    accessUserData = new UserDAO(conn);
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    db.closeConnection(false);
  }

  @Test
  void insertPass() throws DataAccessException {
    accessUserData.insert(user);
    assertNotNull(user);

    assertEquals(user.getUsername(), accessUserData.find(user.getUsername()).getUsername());
  }

  @Test
  void insertFail() throws DataAccessException {
    accessUserData.insert(user);

    // Adding duplicate username
    assertThrows(DataAccessException.class, () -> accessUserData.insert(user));
  }


  @Test
  void findPass() {
    try {
      assertNull(accessUserData.find("maxpothier"));
      accessUserData.insert(user);
      assertNotNull(accessUserData.find("maxpothier"));

      assertEquals(user.getUsername(), accessUserData.find("maxpothier").getUsername());
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  @Test
  void findFail() {
    try {
      assertNull(accessUserData.find("maxpothier"));
      accessUserData.insert(user);
      assertNotNull(accessUserData.find("maxpothier"));

      assertNull(accessUserData.find("fake username"));
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  @Test
  void clear() {
    try {
      accessUserData.insert(user);
      assertNotNull(accessUserData.find("maxpothier"));
      accessUserData.clear();
      assertNull(accessUserData.find("maxpothier"));
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }
}
