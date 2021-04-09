package DataAccess;

import Domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventDAO {
  private final Connection connection;

  public EventDAO(Connection connection) {
    this.connection = connection;
  }

  public void insert(EventModel event) throws DataAccessException {
    String sql = "INSERT INTO Events (EventID, AssociatedUsername, PersonID, Country, City, EventType," +
            "Latitude, Longitude, Year) VALUES(?,?,?,?,?,?,?,?,?)";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, event.getEventID());
      stmt.setString(2, event.getAssociatedUsername());
      stmt.setString(3, event.getPersonID());
      stmt.setString(4, event.getCountry());
      stmt.setString(5, event.getCity());
      stmt.setString(6, event.getEventType());
      stmt.setFloat(7, event.getLatitude());
      stmt.setFloat(8, event.getLongitude());
      stmt.setInt(9, event.getYear());

      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  public EventModel find(String eventID) throws DataAccessException {
    EventModel event;
    ResultSet result = null;
    String sql = "SELECT * FROM Events WHERE EventID = ?;";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, eventID);
      result = stmt.executeQuery();
      if (result.next()) {
        event = new EventModel(
                result.getString("EventID"),
                result.getString("AssociatedUsername"),
                result.getString("PersonID"),
                result.getString("Country"),
                result.getString("City"),
                result.getString("EventType"),
                result.getFloat("Latitude"),
                result.getFloat("Longitude"),
                result.getInt("Year")
        );

        return event;
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

  public ArrayList<EventModel> findAll(String username) throws DataAccessException {
    ArrayList<EventModel> events = new ArrayList<>();
    EventModel event;
    ResultSet result = null;
    String sql = "SELECT * FROM Events WHERE AssociatedUsername = ?;";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, username);
      result = stmt.executeQuery();
      while (result.next()) {
        event = new EventModel(
                result.getString("EventID"),
                result.getString("AssociatedUsername"),
                result.getString("PersonID"),
                result.getString("Country"),
                result.getString("City"),
                result.getString("EventType"),
                result.getFloat("Latitude"),
                result.getFloat("Longitude"),
                result.getInt("Year")
        );

        events.add(event);
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
    if (events.size() == 0) {
      return null;
    } else {
      return events;
    }
  }

  public boolean removeUsername(String associatedUsername) {
    String sql = "DELETE FROM Events WHERE AssociatedUsername = ?;";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, associatedUsername);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
