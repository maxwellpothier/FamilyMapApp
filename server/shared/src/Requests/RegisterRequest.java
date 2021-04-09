package Requests;

public class RegisterRequest {
  private String username, password, email, firstName, lastName, gender, personID;

  public RegisterRequest(String setUsername, String setPassword, String setEmail, String setFirstName, String setLastName,
                         String setGender) {
    this.username = setUsername;
    this.password = setPassword;
    this.email = setEmail;
    this.firstName = setFirstName;
    this.lastName = setLastName;
    this.gender = setGender;
    this.personID = null;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String setEmail) {
    this.email = setEmail;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String setFirstName) {
    this.firstName = setFirstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String setLastName) {
    this.lastName = setLastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String setGender) {
    this.gender = setGender;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String setPersonID) {
    this.personID = setPersonID;
  }
}
