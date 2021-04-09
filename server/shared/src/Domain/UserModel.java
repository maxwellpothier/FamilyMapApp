package Domain;

public class UserModel {
  private String username, password, email, firstName, lastName, gender, personID;

  public UserModel() {
    username = null;
    password = null;
    email = null;
    firstName = null;
    lastName = null;
    gender = null;
    personID = null;
  }

  public UserModel(String setUsername, String setPassword, String setEmail, String setFirstName, String setLastName,
                   String setGender, String setPersonID) {
    this.username = setUsername;
    this.password = setPassword;
    this.email = setEmail;
    this.firstName = setFirstName;
    this.lastName = setLastName;
    this.gender = setGender;
    this.personID = setPersonID;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID = personID;
  }
}
