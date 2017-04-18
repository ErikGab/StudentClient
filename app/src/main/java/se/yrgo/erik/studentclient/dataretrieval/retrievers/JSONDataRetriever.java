package se.yrgo.erik.studentclient.dataretrieval.retrievers;

import android.util.Log;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.yrgo.erik.studentclient.dataretrieval.retrievers.parseresponse.JSONDataParser;
import se.yrgo.erik.studentclient.formatables.Formatable;
import se.yrgo.erik.studentclient.dataretrieval.retrievers.parseresponse.DataParserException;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalException;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalService;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalService2;
import se.yrgo.erik.studentclient.dataretrieval.DataRetriever;
import se.yrgo.erik.studentclient.dataretrieval.serverconnection.ServerConnection;
import se.yrgo.erik.studentclient.dataretrieval.serverconnection.ServerConnectionException;

public class JSONDataRetriever implements DataRetriever {

  private static final String TAG = "JSONDataRetriever";
  private static HttpURLConnection serverConnection;

  static{
    Log.v(TAG, "static block running");
    DataRetrievalService2.register("json", new JSONDataRetriever());
    DataRetrievalService.register("json", new JSONDataRetriever());
  }

  private JSONDataRetriever() {}

  @Override
  public List<Formatable> allStudents() throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "student");
    getParams.put("by", "course");
    getParams.put("id", "all");
    getParams.put("format", "json");
    try {
      String response =
              ServerConnection.connectAndGET(DataRetrievalService.getServerURL(), getParams);
      return JSONDataParser.Json2Students(response);
    } catch (DataParserException dpe) {
      Log.v(TAG, "returning allStudents...Failed: " + dpe.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + dpe.getMessage());
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }

  @Override
  public List<Formatable> allStudentsInCourse(int id) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "student");
    getParams.put("by", "course");
    getParams.put("id", String.valueOf(id));
    getParams.put("format", "json");
    try {
      String response =
              ServerConnection.connectAndGET(DataRetrievalService.getServerURL(), getParams);
      return JSONDataParser.Json2Students(response);
    } catch (DataParserException dpe) {
      Log.v(TAG, "returning allStudents...Failed: " + dpe.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + dpe.getMessage());
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }

  @Override
  public List<Formatable> fullInfoForStudent(int studentId) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "student");
    getParams.put("id", String.valueOf(studentId));
    getParams.put("format", "json");
    try {
      String response =
              ServerConnection.connectAndGET(DataRetrievalService.getServerURL(), getParams);
      return JSONDataParser.Json2Students(response);
    } catch (DataParserException dpe) {
      Log.v(TAG, "returning allStudents...Failed: " + dpe.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + dpe.getMessage());
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }

  @Override
  public List<Formatable> allCourses() throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "course");
    getParams.put("by", "year");
    getParams.put("id", "all");
    getParams.put("format", "json");
    try {
      String response =
              ServerConnection.connectAndGET(DataRetrievalService.getServerURL(), getParams);
      return JSONDataParser.Json2Course(response);
    } catch (DataParserException dpe) {
      Log.v(TAG, "returning allStudents...Failed: " + dpe.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + dpe.getMessage());
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }

  @Override
  public List<Formatable> allCoursesInYear(int year) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "course");
    getParams.put("by", "year");
    getParams.put("format", "json");
    try {
      String response =
              ServerConnection.connectAndGET(DataRetrievalService.getServerURL(), getParams);
      return JSONDataParser.Json2Course(response);
    } catch (DataParserException dpe) {
      Log.v(TAG, "returning allStudents...Failed: " + dpe.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + dpe.getMessage());
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }

  @Override
  public List<Formatable> fullInfoForCourse(int courseId) throws DataRetrievalException {
    Map<String,String> getParams = new HashMap<>();
    getParams.put("type", "course");
    getParams.put("id", String.valueOf(courseId));
    getParams.put("format", "json");
    try {
      String response =
              ServerConnection.connectAndGET(DataRetrievalService.getServerURL(), getParams);
      return JSONDataParser.Json2Course(response);
    } catch (DataParserException dpe) {
      Log.v(TAG, "returning allStudents...Failed: " + dpe.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + dpe.getMessage());
    } catch (ServerConnectionException sce) {
      Log.v(TAG, "returning allStudents...Failed: " + sce.getMessage());
      throw new DataRetrievalException("returning allStudents...Failed: " + sce.getMessage());
    }
  }
}
