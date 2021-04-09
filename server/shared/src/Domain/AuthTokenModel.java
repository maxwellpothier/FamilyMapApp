package Domain;

import java.util.UUID;

public class AuthTokenModel {

  private String token, username;

  public AuthTokenModel() {
    token = UUID.randomUUID().toString();
    username = null;
  }

  public AuthTokenModel(String setUsername) {
    this.username = setUsername;
    this.token = UUID.randomUUID().toString();
  }

  public AuthTokenModel(String setUsername, String setAuthToken) {
    this.username = setUsername;
    this.token = setAuthToken;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
