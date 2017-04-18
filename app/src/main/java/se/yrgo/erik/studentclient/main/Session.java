package se.yrgo.erik.studentclient.main;

import se.yrgo.erik.studentclient.formatables.Formatable;

public class Session {

  public int courseSpinnerPossision = 0;
  public int yearSpinnerPossision = 0;
  public Formatable clickedCourse = null;
  public Formatable clickedStudent = null;
  public Formatable lastClick = null;
  public boolean cachedData = false;

  public String format = "json";
  private static Session session;

  static {
    session = new Session();
  }

  private Session() {}

  public static Session getInstance() {
    return session;
  }

}
