package com.example.erik.studentclient.storage;


import com.example.erik.studentclient.formatables.Course;
import com.example.erik.studentclient.formatables.Student;

import java.util.List;

public interface DataRetriever {

    public List<Student> allStudents() throws DataRetrievalException;
    public List<Student> allStudentsInCourse(int id) throws DataRetrievalException;
    public List<Student> fullInfoForStudent(int studentId) throws DataRetrievalException;
    public List<Course> allCourses() throws DataRetrievalException;
    public List<Course> allCoursesInYear(int year) throws DataRetrievalException;
    public List<Course> fullInfoForCourse(int courseId) throws DataRetrievalException;
}
