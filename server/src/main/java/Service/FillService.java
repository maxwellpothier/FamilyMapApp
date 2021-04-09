package Service;

import DataAccess.*;
import Generate.GenerateData;
import Generate.Storage;
import Domain.*;
import Results.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class FillService {
  private EventDAO accessEventData;
  private PersonDAO accessPersonData;

  public FillResult Fill(String username, int numGenerations) throws DataAccessException {
    GenerateData generateData = new GenerateData();
    Database db = new Database();
    accessEventData = new EventDAO(db.getConnection());
    accessPersonData = new PersonDAO(db.getConnection());
    UserDAO accessUserData = new UserDAO(db.getConnection());

    if (numGenerations <= 0) {
      db.closeConnection(false);
      return new FillResult("Error: Invalid number of generations.", false);
    }

    try {
      if (accessUserData.find(username) == null) {
        db.closeConnection(false);
        return new FillResult("Error: User does not exist.", false);
      } else if (!clearInfo(username, db.getConnection())) {
        db.closeConnection(false);
        return new FillResult("Error: Failed to delete " + username + " Events and Persons" +
                " information from the database.", false);
      } else {
        PersonModel tempPerson = createPerson(accessUserData.find(username));
        Storage generationStorage = generateData.PopulateGenerations(tempPerson, numGenerations);
        insert(generationStorage.getPersonsArray(), generationStorage.getEventsArray());
        db.closeConnection(true);
        return new FillResult("Successfully added " + generationStorage.getPersonsArray().size() +
                " persons and " + generationStorage.getEventsArray().size() + " events.", true);
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
    }
    db.closeConnection(false);
    return new FillResult("Error: Fill error.", false);
  }

  private boolean clearInfo(String username, Connection connection) {
    boolean success = false;

    if (accessEventData.removeUsername(username) && accessPersonData.removeUsername(username)) {
      try {
        connection.commit();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      success = true;
    }
    return success;
  }

  private PersonModel createPerson(UserModel user) {
    PersonModel p = new PersonModel();

    p.setPersonID(user.getPersonID());
    p.setAssociatedUsername(user.getUsername());
    p.setFirstName(user.getFirstName());
    p.setLastName(user.getLastName());
    p.setGender(user.getGender());

    return p;
  }

  private void insert(ArrayList<PersonModel> persons, ArrayList<EventModel> events) throws DataAccessException {
    if (persons.size() == 0) {
      throw new DataAccessException("Error: Persons array is empty.");
    }
    if (events.size() == 0) {
      throw new DataAccessException("Error: Events array is empty.");
    }

    // Populate with the new information
    for (PersonModel person : persons) {
      accessPersonData.insert(person);
    }
    for (EventModel event : events) {
      accessEventData.insert(event);
    }
  }
}
