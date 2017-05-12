package se.yrgo.erik.studentclient.dataretrieval;

import android.util.Log;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import se.yrgo.erik.studentclient.dataretrieval.CacheDB.CacheDB;
import se.yrgo.erik.studentclient.dataretrieval.parsers.DataParserException;
import se.yrgo.erik.studentclient.formatables.Formatable;
import se.yrgo.erik.studentclient.main.Session;

public class DataRetrievalService {

  private static DataRetrievalService instance;

  private static String format = "json"; // Note to self. Shoud I change this to enum?
  private static final String TAG = "DataRetrievalService";
  private static String dataRetriever = null;
  private static CacheDB cache;

  static {
    Log.v(TAG, "static block running");
    instance = new DataRetrievalService();
    cache = new CacheDB(Session.getInstance().context);
    //cache.open();
  }

  private DataRetrievalService() {}

  public static DataRetrievalService getInstance() {
    return instance;
  }

  /** Sets the format that DataRetrievalService requests from the DataRetrievers the data will be
   * parsed accordingly. Default is if no format is set is "json"
   *
   * @param requestedFormat "json" or "xml"
   */
  public static void setFormat(String requestedFormat) {
    format = requestedFormat;
  }

  /** Returns the current format that DataRetrievalService requests from the DataRetriever.
   *
   * @return
   */
  public static String getFormat() {
    return format;
  }

  /** Retrieves all students from a Dataretriever and parse to a List of formatables.
   *
   * @return list of formatables (Student)
   * @throws DataRetrievalException
   */
  public List<Formatable> allStudents() throws DataRetrievalException {
    Log.v(TAG, "Returning allStudents retrieved in format " + format);
    String response = "";
    try {
      response = getRetriever().allStudents(format);
      cache.addResponse(response, "allStudents", format, "student");
      Session.getInstance().cachedData = false;
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "FAILED TO GET NEW DATA FROM SOURCE. USING OLD DATA FROM CACHE");
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

  /** Retrieves students in a specific course from a Dataretriever and
   *  parse to a List of formatables.
   *
   * @param id courseId
   * @return list of formatables (Student)
   * @throws DataRetrievalException
   */
  public List<Formatable> allStudentsInCourse(int id) throws DataRetrievalException {
    Log.v(TAG, "Returning allStudentsInCourse " + id + " retrieved in format " + format);
    String parseFormat = format;
    String response = "";
    try {
      response = getRetriever().allStudentsInCourse(format, id);
      cache.addResponse(response, "allStudentsInCourse" + id, format, "student");
      Session.getInstance().cachedData = false;
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "FAILED TO GET NEW DATA FROM SOURCE. USING OLD DATA FROM CACHE");
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

  /** Retrieves full information for a specific student from a Dataretriever
   *  and parse to a List of formatables.
   *
   * @param id studentId
   * @return list of formatables (Student)
   * @throws DataRetrievalException
   */
  public List<Formatable> fullInfoForStudent(int id) throws DataRetrievalException {
    Log.v(TAG, "Returning fullInfoForStudent " + id + " retrieved in format " + format);
    String parseFormat = format;
    String response = "";
    try {
      response = getRetriever().fullInfoForStudent(format, id);
      cache.addResponse(response, "fullInfoForStudent" + id, format, "student");
      Session.getInstance().cachedData = false;
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "FAILED TO GET NEW DATA FROM SOURCE. USING OLD DATA FROM CACHE");
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

  /** Retrieves all courses from a Dataretriever and parse to a List of formatables.
   *
   * @return list of formatables (Course)
   * @throws DataRetrievalException
   */
  public List<Formatable> allCourses() throws DataRetrievalException {
    Log.v(TAG, "Returning allCourses retrieved in format " + format);
    String parseFormat = format;
    String response = "";
    try {
      response = getRetriever().allCourses(format);
      cache.addResponse(response, "allCourses", format, "course");
      Session.getInstance().cachedData = false;
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "FAILED TO GET NEW DATA FROM SOURCE. USING OLD DATA FROM CACHE");
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

  /** Retrieves all courses in a specific year from a Dataretriever
   *  and parse to a List of formatables.
   *
   * @param year four digit int (YYYY) ie 2016
   * @return list of formatables (Course)
   * @throws DataRetrievalException
   */
  public List<Formatable> allCoursesInYear(int year) throws DataRetrievalException {
    Log.v(TAG, "Returning allCoursesInYear " + year + " retrieved in format " + format);
    String parseFormat = format;
    String response = "";
    try {
      response = getRetriever().allCoursesInYear(format, year);
      cache.addResponse(response, "allCoursesInYear" + year, format, "course");
      Session.getInstance().cachedData = false;
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "FAILED TO GET NEW DATA FROM SOURCE. USING OLD DATA FROM CACHE");
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

  /** Retrieves full information on a specific course from a Dataretriever
   *  and parse to a List of formatables.
   *
   * @param id courseId
   * @return list of formatables (Course)
   * @throws DataRetrievalException
   */
  public List<Formatable> fullInfoForCourse(int id) throws DataRetrievalException {
    Log.v(TAG, "Returning fullInfoForCourse with id " + id + " retrieved in format " + format);
    String parseFormat = format;
    String response = "";
    try {
      response = getRetriever().fullInfoForCourse(format, id);
      cache.addResponse(response, "fullInfoForCourse" + id, format, "course");
      Session.getInstance().cachedData = false;
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "FAILED TO GET NEW DATA FROM SOURCE. USING OLD DATA FROM CACHE");
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

  //NOT IN USE YET... SHOULD BE USED BY METHODS ABOVE TO DRY CODE...when its done.
  /*private List<Formatable> retrieve(String type, int id) throws DataRetrievalException {
    Log.v(TAG, "Retrieving data...");
    String response = "";
    DataRetriever dR = DataRetrieverFactory.getRetriever();
    try {
      response = dR.fullInfoForCourse(format, id);
      cache.addResponse(response, "fullInfoForCourse" + id, format, "course");
      Session.getInstance().cachedData = false;
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "FAILED TO GET NEW DATA FROM SOURCE. USING OLD DATA FROM CACHE");
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
  }*/

  /**
   *  Clears the cache database.
   */
  public void clearCache() {
    cache.clearCache();
  }

  /**
   *
   * @return Returns the number of responses that is cached in the database
   */
  public int cacheSize() {
    return cache.getSize();
  }

  /** Closes cacheDB
   *
   */
  public void closeCache() {
    cache.close();
  }

  //NOTICE!! state of boolean is changed from isOpen to isClosed in order to match DRS if-statements
  //private static Boolean cacheDBIsClosed() {
  //  return !cache.status();
  //}

  /** Sets a requested retriever. If not set a default retriever will be used.
   *
   * @param requestedRetriever name of requested retriever
   */
  public void setDataRetriever(String requestedRetriever) {
    dataRetriever = requestedRetriever;
  }

  private DataRetriever getRetriever() throws DataRetrievalException {
    if (dataRetriever == null ) { //expected this is default setting.
      return DataRetrieverFactory.getRetriever();
    } else {
      return DataRetrieverFactory.getRetriever(dataRetriever);
    }
  }

}
