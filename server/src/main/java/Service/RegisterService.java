package Service;

import DataAccess.*;
import Generate.GenerateData;
import Generate.Storage;
import Domain.*;
import Results.*;
import Requests.*;
import java.util.ArrayList;

public class RegisterService {
  private EventDAO accessEventsData;
  private PersonDAO accessPersonData;
  private final UserModel user = new UserModel();
  private final PersonModel person = new PersonModel();
  private final GenerateData generator = new GenerateData();

  public RegisterResult register(RegisterRequest r) throws DataAccessException {
    Database db = new Database();
    AuthTokenDAO accessTokenData = new AuthTokenDAO(db.getConnection());
    accessEventsData = new EventDAO(db.getConnection());
    accessPersonData = new PersonDAO(db.getConnection());
    UserDAO accessUserData = new UserDAO(db.getConnection());

    if (!isValid(r)) {
      return new RegisterResult("Error: Invalid input.", false);
    }
    createUser(r);
    createPerson(r);

    try {
      if (accessUserData.find(user.getUsername()) == null) {
        accessUserData.insert(user);
        AuthTokenModel token = new AuthTokenModel(user.getUsername());
        accessTokenData.insert(token);

        Storage storage = generator.PopulateGenerations(person, 4);
        insert(storage.getPersonsArray(), storage.getEventsArray());
        db.closeConnection(true);
        return new RegisterResult(token.getToken(), user.getUsername(), person.getPersonID());
      } else {
        db.closeConnection(false);
        return new RegisterResult("Error: Username is already being used.", false);
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
      return new RegisterResult(e.toString(), false);
    }

  }

  private boolean isValid(RegisterRequest r) {
    return ! ((r.getUsername() == null) ||
            (r.getPassword() == null) ||
            (r.getEmail() == null) ||
            (r.getFirstName() == null) ||
            (r.getLastName() == null) ||
            (r.getGender() == null));
  }

  private void createUser(RegisterRequest r) {
    user.setUsername(r.getUsername());
    user.setPassword(r.getPassword());
    user.setEmail(r.getEmail());
    user.setFirstName(r.getFirstName());
    user.setLastName(r.getLastName());
    user.setGender(r.getGender());
    user.setPersonID(person.getPersonID());
  }

  private void createPerson(RegisterRequest r) {
    person.setAssociatedUsername(r.getUsername());
    person.setFirstName(r.getFirstName());
    person.setLastName(r.getLastName());
    person.setGender(r.getGender());
  }

  private void insert(ArrayList<PersonModel> persons, ArrayList<EventModel> events) throws DataAccessException {
    if (persons.size() == 0) {
      throw new DataAccessException("Error: Persons array is empty.");
    }
    if (events.size() == 0) {
      throw new DataAccessException("Error: Events array is empty.");
    }
    for (PersonModel person : persons) {
      accessPersonData.insert(person);
    }
    for (EventModel event : events) {
      accessEventsData.insert(event);
    }
  }
}
