package se.yrgo.erik.studentclient.dataretrieval.retrievers;

import android.util.Log;

import se.yrgo.erik.studentclient.dataretrieval.DataRetrieverFactory;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalException;
import se.yrgo.erik.studentclient.dataretrieval.DataRetriever;

public class CacheDataRetriever implements DataRetriever {

  private static final String TAG = "CacheDataRetriever";

  static{
    Log.v(TAG, "static block running");
    DataRetrieverFactory.register("cache", new CacheDataRetriever());
  }

  private CacheDataRetriever() {}

  @Override
  public String allStudents(String format) throws DataRetrievalException {
    return null;
  }

  @Override
  public String allStudentsInCourse(String format, int id) throws DataRetrievalException {
    return null;
  }

  @Override
  public String fullInfoForStudent(String format, int studentId) throws DataRetrievalException {
    return null;
  }

  @Override
  public String allCourses(String format) throws DataRetrievalException {
    return null;
  }

  @Override
  public String allCoursesInYear(String format, int year) throws DataRetrievalException {
    return null;
  }

  @Override
  public String fullInfoForCourse(String format, int courseId) throws DataRetrievalException {
    return null;
  }
}
