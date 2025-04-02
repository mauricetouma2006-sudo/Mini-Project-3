package com.example.miniproject2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonsStore {
    private final ObservableList<Person> persons = FXCollections.observableArrayList();

    public PersonsStore() {
        this.persons.addAll(
                new Person("Anthony", 23, "NY", "single"));
    }

    public ObservableList<Person> getPersonsList() {
        return persons;
    }

    public void addPerson(Person person) {
        if (person != null)
            this.persons.add(person);
    }

    public void deletePerson(Person person) {
        if (person != null)
            this.persons.remove(person);
    }

    public void updatePerson(Person person, String name, Integer age, String address, String status) {
        if (person != null) {
            person.setName(name);
            person.setAge(age);
            person.setAddress(address);
            person.setStatus(status);
        }
    }
}
