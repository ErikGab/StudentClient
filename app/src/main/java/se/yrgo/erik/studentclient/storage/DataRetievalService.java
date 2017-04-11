package se.yrgo.erik.studentclient.storage;

import android.util.Log;

import se.yrgo.erik.studentclient.main.Session;
import se.yrgo.erik.studentclient.formatables.Course;
import se.yrgo.erik.studentclient.formatables.Formatable;
import se.yrgo.erik.studentclient.formatables.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataRetievalService implements DataRetriever {

    private static DataRetievalService instance;
    private static final String SERVER_URL = "http://127.0.0.1:8080/StudentServiceAPI";
    private static String format = "json";
    private static String className[] = {"se.yrgo.erik.studentclient.storage.JSONMockDataRetriever"};
    private static Map<String,DataRetriever> retrievers;
    private static final String TAG = "DataRetrievalService";

    static{
        Log.v(TAG, "static block running");
        instance = new DataRetievalService();
        retrievers = new HashMap<>();
        try{
            Log.v(TAG, "loading class"+className[0]);
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
        Log.v(TAG, "registering: "+format);
        retrievers.put(format, retriever);
    }

    public static void setFormat(String requestedFormat){
        format = requestedFormat;
        Session.getInstance().format = requestedFormat;
    }


    @Override
    public List<Student> allStudents() throws DataRetrievalException {
        if (retrievers.containsKey(format)){
            Log.v(TAG, "Returning allStudents retrieved in format "+format);
            return retrievers.get(format).allStudents();
        } else {
            Log.v(TAG, "No retriever found for format "+format);
            throw new DataRetrievalException("No retriever found for format "+format);
        }
    }

    @Override
    public List<Student> allStudentsInCourse(int id) throws DataRetrievalException {
        if (retrievers.containsKey(format)){
            Log.v(TAG, "Returning allStudentsInCourse "+id+" retrieved in format "+format);
            return retrievers.get(format).allStudentsInCourse(id);
        } else {
            Log.v(TAG, "No retriever found for format "+format);
            throw new DataRetrievalException("No retriever found for format "+format);
        }
    }

    @Override
    public List<Student> fullInfoForStudent(int studentId) {
        return null;
    }

    @Override
    public List<Course> allCourses() throws DataRetrievalException {
        if (retrievers.containsKey(format)){
            Log.v(TAG, "Returning allCourses retrieved in format "+format);
            return retrievers.get(format).allCourses();
        } else {
            Log.v(TAG, "No retriever found for format "+format);
            throw new DataRetrievalException("No retriever found for format "+format);
        }
    }

    @Override
    public List<Course> allCoursesInYear(int year) throws DataRetrievalException {

        if (retrievers.containsKey(format)){
            Log.v(TAG, "Returning allCoursesInYear "+year+" retrieved in format "+format);
            return retrievers.get(format).allCoursesInYear(year);
        } else {
            Log.v(TAG, "No retriever found for format "+format);
            throw new DataRetrievalException("No retriever found for format "+format);
        }
    }

    @Override
    public List<Course> fullInfoForCourse(int courseId) {
        return null;
    }

    private List<Formatable> getDatafromServer(){
        return null;
    }

}
