package com.example.erik.studentclient;



public class Session {

    public int courseSpinnerPossision = 0;
    public int yearSpinnerPossision = 0;
    public Course clickedCourse = null;
    public Student clickedStudent = null;
    public Formatable lastClick = null;

    private static Session session;
    static {
        session = new Session();
    }
    private Session(){}

    public static Session getInstance(){
        return session;
    }

}
