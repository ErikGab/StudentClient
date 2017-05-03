package se.yrgo.erik.studentclient.formatables;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Course implements Formatable {

  private int id;
  private final FormatableType ITEMTYPE = FormatableType.COURSE;
  private Map<String, String> properties;
  private List<Formatable> subItems = new ArrayList<>();
  private static final String TAG = "Course";

  public Course(int id, String name) {
    this.id = id;
    properties = new LinkedHashMap<>();
    properties.put("name",name);
    Log.v(TAG, "new created with id "+id);
  }

  public Course(int id, String startDate, String endDate, String name, int points,
                String description) {
    this(id, startDate, endDate, name, points, description, null);
  }

  public Course(int id, String startDate, String endDate, String name, int points,
                String description, List<Student> students) {
    this.id = id;
    properties = new LinkedHashMap<>();
    properties.put("startDate", startDate);
    properties.put("endDate", endDate);
    properties.put("name", name);
    properties.put("points", Integer.toString(points));
    properties.put("description", description);
    if (students != null) {
      for (Formatable student:students){
        subItems.add(student);
      }
    }
    Log.v(TAG, "new created with id "+id);
  }

  public Course(int id, Map<String,String> properties, List<Formatable> subItems) {
    this.id = id;
    this.properties = properties;
    this.subItems = subItems;
    Log.v(TAG, "new created with id "+id);
  }

  public Course(int id, Map<String,String> properties) {
    this.id = id;
    this.properties = properties;
    Log.v(TAG, "new created with id "+id);
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public FormatableType getItemType() {
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

  public String getYear() {
    int len = String.valueOf(properties.get("name")).length();
    return String.valueOf(properties.get("name")).substring(len-4, len);
  }

  public String getName() {
    return String.valueOf(properties.get("name"));
  }

  @Override
  public String toString() {
    Log.v(TAG, "with id "+id+" returning String");
    return getName();
  }

  @Override
  public String toListViewString(){
    Log.v(TAG, "with id "+id+" returning ListViewString");
    StringBuilder sb = new StringBuilder()
            .append(getName());
    if (properties.containsKey("grade") && !properties.get("grade").equals("null")) {
      sb.append(" ")
              .append(String.valueOf(properties.get("grade")));
    }
    return sb.toString();
  }

  @Override
  public String toListViewStringHeader() {
    Log.v(TAG, "with id "+id+ " returning ListViewStringHeader");
    if (properties.containsKey("status") && !properties.get("status").equals("null")) {
      return properties.get("status");
    } else {
      return "...";
    }

  }

}
