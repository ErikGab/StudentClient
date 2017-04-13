package se.yrgo.erik.studentclient.main;

import se.yrgo.erik.studentclient.formatables.Course;
import se.yrgo.erik.studentclient.formatables.Formatable;
import se.yrgo.erik.studentclient.formatables.Student;

public class Session {

    public int courseSpinnerPossision = 0;
    public int yearSpinnerPossision = 0;
    //public ListType selectActivity = ListType.STUDENT;
    public Formatable clickedCourse = null;
    public Formatable clickedStudent = null;
    public Formatable lastClick = null;

    public String format = "json";
    private static Session session;

    static {
        session = new Session();
    }

    private Session() {}

    public static Session getInstance() {
        return session;
    }

    //public static enum ListType {
    //    STUDENT,
    //    COURSE;
    //}
}
