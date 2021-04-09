package Generate;

import Domain.EventModel;
import Domain.PersonModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.util.*;

public class GenerateEvent {
  GenerateLocations getLocation = new GenerateLocations();
  ArrayList<EventModel> events = new ArrayList<>();
  String username;
  Random r = new Random();

  public GenerateEvent(String setUsername) {
    username = setUsername;
  }

  public String eventType() {
    try {
      JsonParser jp = new JsonParser();
      FileReader fr = new FileReader("json/events.json");
      JsonObject jo = (JsonObject) jp.parse(fr);
      JsonArray arr = (JsonArray) jo.get("data");
      int i = r.nextInt(arr.size());
      String event = arr.get(i).toString();
      event = event.substring(1, event.length() - 1);
      return event;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void marriage(PersonModel husband, PersonModel wife, int year) {
    int yearMarried = year + r.nextInt(6) + 22;

    EventModel husbandMarriage = getLocation.generateLocation();
    husbandMarriage.setPersonID(husband.getPersonID());
    husbandMarriage.setAssociatedUsername(username);
    husbandMarriage.setEventType("Marriage");
    husbandMarriage.setYear(yearMarried);

    EventModel wifeMarriage = joinMarriage(husbandMarriage, wife, yearMarried);
    events.add(husbandMarriage);
    events.add(wifeMarriage);
  }

  public void rootBirth(PersonModel root, int year) {
    EventModel birth = getLocation.generateLocation();

    birth.setPersonID(root.getPersonID());
    birth.setEventType("Birth");
    birth.setYear(year);
    birth.setAssociatedUsername(username);
    events.add(birth);
  }

  public void birth(PersonModel person, int year) {
    EventModel birth = getLocation.generateLocation();
    int yearBorn = year - r.nextInt(10);

    birth.setPersonID(person.getPersonID());
    birth.setEventType("Birth");
    birth.setYear(yearBorn);
    birth.setAssociatedUsername(username);
    events.add(birth);
  }

  public void death(PersonModel person, int year) {
    EventModel death = getLocation.generateLocation();
    int yearDeath = year + 30 + r.nextInt(50);
    if ((yearDeath - year) > 120) {
      yearDeath = year + 120;
    }

    death.setPersonID(person.getPersonID());
    death.setEventType("Death");
    death.setYear(yearDeath);
    death.setAssociatedUsername(username);

    events.add(death);
  }

  public void random(PersonModel person, int year) {
    int eventYear = year + 10 + r.nextInt(20);

    EventModel event = getLocation.generateLocation();
    event.setPersonID(person.getPersonID());
    event.setEventType(eventType());
    event.setYear(eventYear);
    event.setAssociatedUsername(username);

    events.add(event);
  }

  private EventModel joinMarriage(EventModel husbandMarriage, PersonModel wife, int yearMarried) {
    EventModel wifeMarriage = new EventModel();

    wifeMarriage.setPersonID(wife.getPersonID());
    wifeMarriage.setAssociatedUsername(username);
    wifeMarriage.setEventType("Marriage");
    wifeMarriage.setYear(yearMarried);
    wifeMarriage.setLongitude(husbandMarriage.getLongitude());
    wifeMarriage.setLatitude(husbandMarriage.getLatitude());
    wifeMarriage.setCity(husbandMarriage.getCity());
    wifeMarriage.setCountry(husbandMarriage.getCountry());

    return wifeMarriage;
  }

  public ArrayList<EventModel> getEvents() {
    return events;
  }
}
