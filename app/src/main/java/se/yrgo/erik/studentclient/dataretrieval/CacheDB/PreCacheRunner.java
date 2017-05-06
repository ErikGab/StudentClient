package se.yrgo.erik.studentclient.dataretrieval.CacheDB;


import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.stream.Collectors;

import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalException;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalService;
import se.yrgo.erik.studentclient.formatables.Course;
import se.yrgo.erik.studentclient.formatables.ItemType;

/**
 * An AsyncTask that fills the cache database with all possible responses.
 * It uses the DataRetrieverService that will automatically cache all responses getting throug.
 */
public class PreCacheRunner extends AsyncTask<Void, Void, Void> {

  private final String TAG = "PreCacheRunner";
  DataRetrievalService drs;
  private final int FAST = 25;
  private final int SLOW = 500;
  private int speed;
  private int counter;
  private int target;
  private int progress;

  @Override
  protected Void doInBackground(Void... params) {
    drs = DataRetrievalService.getInstance();
    speed = FAST;
    Log.v(TAG, "STARTING");
    initProgress();
    getStudents();
    Log.v(TAG, "STUDENTS DONE: cached " + counter + " students.");
    getCourses();
    Log.v(TAG, "COURSES DONE: cached " + counter + " courses.");
    this.cancel(true); // Hmm is this necessary?
    return null;
  }

  private void getStudents() {
    try {
      getStudentsForCourse();
      getDetailedStudent(drs.allStudents().stream()
              .map(f -> f.getId())
              .collect(Collectors.toList()));
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "Failed getStudents");
    }
  }

  private void getStudentsForCourse() {
    try {
      for (int id : getCourseIds()) {
        drs.allStudentsInCourse(id);
        phase(speed);
      }
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "Failed getStudentsForCourse");
    }
  }

  private void getCourses() {
    try {
      getCoursesForYear();
      getDetailedCourse(drs.allCourses().stream()
              .map(f -> f.getId())
              .collect(Collectors.toList()));
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "Failed getCourses");
    }
  }

  private void getCoursesForYear() {
    try {
      for (int year : getYears()) {
        drs.allCoursesInYear(year);
        phase(speed);
      }
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "Failed getCoursesForYear");
    }
  }

  private List<Integer> getCourseIds() throws DataRetrievalException {
    return drs.allCourses().stream()
            .filter(f -> f.getItemType() == ItemType.COURSE)
            .map(f -> f.getId())
            .collect(Collectors.toList());
  }

  private List<Integer> getYears() throws DataRetrievalException {
    return drs.allCourses().stream()
            .filter(f -> f.getItemType() == ItemType.COURSE)
            .map(f -> Integer.parseInt(((Course) f).getYear()))
            .collect(Collectors.toList());
  }

  private void getDetailedStudent(List<Integer> ids) throws DataRetrievalException {
    for (int id : ids) {
      drs.fullInfoForStudent(id);
      counter++;
      updateProgress();
      phase(speed);
    }
  }

  private void getDetailedCourse(List<Integer> ids) throws DataRetrievalException {
    for (int id : ids) {
      drs.fullInfoForCourse(id);
      counter++;
      updateProgress();
      phase(speed);
    }
  }

  private void phase(int milliseconds) {
    try {
      Thread.sleep(speed);
      //this.wait(speed);
    } catch (InterruptedException ie) {
      //ignore
    }
  }

  private void initProgress() {
    try {
      counter = 0;
      target = drs.allStudents().size() + drs.allCourses().size();
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "Failed initProgress");
    }
  }

  private void updateProgress(){
    if (target > 0) {
      progress = (int) (100 * ( (double) counter / target));
    }
  }

  /**
   * @return Returns an int (0 .. 100) describing the percentage of completion of the PreCacheTask.
   */
  public int getProgress(){
    return progress;
  }

}