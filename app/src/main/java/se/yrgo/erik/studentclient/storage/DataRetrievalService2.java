package se.yrgo.erik.studentclient.storage;

import android.os.AsyncTask;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.yrgo.erik.studentclient.formatables.Formatable;
import se.yrgo.erik.studentclient.main.Session;

public class DataRetrievalService2 {

  private static DataRetrievalService2 instance;
  private static URL serverURL;
  private static String format = "json";
  private static String classNames[] = {
          "se.yrgo.erik.studentclient.storage.JSONMockDataRetriever",
          "se.yrgo.erik.studentclient.storage.JSONDataRetriever"
  };
  private static Map<String,DataRetriever> retrievers;
  private static final String TAG = "DataRetrievalService2";

  private GetList getList;
  private int id2Update;
  private List<Formatable> tmpList;
  private DataRetrievalClient client;
  private GETTYPE requestedData;

  static {
    Log.v(TAG, "static block running");
    instance = new DataRetrievalService2();
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

  private DataRetrievalService2() {}

  public static DataRetrievalService2 getInstance() {
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

  private class SafeGet extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params) {
      try {
        if (getList != null) {
          tmpList = getList.getList(id2Update);
        }
      } catch (DataRetrievalException dre) {
        Log.v(TAG, "onCreate: Failed to retrieve items!\n" + dre.getMessage());
        client.recieveFromService(null, GETTYPE.ERROR);
        this.cancel(true);
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      super.onPostExecute(result);
      if (tmpList.size() > 0) {
        client.recieveFromService(tmpList, requestedData);
      } else {
        client.recieveFromService(null, GETTYPE.ERROR);
      }
    }
  }

  public void requestData(GETTYPE requestedData, int id, DataRetrievalClient client){

    Map<GETTYPE,GetList> getMethods = new HashMap<>();
    getMethods.put(GETTYPE.ALL_COURSES,            (i) -> allCourses());
    getMethods.put(GETTYPE.ALL_COURSES_IN_YEAR,    (i) -> allCoursesInYear(id));
    getMethods.put(GETTYPE.FULL_INFO_FOR_COURSE,   (i) -> fullInfoForCourse(id));
    getMethods.put(GETTYPE.ALL_STUDENTS,           (i) -> allStudents());
    getMethods.put(GETTYPE.ALL_STUDENTS_IN_COURSE, (i) -> allStudentsInCourse(id));
    getMethods.put(GETTYPE.FULL_INFO_FOR_STUDENT,  (i) -> fullInfoForStudent(id));

    if (getMethods.containsKey(requestedData)) {
      getList = getMethods.get(requestedData);
      id2Update = id;
      this.requestedData = requestedData;
      this.client = client;
      new SafeGet().execute();
    } else {
      client.recieveFromService(null, GETTYPE.ERROR);
    }
  }

  public static enum GETTYPE {
    ALL_STUDENTS,
    ALL_STUDENTS_IN_COURSE,
    FULL_INFO_FOR_STUDENT,
    ALL_COURSES,
    ALL_COURSES_IN_YEAR,
    FULL_INFO_FOR_COURSE,
    ERROR;
  }

  private interface GetList {
    public List<Formatable> getList(int id) throws  DataRetrievalException;
  }

  private List<Formatable> allStudents() throws DataRetrievalException {
    if (retrievers.containsKey(format)) {
      Log.v(TAG, "Returning allStudents retrieved in format " + format);
      return retrievers.get(format).allStudents();
    } else {
      Log.v(TAG, "No retriever found for format " + format);
      throw new DataRetrievalException("No retriever found for format " + format);
    }
  }

  private List<Formatable> allStudentsInCourse(int id) throws DataRetrievalException {
    if (retrievers.containsKey(format)) {
      Log.v(TAG, "Returning allStudentsInCourse " + id + " retrieved in format " + format);
      return retrievers.get(format).allStudentsInCourse(id);
    } else {
      Log.v(TAG, "No retriever found for format " + format);
      throw new DataRetrievalException("No retriever found for format "+format);
    }
  }

  private List<Formatable> fullInfoForStudent(int studentId) throws DataRetrievalException {
    if (retrievers.containsKey(format)) {
      Log.v(TAG, "Returning fullInfoForStudent " + studentId + " retrieved in format " + format);
      return retrievers.get(format).fullInfoForStudent(studentId);
    } else {
      Log.v(TAG, "No retriever found for format " + format);
      throw new DataRetrievalException("No retriever found for format " + format);
    }
  }

  private List<Formatable> allCourses() throws DataRetrievalException {
    if (retrievers.containsKey(format)) {
      Log.v(TAG, "Returning allCourses retrieved in format " + format);
      return retrievers.get(format).allCourses();
    } else {
      Log.v(TAG, "No retriever found for format " + format);
      throw new DataRetrievalException("No retriever found for format " + format);
    }
  }

  private List<Formatable> allCoursesInYear(int year) throws DataRetrievalException {
    if (retrievers.containsKey(format)) {
      Log.v(TAG, "Returning allCoursesInYear " + year + " retrieved in format " + format);
      return retrievers.get(format).allCoursesInYear(year);
    } else {
      Log.v(TAG, "No retriever found for format " + format);
      throw new DataRetrievalException("No retriever found for format " + format);
    }
  }

  private List<Formatable> fullInfoForCourse(int courseId) throws DataRetrievalException {
    if (retrievers.containsKey(format)) {
      Log.v(TAG, "Returning fullInfoForCourse with id " + courseId + " retrieved in format " +
              format);
      return retrievers.get(format).fullInfoForCourse(courseId);
    } else {
      Log.v(TAG, "No retriever found for format " + format);
      throw new DataRetrievalException("No retriever found for format " + format);
    }
  }

}
