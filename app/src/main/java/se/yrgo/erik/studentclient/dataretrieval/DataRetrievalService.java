package se.yrgo.erik.studentclient.dataretrieval;

import android.util.Log;

import se.yrgo.erik.studentclient.main.Session;
import se.yrgo.erik.studentclient.formatables.Formatable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataRetrievalService implements DataRetriever {

  private static DataRetrievalService instance;
  private static URL serverURL;
  private static String format = "json";
  private static String classNames[] = {
          "se.yrgo.erik.studentclient.dataretrieval.retrievers.JSONMockDataRetriever",
          "se.yrgo.erik.studentclient.dataretrieval.retrievers.JSONDataRetriever"
  };
  private static Map<String,DataRetriever> retrievers;
  private static final String TAG = "DataRetrievalService";

  static {
    Log.v(TAG, "static block running");
    instance = new DataRetrievalService();
    retrievers = new HashMap<>();
    try{
      for (String className : classNames) {
        Log.v(TAG, "loading class: " + className);
        Class.forName(className);
      }
    }catch(ClassNotFoundException cnfe){
      System.err.println(cnfe.getMessage());
    }
    try {
      //serverURL = new URL ("http://127.0.0.1:8080/StudentServiceAPI");
      serverURL = new URL ("http://10.0.2.2:8080/StudentServiceAPI");
    } catch (MalformedURLException mue) {
      serverURL = null;
    }
  }

  private DataRetrievalService() {};

  public static DataRetrievalService getInstance() {
    return instance;
  }

  public static void register(String format, DataRetriever retriever) {
    Log.v(TAG, "registering: "+format);
    retrievers.put(format, retriever);
  }

  public static void setFormat(String requestedFormat) {
    format = requestedFormat;
    Session.getInstance().format = requestedFormat;
  }

  public static URL getServerURL() {
    return serverURL;
  }

  @Override
  public List<Formatable> allStudents() throws DataRetrievalException {
    if (retrievers.containsKey(format)) {
      Log.v(TAG, "Returning allStudents retrieved in format " + format);
      return retrievers.get(format).allStudents();
    } else {
      Log.v(TAG, "No retriever found for format " + format);
      throw new DataRetrievalException("No retriever found for format " + format);
    }
  }

  @Override
  public List<Formatable> allStudentsInCourse(int id) throws DataRetrievalException {
    if (retrievers.containsKey(format)) {
      Log.v(TAG, "Returning allStudentsInCourse " + id + " retrieved in format " + format);
      return retrievers.get(format).allStudentsInCourse(id);
    } else {
      Log.v(TAG, "No retriever found for format " + format);
      throw new DataRetrievalException("No retriever found for format "+format);
    }
  }

  @Override
  public List<Formatable> fullInfoForStudent(int studentId) throws DataRetrievalException {
    if (retrievers.containsKey(format)) {
      Log.v(TAG, "Returning fullInfoForStudent " + studentId + " retrieved in format " + format);
      return retrievers.get(format).fullInfoForStudent(studentId);
    } else {
      Log.v(TAG, "No retriever found for format " + format);
      throw new DataRetrievalException("No retriever found for format " + format);
    }
  }

  @Override
  public List<Formatable> allCourses() throws DataRetrievalException {
    if (retrievers.containsKey(format)) {
      Log.v(TAG, "Returning allCourses retrieved in format " + format);
      return retrievers.get(format).allCourses();
    } else {
      Log.v(TAG, "No retriever found for format " + format);
      throw new DataRetrievalException("No retriever found for format " + format);
    }
  }

  @Override
  public List<Formatable> allCoursesInYear(int year) throws DataRetrievalException {
    if (retrievers.containsKey(format)) {
      Log.v(TAG, "Returning allCoursesInYear " + year + " retrieved in format " + format);
      return retrievers.get(format).allCoursesInYear(year);
    } else {
      Log.v(TAG, "No retriever found for format " + format);
      throw new DataRetrievalException("No retriever found for format " + format);
    }
  }

  @Override
  public List<Formatable> fullInfoForCourse(int courseId) throws DataRetrievalException {
    if (retrievers.containsKey(format)) {
      Log.v(TAG, "Returning fullInfoForCourse with id " + courseId + " retrieved in format " +
              format);
      try {
        List<Formatable> returnee = retrievers.get(format).fullInfoForCourse(courseId);
        //CACHEDATA.add(returnee);
        Session.getInstance().cachedData = false;
        return returnee;
      } catch (DataRetrievalException dre) {
        //returnee = CACHEDATA.get(BLA BLA BLA)
        Session.getInstance().cachedData = true;
        return null;
      }
    } else {
      Log.v(TAG, "No retriever found for format " + format);
      throw new DataRetrievalException("No retriever found for format " + format);
    }
  }

}
