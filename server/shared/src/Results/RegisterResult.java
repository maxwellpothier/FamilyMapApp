package Results;

public class RegisterResult {
  private String authtoken, username, personID, message;
  boolean success;

  public RegisterResult(String setError, boolean success) {
    this.setMessage(setError);
    this.setSuccess(success);
  }

  public RegisterResult(String setAuthToken, String setUsername, String setPersonID) {
    this.setAuthToken(setAuthToken);
    this.setUsername(setUsername);
    this.setPersonID(setPersonID);
    this.setSuccess(true);
  }

  public String getAuthtoken() {
    return authtoken;
  }

  public void setAuthToken(String setAuthToken) {
    this.authtoken = setAuthToken;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String setUsername) {
    this.username = setUsername;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String setPersonID) {
    this.personID = setPersonID;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String setMessage) {
    this.message = setMessage;
  }

  public boolean getSuccess() {
    return success;
  }

  private void setSuccess(boolean setSuccess) {
    this.success = setSuccess;
  }
}
