package se.yrgo.erik.studentclient.storage;

import java.util.List;

import se.yrgo.erik.studentclient.formatables.Formatable;

public class XMLDataRetriever implements DataRetriever {
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
