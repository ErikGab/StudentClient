package se.yrgo.erik.studentclient.formatables;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Student implements Formatable{

  private int id;
  private final String ITEMTYPE = "student";
  private Map<String, String> properties;
  private List<Formatable> subItems = new ArrayList<>();
  private static final String TAG = "Student";

  public Student(int id, String name, String surname) {
    this.id = id;
    properties = new LinkedHashMap<>();
    properties.put("name", name);
    properties.put("surname", surname);
    Log.v(TAG, "new created with id "+id);
  }

  public Student(int id, String name, String surname, int age, String postAddress,
                 String streetAddress) {
    this(id, name, surname, age, postAddress, streetAddress, null, null);
  }

  public Student(int id, String name, String surname, int age, String postAddress,
                 String streetAddress, List<Course> courses) {
    this(id, name, surname, age, postAddress, streetAddress, null, courses);
  }

  public Student(int id, String name, String surname, int age, String postAddress,
                 String streetAddress, Formatable phonenumbers) {
    this(id, name, surname, age, postAddress, streetAddress, phonenumbers, null);
  }

  public Student(int id, String name, String surname, int age, String postAddress,
                 String streetAddress, Formatable phoneNumbers, List<Course> courses) {
    this.id = id;
    properties = new LinkedHashMap<>();
    properties.put("name", name);
    properties.put("surname", surname);
    properties.put("age", Integer.toString(age));
    properties.put("postAddress", postAddress);
    properties.put("streetAddress", streetAddress);
    if (phoneNumbers != null) {
      subItems.add(phoneNumbers);
    }
    if (courses != null) {
      for(Formatable course:courses) {
        subItems.add(course);
      }
    }
    Log.v(TAG, "new created with id "+id);
  }

  public Student(int id, Map<String,String> properties, List<Formatable> subItems) {
    this.id = id;
    this.properties = properties;
    this.subItems = subItems;
    Log.v(TAG, "new created with id "+id);
  }

  public Student(int id, Map<String,String> properties) {
    this.id = id;
    this.properties = properties;
    Log.v(TAG, "new created with id "+id);
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getItemType() {
    return ITEMTYPE;
  }

  @Override
  public Map<String,String> getProperties() {
    return properties;
  }

  @Override
  public List<Formatable> getSubItems() {
    return subItems;
  }

  public String getName() {
    return String.valueOf(properties.get("name"));
  }

  public String getSurname() {
    return String.valueOf(properties.get("surname"));
  }

  @Override
  public String toString() {
    Log.v(TAG, "with id "+id+ " returning String");
    return getName() + " " + getSurname();
  }

  public String toListViewString() {
    Log.v(TAG, "with id "+id+ " returning ListViewString");
    return new StringBuilder()
            .append(getName())
            .append(" ")
            .append(getSurname())
            .append(" ")
            .append(String.valueOf(properties.get("status")))
            .append(" ")
            .append(String.valueOf(properties.get("grade")))
            .toString();
  }

}
