package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Results.*;

public class ClearService {
  public ClearResult clear() throws DataAccessException {
    Database db = new Database();
    ClearResult res;

    try {

      db.getConnection();
      db.clearTables();
      db.closeConnection(true);

      res = new ClearResult("Clear succeeded.", true);
      return res;
    } catch (DataAccessException e) {
      db.closeConnection(false);
      res = new ClearResult("Error: The database was not cleared successfully.", false);
      return res;
    }
  }
}
