package se.yrgo.erik.studentclient.storage;


import android.util.Log;

import se.yrgo.erik.studentclient.formatables.Course;
import se.yrgo.erik.studentclient.formatables.Student;
import se.yrgo.erik.studentclient.parseresponse.DataParserException;
import se.yrgo.erik.studentclient.parseresponse.DataParserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSONMockDataRetriever implements DataRetriever {

    private static final String TAG = "JSONMockDataRetriever";

    static{
        Log.v(TAG, "static block running");
        DataRetievalService.register("json", new JSONMockDataRetriever());
    }

    private JSONMockDataRetriever(){}

    @Override
    public List<Student> allStudents() {
        Log.v(TAG, "returning allStudents");
        String json = "{\"student\":[{\"id\":\"1\",\"name\":\"Katie\",\"surname\":\"Anderson\"},{\"id\":\"4\",\"name\":\"Ludovico\",\"surname\":\"Anderson\"},{\"id\":\"7\",\"name\":\"Aretha\",\"surname\":\"James\"},{\"id\":\"9\",\"name\":\"Warren\",\"surname\":\"Flack\"},{\"id\":\"10\",\"name\":\"Ludovico\",\"surname\":\"Einaudi\"},{\"id\":\"15\",\"name\":\"Ludovico\",\"surname\":\"Davis\"},{\"id\":\"20\",\"name\":\"Alice\",\"surname\":\"Cooke\"},{\"id\":\"31\",\"name\":\"Eva\",\"surname\":\"Cooper\"},{\"id\":\"51\",\"name\":\"Albert\",\"surname\":\"Knopfler\"},{\"id\":\"52\",\"name\":\"Etta\",\"surname\":\"Dirkschneider\"},{\"id\":\"60\",\"name\":\"George\",\"surname\":\"Knopfler\"},{\"id\":\"62\",\"name\":\"Jim\",\"surname\":\"Wylde\"},{\"id\":\"64\",\"name\":\"Ludovico\",\"surname\":\"Croce\"},{\"id\":\"77\",\"name\":\"Nina\",\"surname\":\"Cash\"},{\"id\":\"82\",\"name\":\"Roky\",\"surname\":\"Knopfler\"},{\"id\":\"83\",\"name\":\"Miles\",\"surname\":\"Cassidy\"},{\"id\":\"87\",\"name\":\"Katie\",\"surname\":\"Redding\"},{\"id\":\"94\",\"name\":\"Roky\",\"surname\":\"Cooper\"},{\"id\":\"98\",\"name\":\"Tom\",\"surname\":\"MacFarlane\"}]}";
        return DataParserUtil.Json2Students(json);
    }

    @Override
    public List<Student> allStudentsInCourse(int id) {
        Log.v(TAG, "returning allStudentsInCourse (id ignored)");
        String json = "{\"student\":[{\"id\":\"4\",\"name\":\"Ludovico\",\"surname\":\"Anderson\"},{\"id\":\"9\",\"name\":\"Warren\",\"surname\":\"Flack\"},{\"id\":\"15\",\"name\":\"Ludovico\",\"surname\":\"Davis\"},{\"id\":\"31\",\"name\":\"Eva\",\"surname\":\"Cooper\"},{\"id\":\"52\",\"name\":\"Etta\",\"surname\":\"Dirkschneider\"},{\"id\":\"62\",\"name\":\"Jim\",\"surname\":\"Wylde\"},{\"id\":\"77\",\"name\":\"Nina\",\"surname\":\"Cash\"},{\"id\":\"83\",\"name\":\"Miles\",\"surname\":\"Cassidy\"},{\"id\":\"94\",\"name\":\"Roky\",\"surname\":\"Cooper\"}]}";
        return DataParserUtil.Json2Students(json);
    }

    @Override
    public List<Student> fullInfoForStudent(int studentId) {
        return null;
    }

    @Override
    public List<Course> allCourses() {
        Log.v(TAG, "returning allCourses...");
        try {
            String json = "{\"course\":[{\"id\":\"1\",\"name\":\"C-101_2014\"},{\"id\":\"2\",\"name\":\"JAVA-101_2015\"},{\"id\":\"3\",\"name\":\"DB-101_2015\"},{\"id\":\"4\",\"name\":\"Bash-101_2015\"},{\"id\":\"5\",\"name\":\"C-101_2015\"},{\"id\":\"6\",\"name\":\"C-102_2015\"},{\"id\":\"7\",\"name\":\"JAVA-101_2016\"},{\"id\":\"8\",\"name\":\"JAVA-102_2016\"},{\"id\":\"9\",\"name\":\"DB-101_2016\"},{\"id\":\"10\",\"name\":\"Bash-101_2016\"},{\"id\":\"11\",\"name\":\"C-101_2016\"},{\"id\":\"12\",\"name\":\"C-102_2016\"},{\"id\":\"13\",\"name\":\"C-103_2016\"},{\"id\":\"14\",\"name\":\"JAVA-101_2017\"},{\"id\":\"15\",\"name\":\"JAVA-102_2017\"},{\"id\":\"16\",\"name\":\"DB-101_2017\"},{\"id\":\"17\",\"name\":\"Bash-101_2017\"},{\"id\":\"18\",\"name\":\"C-101_2017\"},{\"id\":\"19\",\"name\":\"C-102_2017\"},{\"id\":\"20\",\"name\":\"C-103_2017\"}]}";
            return DataParserUtil.Json2Course(json);
        } catch (DataParserException dpe) {
            //TO DO: Hantera detta bättre!
            Log.v(TAG, "returning allCourses...Failed: "+dpe.getMessage());
            return new ArrayList<Course>();
        }
    }

    @Override
    public List<Course> allCoursesInYear(int year) {
        Log.v(TAG, "returning allCoursesInYear in year "+year+"...");
        try {
            HashMap<Integer, String> jsons = new HashMap<>();
            jsons.put(2014, "{\"course\":[{\"id\":\"1\",\"name\":\"C-101_2014\"}]}");
            jsons.put(2015, "{\"course\":[{\"id\":\"2\",\"name\":\"JAVA-101_2015\"},{\"id\":\"3\",\"name\":\"DB-101_2015\"},{\"id\":\"4\",\"name\":\"Bash-101_2015\"},{\"id\":\"5\",\"name\":\"C-101_2015\"},{\"id\":\"6\",\"name\":\"C-102_2015\"}]}");
            jsons.put(2016, "{\"course\":[{\"id\":\"7\",\"name\":\"JAVA-101_2016\"},{\"id\":\"8\",\"name\":\"JAVA-102_2016\"},{\"id\":\"9\",\"name\":\"DB-101_2016\"},{\"id\":\"10\",\"name\":\"Bash-101_2016\"},{\"id\":\"11\",\"name\":\"C-101_2016\"},{\"id\":\"12\",\"name\":\"C-102_2016\"},{\"id\":\"13\",\"name\":\"C-103_2016\"}]}");
            jsons.put(2017, "{\"course\":[{\"id\":\"14\",\"name\":\"JAVA-101_2017\"},{\"id\":\"15\",\"name\":\"JAVA-102_2017\"},{\"id\":\"16\",\"name\":\"DB-101_2017\"},{\"id\":\"17\",\"name\":\"Bash-101_2017\"},{\"id\":\"18\",\"name\":\"C-101_2017\"},{\"id\":\"19\",\"name\":\"C-102_2017\"},{\"id\":\"20\",\"name\":\"C-103_2017\"}]}");
            return DataParserUtil.Json2Course(jsons.get(year));
        } catch (DataParserException dpe) {
            //TO DO: Hantera detta bättre!
            Log.v(TAG, "returning allCoursesInYear...Failed: "+dpe.getMessage());
            return new ArrayList<Course>();
        }
    }

    @Override
    public List<Course> fullInfoForCourse(int courseId) {
        return null;
    }
}
