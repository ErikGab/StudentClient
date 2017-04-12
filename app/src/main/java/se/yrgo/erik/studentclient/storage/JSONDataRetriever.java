package se.yrgo.erik.studentclient.storage;

import android.util.Log;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.yrgo.erik.studentclient.formatables.Course;
import se.yrgo.erik.studentclient.formatables.Student;
import se.yrgo.erik.studentclient.parseresponse.DataParserException;
import se.yrgo.erik.studentclient.parseresponse.DataParserUtil;

public class JSONDataRetriever implements DataRetriever {

  private static final String TAG = "JSONDataRetriever";
  private static HttpURLConnection serverConnection;

  static{
    Log.v(TAG, "static block running");
    DataRetievalService.register("json", new JSONDataRetriever());
  }

  private JSONDataRetriever() {}

  @Override
  public List<Student> allStudents() throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "student");
    getParams.put("by", "course");
    getParams.put("id", "all");
    getParams.put("format", "json");
    try {
      String response =
              ServerConnection.connectAndGET(DataRetievalService.getServerURL(), getParams);
      return DataParserUtil.Json2Students(response);
    } catch (DataParserException dpe) {
      //TO DO: Hantera detta bättre!
      Log.v(TAG, "returning allStudents...Failed: " + dpe.getMessage());
      return new ArrayList<Student>();
    } catch (ServerConnectionException sce) {
      //TO DO: Hantera detta bättre!
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      return new ArrayList<Student>();
    }
  }

  @Override
  public List<Student> allStudentsInCourse(int id) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "student");
    getParams.put("by", "course");
    getParams.put("id", String.valueOf(id));
    getParams.put("format", "json");
    try {
      String response =
              ServerConnection.connectAndGET(DataRetievalService.getServerURL(), getParams);
      return DataParserUtil.Json2Students(response);
    } catch (DataParserException dpe) {
      //TO DO: Hantera detta bättre!
      Log.v(TAG, "returning allStudents...Failed: " + dpe.getMessage());
      return new ArrayList<Student>();
    } catch (ServerConnectionException sce) {
      //TO DO: Hantera detta bättre!
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      return new ArrayList<Student>();
    }
  }

  @Override
  public List<Student> fullInfoForStudent(int studentId) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "student");
    getParams.put("id", String.valueOf(studentId));
    getParams.put("format", "json");
    try {
      String response =
              ServerConnection.connectAndGET(DataRetievalService.getServerURL(), getParams);
      return DataParserUtil.Json2Students(response);
    } catch (DataParserException dpe) {
      //TO DO: Hantera detta bättre!
      Log.v(TAG, "returning allStudents...Failed: " + dpe.getMessage());
      return new ArrayList<Student>();
    } catch (ServerConnectionException sce) {
      //TO DO: Hantera detta bättre!
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      return new ArrayList<Student>();
    }
  }

  @Override
  public List<Course> allCourses() throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "course");
    getParams.put("format", "json");
    try {
      String response =
              ServerConnection.connectAndGET(DataRetievalService.getServerURL(), getParams);
      return DataParserUtil.Json2Course(response);
    } catch (DataParserException dpe) {
      //TO DO: Hantera detta bättre!
      Log.v(TAG, "returning allStudents...Failed: " + dpe.getMessage());
      return new ArrayList<Course>();
    } catch (ServerConnectionException sce) {
      //TO DO: Hantera detta bättre!
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      return new ArrayList<Course>();
    }
  }

  @Override
  public List<Course> allCoursesInYear(int year) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "course");
    getParams.put("by", "year");
    getParams.put("format", "json");
    try {
      String response =
              ServerConnection.connectAndGET(DataRetievalService.getServerURL(), getParams);
      return DataParserUtil.Json2Course(response);
    } catch (DataParserException dpe) {
      //TO DO: Hantera detta bättre!
      Log.v(TAG, "returning allStudents...Failed: " + dpe.getMessage());
      return new ArrayList<Course>();
    } catch (ServerConnectionException sce) {
      //TO DO: Hantera detta bättre!
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      return new ArrayList<Course>();
    }
  }

  @Override
  public List<Course> fullInfoForCourse(int courseId) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "course");
    getParams.put("id", String.valueOf(courseId));
    getParams.put("format", "json");
    try {
      String response =
              ServerConnection.connectAndGET(DataRetievalService.getServerURL(), getParams);
      return DataParserUtil.Json2Course(response);
    } catch (DataParserException dpe) {
      //TO DO: Hantera detta bättre!
      Log.v(TAG, "returning allStudents...Failed: " + dpe.getMessage());
      return new ArrayList<Course>();
    } catch (ServerConnectionException sce) {
      //TO DO: Hantera detta bättre!
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      return new ArrayList<Course>();
    }
  }
}
