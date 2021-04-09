package Domain;

import java.util.UUID;

public class EventModel {
  private String eventID, associatedUsername, personID, country, city, eventType;
  private float latitude, longitude;
  private int year;

  public EventModel() {
    eventID = UUID.randomUUID().toString();
    associatedUsername = null;
    personID = null;
    country = null;
    city = null;
    eventType = null;
    latitude = 0.0f;
    longitude = 0.0f;
    year = 0;
  }

  public EventModel(String setEventID, String setAssociatedUsername, String setPersonID, String setCountry, String setCity, String setEventType,
                    float setLatitude, float setLongitude, int setYear) {
    this.setEventID(setEventID);
    this.setAssociatedUsername(setAssociatedUsername);
    this.setPersonID(setPersonID);
    this.setCountry(setCountry);
    this.setCity(setCity);
    this.setEventType(setEventType);
    this.setLatitude(setLatitude);
    this.setLongitude(setLongitude);
    this.setYear(setYear);
  }

  public EventModel(String setCity, String setCountry, float setLatitude, float setLongitude) {
    this.eventID = UUID.randomUUID().toString();
    this.setCity(setCity);
    this.setCountry(setCountry);
    this.setLatitude(setLatitude);
    this.setLongitude(setLongitude);
  }

  public String getEventID() {
    return eventID;
  }
  public void setEventID(String setEventID) {
    this.eventID = setEventID;
  }
  public String getAssociatedUsername() {
    return associatedUsername;
  }
  public void setAssociatedUsername(String setAssociatedUsername) {
    this.associatedUsername = setAssociatedUsername;
  }
  public String getPersonID() {
    return personID;
  }
  public void setPersonID(String setPersonID) {
    this.personID = setPersonID;
  }
  public String getCountry() {
    return country;
  }
  public void setCountry(String setCountry) {
    this.country = setCountry;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String setCity) {
    this.city = setCity;
  }
  public String getEventType() {
    return eventType;
  }
  public void setEventType(String setEventType) {
    this.eventType = setEventType;
  }
  public float getLatitude() {
    return latitude;
  }
  public void setLatitude(Float setLatitude) {
    this.latitude = setLatitude;
  }
  public float getLongitude() {
    return longitude;
  }
  public void setLongitude(Float setLongitude) {
    this.longitude = setLongitude;
  }
  public int getYear() {
    return year;
  }
  public void setYear(Integer setYear) {
    this.year = setYear;
  }
}
