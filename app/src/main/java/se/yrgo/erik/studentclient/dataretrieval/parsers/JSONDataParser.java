package se.yrgo.erik.studentclient.dataretrieval.parsers;
import android.util.Log;

import se.yrgo.erik.studentclient.dataretrieval.DataParser;
import se.yrgo.erik.studentclient.dataretrieval.DataParserFactory;
import se.yrgo.erik.studentclient.formatables.Course;
import se.yrgo.erik.studentclient.formatables.Formatable;
import se.yrgo.erik.studentclient.formatables.FormatableItem;
import se.yrgo.erik.studentclient.formatables.ItemType;
import se.yrgo.erik.studentclient.formatables.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class JSONDataParser implements DataParser {

  private static final String TAG = "JSONDataParser";

  static {
    DataParserFactory.register("json", new JSONDataParser());
  }

  private JSONDataParser() {}

  /** Converts a json string (containing Student data) to a list of Formatables
   *
   * @param json raw json data as a tring
   * @return a list of formatables (Student)
   * @throws DataParserException
   */
  public List<Formatable> string2Students(String json)  throws DataParserException {
    Log.v(TAG, "string2Students");
    List<Formatable> returnee = new ArrayList<Formatable>();
    int skippedItems = 0;
    try {
      JSONObject jsonObj = new JSONObject(json);
      String type = "student";
      if (jsonObj.has(type)) {
        JSONArray items = jsonObj.getJSONArray(type);
        for (int i = 0; i < items.length(); i++) {
          JSONObject current = items.getJSONObject(i);
          try {
            if (current.has("id") &&
                    current.has("name") &&
                    current.has("surname") &&
                    current.has("age") &&
                    current.has("postAddress") &&
                    current.has("streetAddress")) {
              returnee.add(new Student(current.getInt("id"),
                      current.getString("name"),
                      current.getString("surname"),
                      parseAge(current),
                      current.getString("postAddress"),
                      current.getString("streetAddress"),
                      extractPhoneNumber(current),
                      extractCourses(current)));
            } else if (current.has("id") && current.has("name") && current.has("surname")) {
              returnee.add(new Student(current.getInt("id"),
                      current.getString("name"),
                      current.getString("surname")));
            } else {
              skippedItems++;
            }
          } catch (Exception e){
            throw new DataParserException( e.getMessage());
          }
        }
      }

    } catch ( JSONException jse){
      Log.v(TAG, "string2Students catching JSONExeption " + jse.getMessage());
    } catch ( NullPointerException npe) {
      Log.v(TAG, "string2Students: null or empty json string, nothing to parse here... moving on.");
    }
    Log.v(TAG, "string2Students returning " + returnee.size() + " students, " + skippedItems +
            " items skipped.");
    return returnee;
  }

  /** Converts a json string (containing Course data) to a list of Formatables
   *
   * @param json raw json data as a tring
   * @return a list of formatables (Course)
   * @throws DataParserException
   */
  public List<Formatable> string2Courses(String json) throws DataParserException {
    Log.v(TAG, "string2Courses");
    List<Formatable> returnee = new ArrayList<Formatable>();
    int skippedItems = 0;
    try {
      JSONObject jsonObj = new JSONObject(json);
      String type = "course";
      if ( jsonObj.has(type)) {
        JSONArray items = jsonObj.getJSONArray(type);
        for (int i = 0; i < items.length(); i++) {
          JSONObject current = items.getJSONObject(i);
          try {
            if (current.has("id") &&
                    current.has("name") &&
                    current.has("description") &&
                    current.has("startDate") &&
                    current.has("endDate") &&
                    current.has("points")) {
              returnee.add(new Course(current.getInt("id"),
                      current.getString("startDate"),
                      current.getString("endDate"),
                      current.getString("name"),
                      current.getInt("points"),
                      current.getString("description"),
                      extractStudents(current)));
            } else if (current.has("id") && current.has("name")) {
              returnee.add(new Course(current.getInt("id"), current.getString("name")));
            } else {
              skippedItems++;
            }
          } catch (Exception e) {
            throw new DataParserException( e.getMessage());
          }
        }
      }

    } catch ( JSONException jse){
    Log.v(TAG, "string2Courses catching JSONExeption " + jse.getMessage());
  } catch ( NullPointerException npe) {
    Log.v(TAG, "string2Courses: null or empty json string, nothing to parse here... moving on.");
  }
    Log.v(TAG, "string2Courses returning " + returnee.size() + " courses, " + skippedItems +
            " items skipped.");
    return returnee;
  }

  private static FormatableItem extractPhoneNumber(JSONObject student) throws DataParserException {
    FormatableItem returnee = null;
    String key = "phonenumbers";
    if (student.has(key)) {
      try {
        Log.v(TAG, "extracting phonenumbers for id "+student.getInt("id"));
        JSONArray numbers = student.getJSONArray(key);
        LinkedHashMap<String, String> phoneNumbers = new LinkedHashMap<>();
        for (int i = 0; i < numbers.length(); i++) {
          JSONObject current = numbers.getJSONObject(i);
          if (current.has("mobile")) {
            phoneNumbers.put("mobile", current.getString("mobile"));
          }
          if (current.has("home")) {
            phoneNumbers.put("home", current.getString("home"));
          }
          if (current.has("fax")) {
            phoneNumbers.put("fax", current.getString("fax"));
          }
          if (current.has("work")) {
            phoneNumbers.put("work", current.getString("work"));
          }
        }
        returnee = new FormatableItem(ItemType.PHONENUMBERS, 0, phoneNumbers);
      } catch (JSONException jse) {
        throw new DataParserException(jse.getMessage());
      }
    }
    return returnee;
  }

  private static List<Course> extractCourses(JSONObject student) throws DataParserException {
    List<Course> returnee = new ArrayList<>();
    String key = "course";
    if (student.has(key)) {
      try {
        Log.v(TAG, "extracting courses for id "+student.getInt("id"));
        JSONArray courses = student.getJSONArray(key);
        for (int i = 0; i < courses.length(); i++) {
          JSONObject current = courses.getJSONObject(i);
          LinkedHashMap<String, String> properties = new LinkedHashMap<>();
          properties.put("name", current.getString("name"));
          properties.put("status", current.getString("status"));
          properties.put("grade", current.getString("grade"));
          returnee.add(new Course(current.getInt("id"), properties));
        }
      } catch (JSONException jse){
        throw new DataParserException(jse.getMessage());
      }
    }
    return returnee;
  }

  private static List<Student> extractStudents(JSONObject course) throws DataParserException {
    List<Student> returnee = new ArrayList<>();
    String key = "student";
    if (course.has(key)) {
      try {
        Log.v(TAG, "extracting students for id "+course.getInt("id"));
        JSONArray students = course.getJSONArray(key);
        for (int i = 0; i < students.length(); i++) {
          JSONObject current = students.getJSONObject(i);
          LinkedHashMap<String, String> properties = new LinkedHashMap<>();
          properties.put("name", current.getString("name"));
          properties.put("surname", current.getString("surname"));
          properties.put("status", current.getString("status"));
          properties.put("grade", current.getString("grade"));
          returnee.add(new Student(current.getInt("id"), properties));
        }
      } catch (JSONException jse) {
        throw new DataParserException(jse.getMessage());
      }
    }
    return returnee;
  }

  private static int parseAge(JSONObject student) throws DataParserException {
    int age;
    try {
      age = student.getInt("age");
    } catch (Exception e) {
      age = 0;
    }
    return age;
  }

}
