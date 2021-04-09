package DataAccessTests;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Domain.EventModel;
import Service.ClearService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class EventTest {
  private final ClearService service = new ClearService();
  private final Database db = new Database();
  private EventModel event;
  private EventDAO accessEventData;

  @BeforeEach
  public void setUp() throws DataAccessException {
    service.clear();
    event = new EventModel("fakeeventID", "maxpothier", "maxpothierID", "USA", "Provo",
            "Death", 0, 0, 2021);

    accessEventData = new EventDAO(db.getConnection());
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    db.closeConnection(false);
    service.clear();
  }

  @Test
  public void insertPass() throws DataAccessException {
    accessEventData.insert(event);
    Assertions.assertNotNull(accessEventData.find(event.getEventID()));
    Assertions.assertEquals(event.getEventID(), accessEventData.find(event.getEventID()).getEventID());
  }

  @Test
  public void insertFail() throws DataAccessException {
    accessEventData.insert(event);
    Assertions.assertThrows(DataAccessException.class, () -> accessEventData.insert(event));
  }

  @Test
  public void findEventPass() throws DataAccessException {
    EventModel toTest = new EventModel("event", "maxpothier", "fdsaf", "ererew", "swwwe", "112223", 0, 0, 1200);
    accessEventData.insert(toTest);
    Assertions.assertEquals(accessEventData.find("event").getEventID(), toTest.getEventID());
  }

  @Test
  public void findEventFail() throws DataAccessException, SQLException {

    EventModel toTest = new EventModel("event", "maxpothier", "fdsaf", "ererew", "swwwe", "112223", 0, 0, 1200);
    accessEventData.insert(toTest);
    db.getConnection().commit();
    Assertions.assertNull(accessEventData.find("1-2"));
  }
}
