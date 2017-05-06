package se.yrgo.erik.studentclient.dataretrieval.retrievers;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalException;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalService;
import se.yrgo.erik.studentclient.dataretrieval.DataRetriever;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrieverFactory;
import se.yrgo.erik.studentclient.dataretrieval.serverconnection.ServerConnection;
import se.yrgo.erik.studentclient.dataretrieval.serverconnection.ServerConnectionException;

/**
 *  A DataRetriever that retrieves data from a ServerConnection by REST GET Requests.
 */
public class DefaultDataRetriever implements DataRetriever {

  private static final String TAG = "DefaultDataRetriever";
  private static HttpURLConnection serverConnection;
  private static URL serverURL;

  static{
    Log.v(TAG, "static block running");
    DataRetrieverFactory.register("defaultDR", new DefaultDataRetriever());
    try {
      //serverURL = new URL ("http://127.0.0.1:8080/StudentServiceAPI");
      serverURL = new URL("http://10.0.2.2:8080/StudentServiceAPI");
    } catch (MalformedURLException mue) {
      serverURL = null;
    }
  }

  private DefaultDataRetriever() {}

  /** Retrieves all Students from Server, Returns Students as a String in requested format
   *
   * @param format json or xml
   * @return students formatted in requested format
   * @throws DataRetrievalException
   */
  @Override
  public String allStudents(String format) throws DataRetrievalException {
    //Note to self. Shouldn't this method just just use allStudentsInCourse() ...
    //return allStudentsInCourse(format, 0); ...since StudentAPI is sneaky and also takes a 0 as all
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "student");
    getParams.put("by", "course");
    getParams.put("id", "all");
    getParams.put("format", format);
    try {
      return ServerConnection.connectAndGET(serverURL, getParams);
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }

  /** Retrieves all Students in a Specific course from Server.
   * Returns Students as a String in requested format
   *
   * @param format json or xml
   * @param id courseId
   * @return students formatted in requested format
   * @throws DataRetrievalException
   */
  @Override
  public String allStudentsInCourse(String format, int id) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "student");
    getParams.put("by", "course");
    getParams.put("id", String.valueOf(id));
    getParams.put("format", format);
    try {
      return ServerConnection.connectAndGET(serverURL, getParams);
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }

  /** Retrieves all details about a specific Student from Server.
   * Returns Students as a String in requested format
   *
   * @param format json or xml
   * @param studentId studentId
   * @return students formatted in requested format
   * @throws DataRetrievalException
   */
  @Override
  public String fullInfoForStudent(String format, int studentId) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "student");
    getParams.put("id", String.valueOf(studentId));
    getParams.put("format", format);
    try {
      return ServerConnection.connectAndGET(serverURL, getParams);
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }

  /** Retrieves all Courses from Server.
   * Returns Courses as a String in requested format
   *
   * @param format json or xml
   * @return courses formatted in requested format
   * @throws DataRetrievalException
   */
  @Override
  public String allCourses(String format) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "course");
    getParams.put("by", "year");
    getParams.put("id", "all");
    getParams.put("format", format);
    try {
      return ServerConnection.connectAndGET(serverURL, getParams);
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }

  /** Retrieves all Courses in a specific year from Server.
   * Returns Courses as a String in requested format
   *
   * @param format json or xml
   * @param year requested year as four digit integer (YYYY) ie 2016
   * @return courses formatted in requested format
   * @throws DataRetrievalException
   */
  @Override
  public String allCoursesInYear(String format, int year) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "course");
    getParams.put("by", "year");
    getParams.put("format", format);
    try {
      return ServerConnection.connectAndGET(serverURL, getParams);
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }

  /** Retrieves all details about a specific Course from Server.
   * Returns Courses as a String in requested format
   *
   * @param format json or xml
   * @param courseId int
   * @return courses formatted in requested format
   * @throws DataRetrievalException
   */
  @Override
  public String fullInfoForCourse(String format, int courseId) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "course");
    getParams.put("id", String.valueOf(courseId));
    getParams.put("format", format);
    try {
      return ServerConnection.connectAndGET(serverURL, getParams);
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }
}
