package com.example.erik.studentclient.storage;

import com.example.erik.studentclient.formatables.Course;
import com.example.erik.studentclient.formatables.Formatable;
import com.example.erik.studentclient.formatables.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataRetievalService implements DataRetriever {

    private static DataRetievalService instance;
    private static final String SERVER_URL = "http://127.0.0.1:8080/StudentServiceAPI";
    private static String format = "json";
    private static String className[] = {"JSONMockDataRetriever"};
    private static Map<String,DataRetriever> retrievers;

    static{
        instance = new DataRetievalService();
        retrievers = new HashMap<>();
        try{
            Class.forName(className[0]);
        }catch(ClassNotFoundException cnfe){
            System.err.println(cnfe.getMessage());
        }
    }

    private DataRetievalService(){};

    public static DataRetievalService getInstance(){
        return instance;
    }

    public static void register(String format, DataRetriever retriever){
        retrievers.put(format, retriever);
    }

    @Override
    public List<Student> allStudents() throws DataRetrievalException {
        if (retrievers.containsKey(format)){
            return retrievers.get(format).allStudents();
        } else {
            System.err.println("No retriever found for format "+format);
            throw new DataRetrievalException("No retriever found for format "+format);
        }

    }

    @Override
    public List<Student> allStudentsInCourse(int id) {
        return null;
    }

    @Override
    public List<Student> fullInfoForStudent(int studentId) {
        return null;
    }

    @Override
    public List<Course> allCourses() {
        return null;
    }

    @Override
    public List<Course> allCoursesInYear(int year) {
        return null;
    }

    @Override
    public List<Course> fullInfoForCourse(int courseId) {
        return null;
    }

    private List<Formatable> getDatafromServer(){
        return null;
    }

}
