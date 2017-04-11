package com.example.erik.studentclient.formatables;

import android.util.Log;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

public class FormatableMockData {

    private FormatableMockData(){};

    private static List<Student> mockDataFullStudent, mockDataStudent;
    private static List<Course> mockDataFullCourse, mockDataCourse;
    private static final String TAG = "FormatableMockData";
    static {
        Log.v(TAG, "static block running");
        mockDataFullStudent = new ArrayList<Student>();
        mockDataFullStudent.add(new Student(74,
                new LinkedHashMap<String,String>() {{
                    put("id", "74");
                    put("name", "Mocke");
                    put("surname", "Datasson");
                    put("age", "34");
                    put("postAddress", "Heapen");
                    put("streetAddress", "Ramminnet 123");
                }},
                new ArrayList<Formatable>() {{
                    add(new FormatableItem("phonenumbers",
                            1,
                            new LinkedHashMap<String,String>() {{
                                put("mobile", "0707-776655");
                                put("fax", "08-1234567");
                            }}));
                    add(new Course(8,
                            new LinkedHashMap<String,String>(){{
                                put("id", "8");
                                put("name", "JAVA-101_2016");
                                put("status", "complete");
                                put("grade", "vg");
                            }}));
                    add(new Course(12,
                            new LinkedHashMap<String,String>(){{
                                put("id", "12");
                                put("name", "JAVA-102_2017");
                                put("status", "ongoing");
                                put("grade", "");
                            }}));
                }}));

        mockDataFullCourse = new ArrayList<Course>();
        mockDataFullCourse.add(new Course(8,
                new LinkedHashMap<String,String>() {{
                    put("id", "8");
                    put("startDate", "2016-02-21");
                    put("endDate", "2016-11-04");
                    put("name", "JAVA-101_2016");
                    put("points", "40");
                    put("description", "Programming with Java");
                }},
                new ArrayList<Formatable>() {{
                    add(new Student(74,
                            new LinkedHashMap<String,String>(){{
                                put("id", "74");
                                put("name", "Mocke");
                                put("surname", "Datasson");
                                put("status", "complete");
                                put("grade", "vg");
                            }}));
                    add(new Student(35,
                            new LinkedHashMap<String,String>(){{
                                put("id", "35");
                                put("name", "Datbert");
                                put("surname", "Mockberg");
                                put("status", "complete");
                                put("grade", "g");
                            }}));
                }}));

        mockDataStudent = new ArrayList<Student>();
        mockDataStudent.add(new Student(74, new LinkedHashMap<String,String>() {{
            put("id", "74");
            put("name", "Mocke");
            put("surname", "Datasson");
        }}));
        mockDataStudent.add(new Student(35, new LinkedHashMap<String,String>() {{
            put("id", "35");
            put("name", "Datbert");
            put("surname", "Mockberg");
        }}));

        mockDataCourse = new ArrayList<Course>();
        mockDataCourse.add(new Course(8, new LinkedHashMap<String,String>() {{
            put("id", "8");
            put("name", "JAVA-101_2016");
        }}));
        mockDataCourse.add(new Course(12, new LinkedHashMap<String,String>() {{
            put("id", "12");
            put("name", "JAVA-102_2017");
        }}));
    }
    public static List<Student> getStudent(int id) {
        Log.v(TAG, "returning detailed list of students");
        return mockDataFullStudent;
    }
    public static List<Course> getCourse(int id) {
        Log.v(TAG, "returning detailed list of courses");
        return mockDataFullCourse;
    }
    public static List<Student> getStudentsByCourse(int id) {
        Log.v(TAG, "returning list of students");
        return mockDataStudent;
    }
    public static List<Course> getCoursesByYear(int id) {
        Log.v(TAG, "returning list of courses");
        return mockDataCourse;
    }
}
