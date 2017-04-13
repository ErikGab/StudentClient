package se.yrgo.erik.studentclient.storage;


import se.yrgo.erik.studentclient.formatables.Course;
import se.yrgo.erik.studentclient.formatables.Formatable;
import se.yrgo.erik.studentclient.formatables.Student;

import java.util.List;

public interface DataRetriever {

  public List<Formatable> allStudents() throws DataRetrievalException;
  public List<Formatable> allStudentsInCourse(int id) throws DataRetrievalException;
  public List<Formatable> fullInfoForStudent(int studentId) throws DataRetrievalException;
  public List<Formatable> allCourses() throws DataRetrievalException;
  public List<Formatable> allCoursesInYear(int year) throws DataRetrievalException;
  public List<Formatable> fullInfoForCourse(int courseId) throws DataRetrievalException;
}

