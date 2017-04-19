package se.yrgo.erik.studentclient.dataretrieval;

public interface DataRetriever {

  String allStudents(String format) throws DataRetrievalException;
  String allStudentsInCourse(String format, int id) throws DataRetrievalException;
  String fullInfoForStudent(String format, int studentId) throws DataRetrievalException;
  String allCourses(String format) throws DataRetrievalException;
  String allCoursesInYear(String format, int year) throws DataRetrievalException;
  String fullInfoForCourse(String format, int courseId) throws DataRetrievalException;
}

