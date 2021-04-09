package Results;

import Domain.*;
import java.util.ArrayList;

public class PersonResult {
  private ArrayList<PersonModel> data;
  private String personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID, message;
  private boolean success;

  public PersonResult(String setMessage, boolean setBoolean) {
    this.setMessage(setMessage);
    this.setSuccess(setBoolean);
  }

  public PersonResult(PersonModel p) {
    this.setPersonID(p.getPersonID());
    this.setAssociatedUsername(p.getAssociatedUsername());
    this.setFirstName(p.getFirstName());
    this.setLastName(p.getLastName());
    this.setGender(p.getGender());
    this.setFatherID(p.getFatherID());
    this.setMotherID(p.getMotherID());
    this.setSpouseID(p.getSpouseID());
    this.setSuccess(true);
  }

  public PersonResult(ArrayList<PersonModel> data, PersonModel p) {
    this.setPersonID(p.getPersonID());
    this.setAssociatedUsername(p.getAssociatedUsername());
    this.setFirstName(p.getFirstName());
    this.setLastName(p.getLastName());
    this.setGender(p.getGender());
    this.setFatherID(p.getFatherID());
    this.setMotherID(p.getMotherID());
    this.setSpouseID(p.getSpouseID());
    this.setData(data);
    this.setMessage(null);
    this.setSuccess(true);
  }

  public ArrayList<PersonModel> getData() {
    return data;
  }

  public void setData(ArrayList<PersonModel> setPersons) {
    this.data = setPersons;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String setPersonID) {
    this.personID = setPersonID;
  }

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String setAssociatedUsername) {
    this.associatedUsername = setAssociatedUsername;
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

  public String getFatherID() {
    return fatherID;
  }

  public void setFatherID(String setFatherID) {
    this.fatherID = setFatherID;
  }

  public String getSpouseID() {
    return spouseID;
  }

  public void setSpouseID(String setSpouseID) {
    this.spouseID = setSpouseID;
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

  public void setSuccess(boolean setSuccess) {
    this.success = setSuccess;
  }

  public String getMotherID() {
    return motherID;
  }

  public void setMotherID(String setMotherID) {
    this.motherID = setMotherID;
  }
}
