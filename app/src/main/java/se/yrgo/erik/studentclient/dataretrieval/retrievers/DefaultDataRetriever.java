package se.yrgo.erik.studentclient.dataretrieval.retrievers;

import android.util.Log;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalException;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalService;
import se.yrgo.erik.studentclient.dataretrieval.DataRetriever;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrieverFactory;
import se.yrgo.erik.studentclient.dataretrieval.serverconnection.ServerConnection;
import se.yrgo.erik.studentclient.dataretrieval.serverconnection.ServerConnectionException;

public class DefaultDataRetriever implements DataRetriever {

  private static final String TAG = "DefaultDataRetriever";
  private static HttpURLConnection serverConnection;

  static{
    Log.v(TAG, "static block running");
    DataRetrieverFactory.register("default", new DefaultDataRetriever());
  }

  private DefaultDataRetriever() {}

  @Override
  public String allStudents(String format) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "student");
    getParams.put("by", "course");
    getParams.put("id", "all");
    getParams.put("format", format);
    try {
      return ServerConnection.connectAndGET(DataRetrievalService.getServerURL(), getParams);
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }

  @Override
  public String allStudentsInCourse(String format, int id) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "student");
    getParams.put("by", "course");
    getParams.put("id", String.valueOf(id));
    getParams.put("format", format);
    try {
      return ServerConnection.connectAndGET(DataRetrievalService.getServerURL(), getParams);
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }

  @Override
  public String fullInfoForStudent(String format, int studentId) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "student");
    getParams.put("id", String.valueOf(studentId));
    getParams.put("format", format);
    try {
      return ServerConnection.connectAndGET(DataRetrievalService.getServerURL(), getParams);
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }

  @Override
  public String allCourses(String format) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "course");
    getParams.put("by", "year");
    getParams.put("id", "all");
    getParams.put("format", format);
    try {
      return ServerConnection.connectAndGET(DataRetrievalService.getServerURL(), getParams);
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }

  @Override
  public String allCoursesInYear(String format, int year) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "course");
    getParams.put("by", "year");
    getParams.put("format", format);
    try {
      return ServerConnection.connectAndGET(DataRetrievalService.getServerURL(), getParams);
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }

  @Override
  public String fullInfoForCourse(String format, int courseId) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "course");
    getParams.put("id", String.valueOf(courseId));
    getParams.put("format", format);
    try {
      return ServerConnection.connectAndGET(DataRetrievalService.getServerURL(), getParams);
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }
}
