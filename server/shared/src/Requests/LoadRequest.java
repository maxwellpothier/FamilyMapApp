package Requests;

import Domain.*;

public class LoadRequest {
  private UserModel[] users;
  private PersonModel[] persons;
  private EventModel[] events;

  public LoadRequest(UserModel[] users, PersonModel[] persons, EventModel[] events) {
    this.users = users;
    this.persons = persons;
    this.events = events;
  }

  public UserModel[] getUsers() { return users; }
  public void setUsers(UserModel[] users) { this.users = users; }
  public PersonModel[] getPersons() { return persons; }
  public void setPersons(PersonModel[] persons) { this.persons = persons; }
  public EventModel[] getEvents() { return events; }
  public void setEvents(EventModel[] events) { this.events = events; }
}
