package DataAccess;

import Domain.*;
import java.sql.*;
import java.util.*;

public class PersonDAO {
  private final Connection connection;

  public PersonDAO(Connection connection) {
    this.connection = connection;
  }

  public void insert(PersonModel person) throws DataAccessException {
    String sql = "INSERT INTO Persons (Person_ID, Username, First_Name, Last_Name, Gender, Father_ID, Mother_ID, Spouse_ID)" +
            " VALUES(?,?,?,?,?,?,?,?)";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, person.getPersonID());
      stmt.setString(2, person.getAssociatedUsername());
      stmt.setString(3, person.getFirstName());
      stmt.setString(4, person.getLastName());
      stmt.setString(5, person.getGender());
      stmt.setString(6, person.getFatherID());
      stmt.setString(7, person.getMotherID());
      stmt.setString(8, person.getSpouseID());

      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  public PersonModel find(String person_ID) throws DataAccessException {
    PersonModel person;
    ResultSet result = null;
    String sql = "SELECT * FROM Persons WHERE Person_ID = ?;";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, person_ID);
      result = stmt.executeQuery();
      if (result.next()) {
        person = new PersonModel(
                result.getString("Person_ID"),
                result.getString("Username"),
                result.getString("First_Name"),
                result.getString("Last_Name"),
                result.getString("Gender"),
                result.getString("Father_ID"),
                result.getString("Mother_ID"),
                result.getString("Spouse_ID")
        );

        return person;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding event");
    } finally {
      if (result != null) {
        try {
          result.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  public ArrayList<PersonModel> findAll(String username) throws DataAccessException {
    ArrayList<PersonModel> persons = new ArrayList<>();
    ResultSet result = null;
    String sql = "SELECT * FROM Persons WHERE Username = ?;";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, username);
      result = stmt.executeQuery();
      while (result.next()) {
        PersonModel person = new PersonModel(
                result.getString("Person_ID"),
                result.getString("Username"),
                result.getString("First_Name"),
                result.getString("Last_Name"),
                result.getString("Gender"),
                result.getString("Father_ID"),
                result.getString("Mother_ID"),
                result.getString("Spouse_ID")
        );

        persons.add(person);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding all persons associated with " + username + ".");
    } finally {
      if (result != null) {
        try {
          result.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }

    if (persons.size() == 0) {
      return null;
    } else {
      return persons;
    }
  }

  public boolean removeUsername(String username) {
    String sql = "DELETE FROM Persons WHERE Username = ?;";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, username);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public void clear() throws DataAccessException {
    String sql = "DELETE FROM Persons";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered when clearing the Persons table");
    }
  }
}
