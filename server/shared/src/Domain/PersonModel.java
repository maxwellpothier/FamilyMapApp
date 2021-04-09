package Domain;

import java.util.Objects;
import java.util.UUID;

public class PersonModel {
  private String personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID;

  public PersonModel() {
    personID = UUID.randomUUID().toString();
    associatedUsername = null;
    firstName = null;
    lastName = null;
    gender = null;
    fatherID = null;
    motherID = null;
    spouseID = null;
  }

  public PersonModel(String setPersonID, String setAssociatedUsername, String setFirstName, String setLastName, String setGender,
                     String setFatherID, String setMotherID, String setSpouseID) {
    this.personID = setPersonID;
    this.associatedUsername = setAssociatedUsername;
    this.firstName = setFirstName;
    this.lastName = setLastName;
    this.gender = setGender;
    this.fatherID = setFatherID;
    this.motherID = setMotherID;
    this.spouseID = setSpouseID;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID = personID;
  }

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername = associatedUsername;
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

  public String getFatherID() {
    return fatherID;
  }

  public void setFatherID(String fatherID) {
    this.fatherID = fatherID;
  }

  public String getMotherID() {
    return motherID;
  }

  public void setMotherID(String motherID) {
    this.motherID = motherID;
  }

  public String getSpouseID() {
    return spouseID;
  }

  public void setSpouseID(String spouseID) {
    this.spouseID = spouseID;
  }
}
