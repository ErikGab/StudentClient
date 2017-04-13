package se.yrgo.erik.studentclient.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;
import java.util.stream.Collectors;

import se.yrgo.erik.studentclient.formatables.Course;
import se.yrgo.erik.studentclient.formatables.Formatable;
import se.yrgo.erik.studentclient.storage.DataRetrievalClient;
import se.yrgo.erik.studentclient.storage.DataRetrievalService2;

public class CoursesActivity2 extends AppCompatActivity implements DataRetrievalClient {

  private List<String> yearList;
  private Spinner yearSpinner;
  private List<Formatable> coursesList;
  private ListView listView;
  private DataRetrievalService2 drs;
  private static final String TAG = "CoursesActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_courses);
    Log.v(TAG, "onCreate");
    drs = DataRetrievalService2.getInstance();
    yearSpinner = (Spinner) findViewById(R.id.course_select_spinner);
    listView = (ListView) findViewById(R.id.course_listview);
    drs.requestData(DataRetrievalService2.GETTYPE.ALL_COURSES, 0, this);
  }

  @Override
  public void recieveFromService(List<Formatable> list, DataRetrievalService2.GETTYPE requestedData) {
    switch (requestedData) {
      case ALL_COURSES:
        Log.v(TAG, "Recieved data for All Courses...Starting to update views");
        coursesList = list;
        yearList = createListOfYears();
        updateSpinner();
        updateListView();
        break;
      case FULL_INFO_FOR_COURSE:
        Log.v(TAG, "Recieved Detailed data for a Courses... switching to Info Activity");
        Session.getInstance().clickedCourse = list.get(0);
        Session.getInstance().lastClick = list.get(0);
        Intent intent = new Intent(CoursesActivity2.this, InfoActivity.class);
        startActivity(intent);
        break;
      default:
        Log.v(TAG, "Recieve ERROR switching to Main Activity");
        Intent errorintent = new Intent(CoursesActivity2.this, MainActivity.class);
        startActivity(errorintent);
    }
  }

  private List<String> createListOfYears() {
    Log.v(TAG, "createListOfYears");
    List<String> years = coursesList.stream()
            .map( f -> ((Course) f).getYear())
            .distinct()
            .collect(Collectors.toList());
    years.add(0, "all");
    return years;
  }

  private void updateSpinner(){
    yearSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.textview,
            yearList)
    );
    yearSpinner.setSelection(Session.getInstance().yearSpinnerPossision);
    yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position,
                                 long id) {
        if (position != 0)  {
          listView.setAdapter(new ArrayAdapter<Formatable>(   CoursesActivity2.this,
                  android.R.layout.simple_list_item_1,
                  coursesList.stream()
                          .filter( c -> ((Course) c).getYear().equals(yearList.get(position)))
                          .collect(Collectors.toList()))
          );
          Session.getInstance().yearSpinnerPossision=position;
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parentView) {}

    });
  }

  private void updateListView(){
    listView.setAdapter(new ArrayAdapter<Formatable>(this, android.R.layout.simple_list_item_1,
            coursesList)
    );
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int courseId = ((Formatable)listView.getAdapter().getItem(position)).getId();
        Log.v(TAG, "ListView clicked id: "+courseId);
        drs.requestData(DataRetrievalService2.GETTYPE.FULL_INFO_FOR_COURSE, courseId,
                CoursesActivity2.this);
      }
    });
  }

}
