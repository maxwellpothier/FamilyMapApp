package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Domain.EventModel;
import Results.*;
import Domain.AuthTokenModel;

import java.util.ArrayList;

public class EventService {
  private Database db;
  private AuthTokenDAO accessTokenData;
  private EventDAO accessEventData;


  public EventResult event(String token) throws DataAccessException {
    db = new Database();
    accessTokenData = new AuthTokenDAO(db.getConnection());
    accessEventData = new EventDAO(db.getConnection());

    try {
      AuthTokenModel tokenModel = accessTokenData.find(token);
      if (tokenModel == null) {
        db.closeConnection(false);
        return new EventResult("Error: Invalid token returned null.", false);
      } else {
        ArrayList<EventModel> events = accessEventData.findAll(tokenModel.getUsername());
        if (events == null) {
          db.closeConnection(false);
          return new EventResult("Error: Person is not associated with " + tokenModel.getUsername() + ".", false);
        } else {
          db.closeConnection(true);
          return new EventResult(events);
        }
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
      return new EventResult("Error: Database fatal error.", false);
    }
  }

  public EventResult event(String eventID, String token) throws DataAccessException {
    db = new Database();
    accessTokenData = new AuthTokenDAO(db.getConnection());
    accessEventData = new EventDAO(db.getConnection());

    try {
      AuthTokenModel tokenModel = accessTokenData.find(token);
      if (tokenModel == null) {
        db.closeConnection(false);
        return new EventResult("Error: Invalid token returned null.", false);
      } else {
        EventModel findEvent = accessEventData.find(eventID);
        if (findEvent == null) {
          db.closeConnection(false);
          return new EventResult("Error: Event does not exist.", false);
        } else if (! tokenModel.getUsername().equals(findEvent.getAssociatedUsername())) {
          db.closeConnection(false);
          return new EventResult("Error: Event is not associated with " + tokenModel.getUsername() + ".", false);
        } else {
          db.closeConnection(true);
          return new EventResult(findEvent, tokenModel.getUsername());
        }
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
      return new EventResult("Error: Database fatal error.", false);
    }
  }
}
