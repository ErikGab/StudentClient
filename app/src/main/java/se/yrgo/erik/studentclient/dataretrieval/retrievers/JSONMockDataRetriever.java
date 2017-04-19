package se.yrgo.erik.studentclient.dataretrieval.retrievers;


import android.util.Log;

import se.yrgo.erik.studentclient.dataretrieval.DataRetrieverFactory;
import se.yrgo.erik.studentclient.dataretrieval.DataRetriever;

import java.util.HashMap;

public class JSONMockDataRetriever implements DataRetriever {

  private static final String TAG = "JSONMockDataRetriever";

  static{
    Log.v(TAG, "static block running");
    DataRetrieverFactory.register("jsonMock", new JSONMockDataRetriever());
  }

  private JSONMockDataRetriever() {}

  @Override
  public String allStudents(String format) {
    Log.v(TAG, "returning allStudents...");
    String json = "{\"student\":[{\"id\":\"1\",\"name\":\"Katie\",\"surname\":\"Anderson\"}," +
            "{\"id\":\"4\",\"name\":\"Ludovico\",\"surname\":\"Anderson\"}," +
            "{\"id\":\"7\",\"name\":\"Aretha\",\"surname\":\"James\"}," +
            "{\"id\":\"9\",\"name\":\"Warren\",\"surname\":\"Flack\"}," +
            "{\"id\":\"10\",\"name\":\"Ludovico\",\"surname\":\"Einaudi\"}," +
            "{\"id\":\"15\",\"name\":\"Ludovico\",\"surname\":\"Davis\"}," +
            "{\"id\":\"20\",\"name\":\"Alice\",\"surname\":\"Cooke\"}," +
            "{\"id\":\"31\",\"name\":\"Eva\",\"surname\":\"Cooper\"}," +
            "{\"id\":\"51\",\"name\":\"Albert\",\"surname\":\"Knopfler\"}," +
            "{\"id\":\"52\",\"name\":\"Etta\",\"surname\":\"Dirkschneider\"}," +
            "{\"id\":\"60\",\"name\":\"George\",\"surname\":\"Knopfler\"}," +
            "{\"id\":\"62\",\"name\":\"Jim\",\"surname\":\"Wylde\"}," +
            "{\"id\":\"64\",\"name\":\"Ludovico\",\"surname\":\"Croce\"}," +
            "{\"id\":\"77\",\"name\":\"Nina\",\"surname\":\"Cash\"}," +
            "{\"id\":\"82\",\"name\":\"Roky\",\"surname\":\"Knopfler\"}," +
            "{\"id\":\"83\",\"name\":\"Miles\",\"surname\":\"Cassidy\"}," +
            "{\"id\":\"87\",\"name\":\"Katie\",\"surname\":\"Redding\"}," +
            "{\"id\":\"94\",\"name\":\"Roky\",\"surname\":\"Cooper\"}," +
            "{\"id\":\"98\",\"name\":\"Tom\",\"surname\":\"MacFarlane\"}]}";
    return json;
  }

  @Override
  public String allStudentsInCourse(String format, int id) {
    Log.v(TAG, "returning allStudentsInCourse... (id ignored)");
    String json = "{\"student\":[{\"id\":\"4\",\"name\":\"Ludovico\",\"surname\":\"Anderson\"}," +
            "{\"id\":\"9\",\"name\":\"Warren\",\"surname\":\"Flack\"}," +
            "{\"id\":\"15\",\"name\":\"Ludovico\",\"surname\":\"Davis\"}," +
            "{\"id\":\"31\",\"name\":\"Eva\",\"surname\":\"Cooper\"}," +
            "{\"id\":\"52\",\"name\":\"Etta\",\"surname\":\"Dirkschneider\"}," +
            "{\"id\":\"62\",\"name\":\"Jim\",\"surname\":\"Wylde\"}," +
            "{\"id\":\"77\",\"name\":\"Nina\",\"surname\":\"Cash\"}," +
            "{\"id\":\"83\",\"name\":\"Miles\",\"surname\":\"Cassidy\"}," +
            "{\"id\":\"94\",\"name\":\"Roky\",\"surname\":\"Cooper\"}]}";
    return json;
  }

  @Override
  public String fullInfoForStudent(String format, int studentId) {
    String json = "{\"student\":[{\"id\":\"8\",\"name\":\"Katie\",\"surname\":\"Dio\"," +
            "\"age\":\"44\",\"postAddress\":\"Bärslanda\",\"streetAddress\":\"Mesksmasket\"," +
            "\"phonenumbers\":[{\"mobile\":\"0759-971563\",\"home\":\"080-359162\"}]," +
            "\"course\":[{\"id\":\"9\",\"name\":\"DB-101_2016\",\"status\":\"aborted\"," +
            "\"grade\":\"\"}]}]}";
    return json;
  }


  @Override
  public String allCourses(String format) {
    Log.v(TAG, "returning allCourses...");
    String json = "{\"course\":[{\"id\":\"1\",\"name\":\"C-101_2014\"}," +
            "{\"id\":\"2\",\"name\":\"JAVA-101_2015\"},{\"id\":\"3\",\"name\":\"DB-101_2015\"}," +
            "{\"id\":\"4\",\"name\":\"Bash-101_2015\"},{\"id\":\"5\",\"name\":\"C-101_2015\"}," +
            "{\"id\":\"6\",\"name\":\"C-102_2015\"},{\"id\":\"7\",\"name\":\"JAVA-101_2016\"}," +
            "{\"id\":\"8\",\"name\":\"JAVA-102_2016\"},{\"id\":\"9\",\"name\":\"DB-101_2016\"}," +
            "{\"id\":\"10\",\"name\":\"Bash-101_2016\"},{\"id\":\"11\",\"name\":\"C-101_2016\"}," +
            "{\"id\":\"12\",\"name\":\"C-102_2016\"},{\"id\":\"13\",\"name\":\"C-103_2016\"}," +
            "{\"id\":\"14\",\"name\":\"JAVA-101_2017\"},{\"id\":\"15\",\"name\":\"JAVA-102_2017\"}," +
            "{\"id\":\"16\",\"name\":\"DB-101_2017\"},{\"id\":\"17\",\"name\":\"Bash-101_2017\"}," +
            "{\"id\":\"18\",\"name\":\"C-101_2017\"},{\"id\":\"19\",\"name\":\"C-102_2017\"}," +
            "{\"id\":\"20\",\"name\":\"C-103_2017\"}]}";
    return json;
  }

  @Override
  public String allCoursesInYear(String format, int year) {
    Log.v(TAG, "returning allCoursesInYear in year "+year+"...");
    HashMap<Integer, String> jsons = new HashMap<>();
    jsons.put(2014, "{\"course\":[{\"id\":\"1\",\"name\":\"C-101_2014\"}]}");
    jsons.put(2015, "{\"course\":[{\"id\":\"2\",\"name\":\"JAVA-101_2015\"}," +
            "{\"id\":\"3\",\"name\":\"DB-101_2015\"},{\"id\":\"4\",\"name\":\"Bash-101_2015\"}," +
            "{\"id\":\"5\",\"name\":\"C-101_2015\"},{\"id\":\"6\",\"name\":\"C-102_2015\"}]}"
    );
    jsons.put(2016, "{\"course\":[{\"id\":\"7\",\"name\":\"JAVA-101_2016\"}," +
            "{\"id\":\"8\",\"name\":\"JAVA-102_2016\"},{\"id\":\"9\",\"name\":\"DB-101_2016\"}," +
            "{\"id\":\"10\",\"name\":\"Bash-101_2016\"},{\"id\":\"11\",\"name\":\"C-101_2016\"}," +
            "{\"id\":\"12\",\"name\":\"C-102_2016\"},{\"id\":\"13\",\"name\":\"C-103_2016\"}]}"
    );
    jsons.put(2017, "{\"course\":[{\"id\":\"14\",\"name\":\"JAVA-101_2017\"}," +
            "{\"id\":\"15\",\"name\":\"JAVA-102_2017\"},{\"id\":\"16\",\"name\":\"DB-101_2017\"}," +
            "{\"id\":\"17\",\"name\":\"Bash-101_2017\"},{\"id\":\"18\",\"name\":\"C-101_2017\"}," +
            "{\"id\":\"19\",\"name\":\"C-102_2017\"},{\"id\":\"20\",\"name\":\"C-103_2017\"}]}"
    );
    if (jsons.containsKey(year)) {
      return jsons.get(year);
    } else {
      return "";
    }
  }

  @Override
  public String fullInfoForCourse(String format, int courseId) {
    Log.v(TAG, "returning detailed Course info...");
    String json = "{\"course\":[{\"id\":\"8\",\"name\":\"JAVA-102_2016\"," +
            "\"description\":\"Programming with Java\",\"startDate\":\"2016-02-18\"," +
            "\"endDate\":\"2016-10-18\",\"points\":\"40\",\"student\":[{\"id\":\"25\"," +
            "\"name\":\"Harry\",\"surname\":\"Simone\",\"status\":\"complete\"," +
            "\"grade\":\"ig\"}]}]}";
    return json;
  }
}
