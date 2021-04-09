package DataAccessTests;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import DataAccess.UserDAO;
import Domain.PersonModel;
import Service.ClearService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class PersonTest {
  private Database db = new Database();
  ClearService service = new ClearService();
  private PersonModel person;
  private PersonDAO accessPersonData;
  private UserDAO accessUserData;


  @BeforeEach
  public void setUp() throws DataAccessException {
    service.clear();
    person = new PersonModel("personIDfake", "maxpothier", "Max",
            "Pothier", "M", "fake father",
            "fake mother", "fake spouse");

    accessPersonData = new PersonDAO(db.getConnection());
    accessUserData = new UserDAO(db.getConnection());
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    db.closeConnection(false);
    service.clear();
  }

  @Test
  void insertPass() throws DataAccessException {
    accessPersonData.insert(person);
    Assertions.assertEquals(person.getAssociatedUsername(), accessPersonData.find(person.getPersonID()).getAssociatedUsername());
  }

  @Test
  void insertFail() throws DataAccessException {
    accessPersonData.insert(person);
    Assertions.assertThrows(DataAccessException.class, () -> accessPersonData.insert(person));
  }

  @Test
  void deletePass() throws DataAccessException, SQLException {
    Assertions.assertNull(accessPersonData.find("incorrect person id"));
    accessPersonData.insert(person);
    db.getConnection().commit();

    Assertions.assertEquals("maxpothier", accessPersonData.find("personIDfake").getAssociatedUsername());
    accessPersonData.removeUsername("maxpothier");
    db.getConnection().commit();
    Assertions.assertNull(accessPersonData.find("personIDfake"));
  }

  @Test
  void deleteFail() throws DataAccessException, SQLException {

  }

  @Test
  void clearPass() {
    try {
      Assertions.assertNull(accessPersonData.find("personIDfake"));
      accessPersonData.insert(person);
      Assertions.assertNotNull(accessPersonData.find("personIDfake"));
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  @Test
  void findPass() {
    try {
      Assertions.assertNull(accessPersonData.find("personIDfake"));
      accessPersonData.insert(person);
      Assertions.assertNotNull(accessPersonData.find("personIDfake"));
      Assertions.assertEquals(person.getPersonID(), accessPersonData.find("personIDfake").getPersonID());
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  @Test
  void findFail() {
    try {
      Assertions.assertNull(accessPersonData.find("personIDfake"));
      accessPersonData.insert(person);
      Assertions.assertNotNull(accessPersonData.find("personIDfake"));
      Assertions.assertNull(accessPersonData.find("Doesn'tExist"));
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }
}
