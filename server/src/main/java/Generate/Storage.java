package Generate;

import java.util.ArrayList;

import Domain.PersonModel;
import Domain.EventModel;

public class Storage {

  private ArrayList<PersonModel> persons = null;
  private ArrayList<EventModel> events = null;

  public Storage(ArrayList<PersonModel> setPersons, ArrayList<EventModel> setEvents) {
    this.persons = setPersons;
    this.events = setEvents;
  }

  public ArrayList<PersonModel> getPersonsArray() { return persons; }

  public ArrayList<EventModel> getEventsArray() { return events; }
}
