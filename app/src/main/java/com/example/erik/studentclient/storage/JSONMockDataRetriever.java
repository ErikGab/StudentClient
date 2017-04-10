package com.example.erik.studentclient.storage;


import com.example.erik.studentclient.formatables.Course;
import com.example.erik.studentclient.formatables.Student;
import com.example.erik.studentclient.parseresponse.DataParserUtil;

import java.util.List;

public class JSONMockDataRetriever implements DataRetriever {

    static{
        DataRetievalService.register("json", new JSONMockDataRetriever());
    }

    private JSONMockDataRetriever(){}

    @Override
    public List<Student> allStudents() {
        String getStudentByCourseAll = "{\"student\":[{\"id\":\"1\",\"name\":\"Katie\",\"surname\":\"Anderson\"},{\"id\":\"4\",\"name\":\"Ludovico\",\"surname\":\"Anderson\"},{\"id\":\"7\",\"name\":\"Aretha\",\"surname\":\"James\"},{\"id\":\"9\",\"name\":\"Warren\",\"surname\":\"Flack\"},{\"id\":\"10\",\"name\":\"Ludovico\",\"surname\":\"Einaudi\"},{\"id\":\"15\",\"name\":\"Ludovico\",\"surname\":\"Davis\"},{\"id\":\"20\",\"name\":\"Alice\",\"surname\":\"Cooke\"},{\"id\":\"31\",\"name\":\"Eva\",\"surname\":\"Cooper\"},{\"id\":\"51\",\"name\":\"Albert\",\"surname\":\"Knopfler\"},{\"id\":\"52\",\"name\":\"Etta\",\"surname\":\"Dirkschneider\"},{\"id\":\"60\",\"name\":\"George\",\"surname\":\"Knopfler\"},{\"id\":\"62\",\"name\":\"Jim\",\"surname\":\"Wylde\"},{\"id\":\"64\",\"name\":\"Ludovico\",\"surname\":\"Croce\"},{\"id\":\"77\",\"name\":\"Nina\",\"surname\":\"Cash\"},{\"id\":\"82\",\"name\":\"Roky\",\"surname\":\"Knopfler\"},{\"id\":\"83\",\"name\":\"Miles\",\"surname\":\"Cassidy\"},{\"id\":\"87\",\"name\":\"Katie\",\"surname\":\"Redding\"},{\"id\":\"94\",\"name\":\"Roky\",\"surname\":\"Cooper\"},{\"id\":\"98\",\"name\":\"Tom\",\"surname\":\"MacFarlane\"}]}";
        return DataParserUtil.Json2Students(getStudentByCourseAll);
    }

    @Override
    public List<Student> allStudentsInCourse(int id) {
        return allStudents();
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
}
