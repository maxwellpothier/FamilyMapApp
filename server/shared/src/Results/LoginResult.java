package Results;

public class LoginResult {
  private String authtoken, username, personID, message;
  private boolean success;

  public LoginResult(String setMessage, boolean setSuccess) {
    this.setMessage(setMessage);
    this.setSuccess(setSuccess);
  }

  public LoginResult(String setAuthToken, String setUsername, String setPersonID) {
    this.setAuthtoken(setAuthToken);
    this.setUsername(setUsername);
    this.setPersonID(setPersonID);
    this.setSuccess(true);
  }

  public String getAuthtoken() {
    return authtoken;
  }

  public void setAuthtoken(String setAuthToken) {
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
