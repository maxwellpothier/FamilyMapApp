package Results;

import Domain.EventModel;
import java.util.ArrayList;

public class EventResult {
  private ArrayList<EventModel> data;
  private String eventID, associatedUsername, personID, country, city, eventType, message;
  private Float latitude;
  private Float longitude;
  private Integer year;
  private boolean success;

  public EventResult(String message, boolean success) {
    this.message = message;
    this.success = success;
  }

  public EventResult(ArrayList<EventModel> events) {
    this.data = events;
    this.success = true;
  }

  public EventResult(EventModel event, String username) {
    this.eventID = event.getEventID();
    this.associatedUsername = username;
    this.personID = event.getPersonID();
    this.country = event.getCountry();
    this.city = event.getCity();
    this.eventType = event.getEventType();
    this.latitude = event.getLatitude();
    this.longitude = event.getLongitude();
    this.year = event.getYear();
    this.success = true;
  }

  public ArrayList<EventModel> getData() {
    return data;
  }

  public void setData(ArrayList<EventModel> setEvents) {
    this.data = setEvents;
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

  public Float getLatitude() {
    return latitude;
  }

  public void setLatitude(Float setLatitude) {
    this.latitude = setLatitude;
  }

  public Float getLongitude() {
    return longitude;
  }

  public void setLongitude(Float setLongitude) {
    this.longitude = setLongitude;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer setYear) {
    this.year = setYear;
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
}



