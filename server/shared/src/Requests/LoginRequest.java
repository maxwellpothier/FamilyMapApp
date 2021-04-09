package Requests;

public class LoginRequest {
  private String username, password;

  public LoginRequest(String setUsername, String setPassword) {
    this.setUsername(setUsername);
    this.setPassword(setPassword);
  }

  public String getUsername() {
    return username;
  }
  public void setUsername(String setUsername) {
    this.username = setUsername;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String setPassword) {
    this.password = setPassword;
  }
}
