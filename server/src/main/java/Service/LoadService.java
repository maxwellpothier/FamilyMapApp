package Service;

import DataAccess.*;
import Domain.*;
import Results.*;
import Requests.*;

import java.sql.SQLException;

public class LoadService {
  private UserDAO accessUserData;
  private PersonDAO accessPersonData;
  private EventDAO accessEventData;
  private AuthTokenDAO accessTokenData;

  private final ClearService service = new ClearService();

  public LoadResult Load(LoadRequest r) throws DataAccessException {
    Database db = new Database();
    accessTokenData = new AuthTokenDAO(db.getConnection());
    accessEventData = new EventDAO(db.getConnection());
    accessPersonData = new PersonDAO(db.getConnection());
    accessUserData = new UserDAO(db.getConnection());

    try {
      if (!isValid(r)) {
        return new LoadResult("Error: Invalid input.", false);
      }

      try {
        service.clear();
      } catch (DataAccessException e) {
        e.printStackTrace();
        db.closeConnection(false);
        return new LoadResult("Error: Failed to clear database on load.", false);
      }

      insertUsers(r.getUsers());
      db.getConnection().commit();

      insertPersons(r.getPersons());
      db.getConnection().commit();

      insertEvents(r.getEvents());
      db.closeConnection(true);

      return new LoadResult("Successfully added " + r.getUsers().length + " users, " +
              r.getPersons().length + " persons, and " + r.getEvents().length + " events.", true);

    } catch (DataAccessException | SQLException e) {
      e.printStackTrace();
      db.closeConnection(false);
      service.clear();
      return new LoadResult(e.toString(), false);
    }
  }

  private boolean isValid(LoadRequest r) {
    return (
            validUserInput(r.getUsers()) &&
            validPersonInput(r.getPersons()) &&
            validEventInput(r.getEvents())
    );
  }

  private boolean validUserInput(UserModel[] users) {
    for (UserModel user : users) {
      if (user.getUsername() == null ||
              user.getPassword() == null ||
              user.getEmail() == null ||
              user.getFirstName() == null ||
              user.getLastName() == null ||
              user.getGender() == null ||
              user.getPersonID() == null) {
        return false;
      }
    }
    return true;
  }

  private boolean validPersonInput(PersonModel[] persons) {
    for (PersonModel person : persons) {
      if (person.getPersonID() == null ||
              person.getAssociatedUsername() == null ||
              person.getFirstName() == null ||
              person.getLastName() == null ||
              person.getGender() == null) {
        return false;
      }
    }
    return true;
  }

  private boolean validEventInput(EventModel[] events) {
    for (EventModel event : events) {
      if (event.getEventID() == null ||
              event.getAssociatedUsername() == null ||
              event.getPersonID() == null ||
              event.getCountry() == null ||
              event.getCity() == null ||
              event.getEventType() == null) {
        return false;
      }
    }
    return true;
  }

  private void insertUsers(UserModel[] users) throws DataAccessException {
    if (users.length == 0) {
      throw new DataAccessException("Error: Users array is empty.");
    }

    for (UserModel user : users) {
      if (accessUserData.find(user.getUsername()) == null) {
        accessUserData.insert(user);
        accessTokenData.insert(new AuthTokenModel(user.getUsername()));
      } else {
        throw new DataAccessException("Error: User has already been created.");
      }
    }
  }

  private void insertPersons(PersonModel[] persons) throws DataAccessException {
    if (persons.length == 0) {
      throw new DataAccessException("Error: Persons array is empty.");
    }

    for (PersonModel person : persons) {
      if (accessUserData.find(person.getAssociatedUsername()) == null) {
        throw new DataAccessException("Error: User does not exist.");
      } else if (accessPersonData.find(person.getPersonID()) == null) {
        accessPersonData.insert(person);
      } else {
        throw new DataAccessException("Error: Duplicate person in database.");
      }
    }
  }

  private void insertEvents(EventModel[] events) throws DataAccessException {
    if (events.length == 0) {
      throw new DataAccessException("Error: Events array is empty.");
    }

    for (EventModel event : events) {
      if (accessUserData.find(event.getAssociatedUsername()) == null) {
        throw new DataAccessException("Error: User does not exist.");
      } else if (accessEventData.find(event.getEventID()) == null) {
        accessEventData.insert(event);
      } else {
        throw new DataAccessException("Error: Duplicate event in database.");
      }
    }
  }
}
