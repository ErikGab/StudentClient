package se.yrgo.erik.studentclient.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import se.yrgo.erik.studentclient.formatables.Course;
import se.yrgo.erik.studentclient.formatables.FormatableMockData;
import se.yrgo.erik.studentclient.formatables.Student;
import se.yrgo.erik.studentclient.storage.DataRetievalService;
import se.yrgo.erik.studentclient.storage.DataRetrievalException;

import java.util.List;
import java.util.stream.Collectors;

public class CoursesActivity extends AppCompatActivity {

  private List<String> yearList;
  private Spinner yearSpinner;
  private List<Course> coursesList;
  private ListView listView;
  private DataRetievalService drs;
  private static final String TAG = "CoursesActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_courses);
    Log.v(TAG, "onCreate");
    drs = DataRetievalService.getInstance();

    try {
      coursesList = drs.allCourses();
    } catch (DataRetrievalException dre) {
      Log.v(TAG, "onCreate: Failed to retrieve items!\n"+dre.getMessage());
      Intent intent = new Intent(CoursesActivity.this, MainActivity.class);
      startActivity(intent);
    }

    yearList = createListOfYears();
    yearSpinner = (Spinner) findViewById(R.id.course_select_spinner);
    yearSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.textview,
                                                    yearList)
    );
    yearSpinner.setSelection(Session.getInstance().yearSpinnerPossision);
    yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position,
                                 long id) {
        if (position != 0)  {
          listView.setAdapter(new ArrayAdapter<Course>(   CoursesActivity.this,
                  android.R.layout.simple_list_item_1,
                  coursesList.stream()
                          .filter( c -> c.getYear().equals(yearList.get(position)))
                          .collect(Collectors.toList()))
          );
          Session.getInstance().yearSpinnerPossision=position;
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parentView) {}
    });
    Log.v(TAG, "onCreate: Spinner Done");

    listView = (ListView) findViewById(R.id.course_listview);
    listView.setAdapter(new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_1,
                                                 coursesList)
    );
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int courseId = ((Course)listView.getAdapter().getItem(position)).getId();
        Log.v(TAG, "ListView clicked id: "+courseId);
        try {
          Session.getInstance().clickedCourse = drs.fullInfoForCourse(courseId).get(0);
          Session.getInstance().lastClick = drs.fullInfoForCourse(courseId).get(0);
          Intent intent = new Intent(CoursesActivity.this, InfoActivity.class);
          startActivity(intent);
        } catch (DataRetrievalException dre) {
          Log.v(TAG, "setOnItemClickListener: Failed to retrieve course!\n"+dre.getMessage());
          Intent intent = new Intent(CoursesActivity.this, MainActivity.class);
          startActivity(intent);
        }
      }
    });
    Log.v(TAG, "onCreate: Listview Done");
  }

  private List<String> createListOfYears() {
    Log.v(TAG, "createListOfYears");
    List<String> years = coursesList.stream()
            .map( f -> f.getYear())
            .distinct()
            .collect(Collectors.toList());
    years.add(0, "all");
    return years;
  }

}
