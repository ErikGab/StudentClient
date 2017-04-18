package se.yrgo.erik.studentclient.dataretrieval.retrievers.parseresponse;
import android.util.Log;

import se.yrgo.erik.studentclient.formatables.Course;
import se.yrgo.erik.studentclient.formatables.Formatable;
import se.yrgo.erik.studentclient.formatables.FormatableItem;
import se.yrgo.erik.studentclient.formatables.FormatableType;
import se.yrgo.erik.studentclient.formatables.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class JSONDataParser {

  private static final String TAG = "JSONDataParser";

  public static List<Formatable> Json2Students(String json)  throws DataParserException {
    Log.v(TAG, "Json2Students");
    List<Formatable> returnee = new ArrayList<Formatable>();
    int skippedItems = 0;
    try {
      JSONObject jsonObj = new JSONObject(json);
      String type = "student";
      if (jsonObj.has(type)) {
        JSONArray items = jsonObj.getJSONArray(type);
        for (int i = 0; i < items.length(); i++) {
          JSONObject c = items.getJSONObject(i);
          try {
            if (c.has("id") && c.has("name") && c.has("surname") &&
                    c.has("age") && c.has("postAddress") && c.has("streetAddress")) {
              returnee.add(new Student(c.getInt("id"),
                      c.getString("name"),
                      c.getString("surname"),
                      parseAge(c),
                      c.getString("postAddress"),
                      c.getString("streetAddress"),
                      extractPhoneNumber(c),
                      extractCourses(c)));
            } else if (c.has("id") && c.has("name") && c.has("surname")) {
              returnee.add(new Student(c.getInt("id"),
                      c.getString("name"),
                      c.getString("surname")));
            } else {
              skippedItems++;
            }
          } catch (Exception e){
            throw new DataParserException( e.getMessage());
          }
        }
      }

    } catch ( JSONException jse){
      System.err.println(jse.getMessage());
    }
    Log.v(TAG, "Json2Students returning " + returnee.size() + " students, " + skippedItems +
            " items skipped.");
    return returnee;
  }

  public static List<Formatable> Json2Course(String json) throws DataParserException {
    Log.v(TAG, "Json2Course");
    List<Formatable> returnee = new ArrayList<Formatable>();
    int skippedItems = 0;
    try {
      JSONObject jsonObj = new JSONObject(json);
      String type = "course";
      if ( jsonObj.has(type)) {
        JSONArray items = jsonObj.getJSONArray(type);
        for (int i = 0; i < items.length(); i++) {
          JSONObject c = items.getJSONObject(i);
          try {
            if (c.has("id") && c.has("name") && c.has("description") &&
                    c.has("startDate") && c.has("endDate") && c.has("points")) {
              returnee.add(new Course(c.getInt("id"),
                      c.getString("startDate"),
                      c.getString("endDate"),
                      c.getString("name"),
                      c.getInt("points"),
                      c.getString("description"),
                      extractStudents(c)));
            } else if (c.has("id") && c.has("name")) {
              returnee.add(new Course(c.getInt("id"), c.getString("name")));
            } else {
              skippedItems++;
            }
          } catch (Exception e) {
            throw new DataParserException( e.getMessage());
          }
        }
      }

    } catch ( JSONException jse){
      System.err.println(jse.getMessage());
    }
    Log.v(TAG, "Json2Course returning " + returnee.size() + " courses, " + skippedItems +
            " items skipped.");
    return returnee;
  }

  private static FormatableItem extractPhoneNumber(JSONObject object) throws DataParserException {
    FormatableItem returnee = null;
    String key = "phonenumbers";
    if (object.has(key)) {
      try {
        Log.v(TAG, "extracting phonenumbers for id "+object.getInt("id"));
        JSONArray numbers = object.getJSONArray(key);
        LinkedHashMap<String, String> phoneNumbers = new LinkedHashMap<>();
        for (int i = 0; i < numbers.length(); i++) {
          JSONObject c = numbers.getJSONObject(i);
          if (c.has("mobile")) {
            phoneNumbers.put("mobile", c.getString("mobile"));
          }
          if (c.has("home")) {
            phoneNumbers.put("home", c.getString("home"));
          }
          if (c.has("fax")) {
            phoneNumbers.put("fax", c.getString("fax"));
          }
          if (c.has("work")) {
            phoneNumbers.put("work", c.getString("work"));
          }
        }
        returnee = new FormatableItem(FormatableType.PHONENUMBERS, 0, phoneNumbers);
      } catch (JSONException jse) {
        throw new DataParserException(jse.getMessage());
      }
    }
    return returnee;
  }

  private static List<Course> extractCourses(JSONObject object) throws DataParserException {
    List<Course> returnee = new ArrayList<>();
    String key = "course";
    if (object.has(key)) {
      try {
        Log.v(TAG, "extracting courses for id "+object.getInt("id"));
        JSONArray courses = object.getJSONArray(key);
        for (int i = 0; i < courses.length(); i++) {
          JSONObject c = courses.getJSONObject(i);
          LinkedHashMap<String, String> properties = new LinkedHashMap<>();
          properties.put("name", c.getString("name"));
          properties.put("status", c.getString("status"));
          properties.put("grade", c.getString("grade"));
          returnee.add(new Course(c.getInt("id"), properties));
        }
      } catch (JSONException jse){
        throw new DataParserException(jse.getMessage());
      }
    }
    return returnee;
  }

  private static List<Student> extractStudents(JSONObject object) throws DataParserException {
    List<Student> returnee = new ArrayList<>();
    String key = "student";
    if (object.has(key)) {
      try {
        Log.v(TAG, "extracting students for id "+object.getInt("id"));
        JSONArray students = object.getJSONArray(key);
        for (int i = 0; i < students.length(); i++) {
          JSONObject c = students.getJSONObject(i);
          LinkedHashMap<String, String> properties = new LinkedHashMap<>();
          properties.put("name", c.getString("name"));
          properties.put("surname", c.getString("surname"));
          properties.put("status", c.getString("status"));
          properties.put("grade", c.getString("grade"));
          returnee.add(new Student(c.getInt("id"), properties));
        }
      } catch (JSONException jse) {
        throw new DataParserException(jse.getMessage());
      }
    }
    return returnee;
  }

  private static int parseAge(JSONObject object) throws DataParserException {
    int age;
    try {
      age = object.getInt("age");
    } catch (Exception e) {
      age = 0;
    }
    return age;
  }

}
