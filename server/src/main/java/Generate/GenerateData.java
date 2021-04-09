package Generate;

import Domain.PersonModel;

import java.util.*;

public class GenerateData {

  private ArrayList<PersonModel> ancestors;
  private final GenerateNames getNames = new GenerateNames();
  private GenerateEvent getEvents;
  private String username;
  private final Random r = new Random();

  public Storage PopulateGenerations(PersonModel userPerson, int numGenerations) {
    username = userPerson.getAssociatedUsername();
    getEvents = new GenerateEvent(username);
    populateTree(userPerson, numGenerations);

    return new Storage(ancestors, getEvents.getEvents());
  }

  private void populateTree(PersonModel root, int numGenerations) {
    int year = 2000 - (r.nextInt(10));
    ancestors = new ArrayList<>();
    ancestors.add(root);
    getEvents.rootBirth(root, year);

    createParents(root, numGenerations - 1, year);
  }

  private void createParents(PersonModel root, int parse, int year) {
    year = year - 40;
    PersonModel father = createFather(root);
    PersonModel mother = createMother(root);
    joinFamily(root, father, mother);
    generateEvents(father, mother, year);
    ancestors.add(father);
    ancestors.add(mother);

    if (parse != 0) {
      createParents(father, parse - 1, year);
      createParents(mother, parse - 1, year);
    }

  }

  private PersonModel createFather(PersonModel child) {
    PersonModel father = new PersonModel();
    father.setAssociatedUsername(username);
    father.setFirstName(getNames.MaleName());
    father.setLastName(child.getLastName());
    father.setGender("M");

    return father;
  }

  private PersonModel createMother(PersonModel child) {
    PersonModel mother = new PersonModel();

    mother.setAssociatedUsername(username);
    mother.setFirstName(getNames.FemaleName());
    mother.setLastName(child.getLastName());
    mother.setGender("F");

    return mother;
  }

  private void joinFamily(PersonModel child, PersonModel father, PersonModel mother) {
    father.setSpouseID(mother.getPersonID());
    mother.setSpouseID(father.getPersonID());
    child.setFatherID(father.getPersonID());
    child.setMotherID(mother.getPersonID());
  }

  private void generateEvents(PersonModel father, PersonModel mother, int year) {
    getEvents.birth(father, year);
    getEvents.birth(mother, year);
    getEvents.marriage(father, mother, year);
    getEvents.death(father, year);
    getEvents.death(mother, year);
    int randomEventChance = r.nextInt(2);
    if (randomEventChance == 0) {
      getEvents.random(mother, year);
    } else {
      getEvents.random(father, year);
    }
  }
}
