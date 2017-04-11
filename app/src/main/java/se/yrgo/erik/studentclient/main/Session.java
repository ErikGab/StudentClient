package se.yrgo.erik.studentclient.main;


import se.yrgo.erik.studentclient.formatables.Course;
import se.yrgo.erik.studentclient.formatables.Formatable;
import se.yrgo.erik.studentclient.formatables.Student;

public class Session {

    public int courseSpinnerPossision = 0;
    public int yearSpinnerPossision = 0;
    public Course clickedCourse = null;
    public Student clickedStudent = null;
    public Formatable lastClick = null;
    public String format = "json";

    private static Session session;
    static {
        session = new Session();
    }
    private Session(){}

    public static Session getInstance(){
        return session;
    }

}
