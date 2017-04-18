package se.yrgo.erik.studentclient.dataretrieval.retrievers;

import java.util.List;

import se.yrgo.erik.studentclient.formatables.Formatable;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalException;
import se.yrgo.erik.studentclient.dataretrieval.DataRetriever;

public class CacheDataRetriever implements DataRetriever {
  @Override
  public List<Formatable> allStudents() throws DataRetrievalException {
    return null;
  }

  @Override
  public List<Formatable> allStudentsInCourse(int id) throws DataRetrievalException {
    return null;
  }

  @Override
  public List<Formatable> fullInfoForStudent(int studentId) throws DataRetrievalException {
    return null;
  }

  @Override
  public List<Formatable> allCourses() throws DataRetrievalException {
    return null;
  }

  @Override
  public List<Formatable> allCoursesInYear(int year) throws DataRetrievalException {
    return null;
  }

  @Override
  public List<Formatable> fullInfoForCourse(int courseId) throws DataRetrievalException {
    return null;
  }
}
