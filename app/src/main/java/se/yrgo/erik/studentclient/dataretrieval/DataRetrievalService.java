package se.yrgo.erik.studentclient.dataretrieval;

import android.util.Log;

import se.yrgo.erik.studentclient.dataretrieval.CacheDB.CacheDB;
import se.yrgo.erik.studentclient.dataretrieval.parsers.DataParserException;
import se.yrgo.erik.studentclient.main.Session;
import se.yrgo.erik.studentclient.formatables.Formatable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataRetrievalService {

  private static DataRetrievalService instance;
  private static URL serverURL;
  private static String format = "json";
  private static final String TAG = "DataRetrievalService";
  private static CacheDB cache;

  static {
    Log.v(TAG, "static block running");
    instance = new DataRetrievalService();
    cache = new CacheDB(Session.getInstance().context);
    cache.open();
    try {
      //serverURL = new URL ("http://127.0.0.1:8080/StudentServiceAPI");
      serverURL = new URL ("http://10.0.2.2:8080/StudentServiceAPI");
    } catch (MalformedURLException mue) {
      serverURL = null;
    }
  }

  private DataRetrievalService() {}

  public static DataRetrievalService getInstance() {
    return instance;
  }

  public static void setFormat(String requestedFormat) {
    format = requestedFormat;
  }

  public static String getFormat() {
    return format;
  }

  public static URL getServerURL() {
    return serverURL;
  }

  public List<Formatable> allStudents() throws DataRetrievalException {
    Log.v(TAG, "Returning allStudents retrieved in format " + format);
    String response = "";
    try {
      response = DataRetrieverFactory.getRetriever().allStudents(format);
      cache.addResponse(response, "allStudents", format, "student");
      Session.getInstance().cachedData = false;
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "FAILED TO GET NEW DATA FROM SERVER. USING OLD DATA FROM CACHE");
      response = cache.getResponse("allStudents").get("response");
      Session.getInstance().cachedData = true;
    }
    try {
      return DataParserFactory.getParser(format).string2Students(response);
    } catch (DataParserException dpe) {
      Log.v(TAG, "FAILED TO PARSE DATA");
      throw new DataRetrievalException(dpe.getMessage());
    }
  }

  public List<Formatable> allStudentsInCourse(int id) throws DataRetrievalException {
    Log.v(TAG, "Returning allStudentsInCourse " + id + " retrieved in format " + format);
    String parseFormat = format;
    String response = "";
    try {
      response = DataRetrieverFactory.getRetriever().allStudentsInCourse(format, id);
      cache.addResponse(response, "allStudentsInCourse" + id, format, "student");
      Session.getInstance().cachedData = false;
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "FAILED TO GET NEW DATA FROM SERVER. USING OLD DATA FROM CACHE");
      Map<String,String> cachedata = cache.getResponse("allStudentsInCourse" + id);
      response = cachedata.get("response");
      parseFormat = cachedata.get("contentType");
      Session.getInstance().cachedData = true;
    }
    try {
      return DataParserFactory.getParser(parseFormat).string2Students(response);
    } catch (DataParserException dpe) {
      Log.v(TAG, "FAILED TO PARSE DATA");
      throw new DataRetrievalException(dpe.getMessage());
    }
  }

  public List<Formatable> fullInfoForStudent(int id) throws DataRetrievalException {
    Log.v(TAG, "Returning fullInfoForStudent " + id + " retrieved in format " + format);
    String parseFormat = format;
    String response = "";
    try {
      response = DataRetrieverFactory.getRetriever().fullInfoForStudent(format, id);
      cache.addResponse(response, "fullInfoForStudent" + id, format, "student");
      Session.getInstance().cachedData = false;
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "FAILED TO GET NEW DATA FROM SERVER. USING OLD DATA FROM CACHE");
      Map<String,String> cachedata = cache.getResponse("fullInfoForStudent" + id );
      response = cachedata.get("response");
      parseFormat = cachedata.get("contentType");
      Session.getInstance().cachedData = true;
    }
    try {
      return DataParserFactory.getParser(parseFormat).string2Students(response);
    } catch (DataParserException dpe) {
      Log.v(TAG, "FAILED TO PARSE DATA");
      throw new DataRetrievalException(dpe.getMessage());
    }
  }

  public List<Formatable> allCourses() throws DataRetrievalException {
    Log.v(TAG, "Returning allCourses retrieved in format " + format);
    String parseFormat = format;
    String response = "";
    try {
      response = DataRetrieverFactory.getRetriever().allCourses(format);
      cache.addResponse(response, "allCourses", format, "course");
      Session.getInstance().cachedData = false;
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "FAILED TO GET NEW DATA FROM SERVER. USING OLD DATA FROM CACHE");
      Map<String,String> cachedata = cache.getResponse("allCourses");
      response = cachedata.get("response");
      parseFormat = cachedata.get("contentType");
      Session.getInstance().cachedData = true;
    }
    try {
      return DataParserFactory.getParser(parseFormat).string2Courses(response);
    } catch (DataParserException dpe) {
      Log.v(TAG, "FAILED TO PARSE DATA");
      throw new DataRetrievalException(dpe.getMessage());
    }
  }

  public List<Formatable> allCoursesInYear(int year) throws DataRetrievalException {
    Log.v(TAG, "Returning allCoursesInYear " + year + " retrieved in format " + format);
    String parseFormat = format;
    String response = "";
    try {
      response = DataRetrieverFactory.getRetriever().allCoursesInYear(format, year);
      cache.addResponse(response, "allCoursesInYear" + year, format, "course");
      Session.getInstance().cachedData = false;
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "FAILED TO GET NEW DATA FROM SERVER. USING OLD DATA FROM CACHE");
      Map<String,String> cachedata = cache.getResponse("allCoursesInYear" + year);
      response = cachedata.get("response");
      parseFormat = cachedata.get("contentType");
      Session.getInstance().cachedData = true;
    }
    try {
      return DataParserFactory.getParser(parseFormat).string2Courses(response);
    } catch (DataParserException dpe) {
      Log.v(TAG, "FAILED TO PARSE DATA");
      throw new DataRetrievalException(dpe.getMessage());
    }
  }

  public List<Formatable> fullInfoForCourse(int id) throws DataRetrievalException {
    Log.v(TAG, "Returning fullInfoForCourse with id " + id + " retrieved in format " + format);
    String parseFormat = format;
    String response = "";
    try {
      response = DataRetrieverFactory.getRetriever().fullInfoForCourse(format, id);
      cache.addResponse(response, "fullInfoForCourse" + id, format, "course");
      Session.getInstance().cachedData = false;
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "FAILED TO GET NEW DATA FROM SERVER. USING OLD DATA FROM CACHE");
      Map<String,String> cachedata = cache.getResponse("fullInfoForCourse" + id);
      response = cachedata.get("response");
      parseFormat = cachedata.get("contentType");
      Session.getInstance().cachedData = true;
    }
    try {
      return DataParserFactory.getParser(parseFormat).string2Courses(response);
    } catch (DataParserException dpe) {
      Log.v(TAG, "FAILED TO PARSE DATA");
      throw new DataRetrievalException(dpe.getMessage());
    }
  }

  //NOT IN USE YET... SHOULD BE USED BY METHODS ABOVE TO DRY CODE
  private List<Formatable> retrieve(String type, int id) throws DataRetrievalException {
    Log.v(TAG, "Retrieving data...");
    String response = "";
    DataRetriever dR = DataRetrieverFactory.getRetriever();
    try {
      response = dR.fullInfoForCourse(format, id);
      cache.addResponse(response, "fullInfoForCourse" + id, format, "course");
      Session.getInstance().cachedData = false;
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "FAILED TO GET NEW DATA FROM SERVER. USING OLD DATA FROM CACHE");
      Map<String, String> cachedResponse = cache.getResponse("fullInfoForCourse" + id);
      response = cachedResponse.get("response");
      Session.getInstance().cachedData = true;
    }
    try {
      return DataParserFactory.getParser(format).string2Courses(response);
    } catch (DataParserException dpe) {
      Log.v(TAG, "FAILED TO PARSE DATA");
      throw new DataRetrievalException(dpe.getMessage());
    }
  }

  public void clearCache() {
    cache.clearCache();
  }

  public int cacheSize() {
    return cache.getSize();
  }

}
