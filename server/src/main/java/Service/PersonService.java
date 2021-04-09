package Service;

import DataAccess.*;
import Domain.*;
import Results.*;

import java.util.ArrayList;

public class PersonService {
  private Database db;
  private AuthTokenDAO accessTokenData;
  private PersonDAO accessPersonData;


  public PersonResult Person(String personID, String token) throws DataAccessException {
    db = new Database();
    accessTokenData = new AuthTokenDAO(db.getConnection());
    accessPersonData = new PersonDAO(db.getConnection());
    try {
      AuthTokenModel findToken = accessTokenData.find(token);
      PersonModel person = accessPersonData.find(personID);

      if (findToken == null) {
        db.closeConnection(false);
        return new PersonResult("Error: Invalid token returned null.", false);
      } else {
        if (person == null) {
          db.closeConnection(false);
          return new PersonResult("Error: Person was not found in the database.", false);
        } else if (! findToken.getUsername().equals(person.getAssociatedUsername())) {
          db.closeConnection(false);
          return new PersonResult("Error: Person is not associated with " + findToken.getUsername() + ".", false);
        } else {
          db.closeConnection(true);
          return new PersonResult(person);
        }
      }
    } catch (DataAccessException e) {
      db.closeConnection(false);
      return new PersonResult(e.toString(), false);
    }
  }

  public PersonResult Person(String token) throws DataAccessException {
    db = new Database();
    accessTokenData = new AuthTokenDAO(db.getConnection());
    accessPersonData = new PersonDAO(db.getConnection());

    try {
      AuthTokenModel findToken = accessTokenData.find(token);

      if (findToken == null) {
        db.closeConnection(false);
        return new PersonResult("Error: AuthToken returned null.", false);
      } else {
        ArrayList<PersonModel> persons = accessPersonData.findAll(findToken.getUsername());
        if (persons == null) {
          db.closeConnection(false);
          return new PersonResult("Error: " + findToken.getUsername() + " has no associated Persons.", false);
        } else {
          PersonModel personsPerson = findPerson(findToken);
          db.closeConnection(true);
          return new PersonResult(persons, personsPerson);
        }
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
      return new PersonResult(e.toString(), false);
    }
  }

  private PersonModel findPerson(AuthTokenModel t) throws DataAccessException {
    UserDAO uDAO = new UserDAO(db.getConnection());
    UserModel temp = uDAO.find(t.getUsername());
    return accessPersonData.find(temp.getPersonID());
  }
}
