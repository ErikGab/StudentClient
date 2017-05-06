package se.yrgo.erik.studentclient.main;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import se.yrgo.erik.studentclient.formatables.Course;
import se.yrgo.erik.studentclient.formatables.Formatable;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalService;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalException;

import java.util.List;
import java.util.stream.Collectors;

public class CoursesActivity extends AppCompatActivity {

  private List<String> yearList;
  private Spinner yearSpinner;
  private List<Formatable> coursesList;
  private ListView listView;
  private DataRetrievalService drs;
  private int listViewClickId;
  private static final String TAG = "CoursesActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_courses);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    Log.v(TAG, "onCreate");
    drs = DataRetrievalService.getInstance();
    listView = (ListView) findViewById(R.id.course_listview);
    yearSpinner = (Spinner) findViewById(R.id.course_select_spinner);
    new listAndSpinnerUpdate().execute();
    setListeners();
  }

  private class listAndSpinnerUpdate extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params) {
      try {
        coursesList = drs.allCourses();
        yearList = createListOfYears();
      } catch (DataRetrievalException dre) {
        Log.v(TAG, "Failed to retrieve items!\n"+dre.getMessage());
        Intent intent = new Intent(CoursesActivity.this, MainActivity.class);
        startActivity(intent);
        this.cancel(true);
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      super.onPostExecute(result);
      yearSpinner.setAdapter(new ArrayAdapter<String>(CoursesActivity.this, R.layout.spinner_item,
              R.id.textview, yearList));
      yearSpinner.setSelection(Session.getInstance().yearSpinnerPossision);
      listView.setAdapter(new FormatableAdapter(coursesList));
    }
  }

  private List<String> createListOfYears() {
    Log.v(TAG, "createListOfYears");
    List<String> years = coursesList.stream()
            .map(f -> ((Course) f).getYear())
            .distinct()
            .collect(Collectors.toList());
    years.add(0, getString(R.string.spinner_all_text));
    return years;
  }

  private class SwitchToInfoActivity extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params) {
      try {
        Session.getInstance().clickedCourse = drs.fullInfoForCourse(listViewClickId).get(0);
        Session.getInstance().lastClick = drs.fullInfoForCourse(listViewClickId).get(0);
      } catch (DataRetrievalException dre) {
        Log.v(TAG, "setOnItemClickListener: Failed to retrieve course!\n" + dre.getMessage());
        Intent intent = new Intent(CoursesActivity.this, MainActivity.class);
        startActivity(intent);
        this.cancel(true);
      } catch (IndexOutOfBoundsException ioobe) {
        Log.v(TAG, "setOnItemClickListener: Failed to retrieve course!");
        Intent intent = new Intent(CoursesActivity.this, MainActivity.class);
        startActivity(intent);
        this.cancel(true);
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      super.onPostExecute(result);
      Intent intent = new Intent(CoursesActivity.this, InfoActivity.class);
      startActivity(intent);
    }
  }

  private void setListeners(){
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listViewClickId = ((Formatable) listView.getAdapter().getItem(position)).getId();
        Log.v(TAG, "ListView clicked id: " + listViewClickId);
        new SwitchToInfoActivity().execute();
      }
    });
    yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position,
                                 long id) {
        if (position == 0) {
          listView.setAdapter(new FormatableAdapter(coursesList));
        } else {
          listView.setAdapter(new FormatableAdapter(coursesList.stream()
                  .filter( c -> ((Course) c).getYear().equals(yearList.get(position)))
                  .collect(Collectors.toList())));
        }
        Session.getInstance().yearSpinnerPossision=position;
      }

      @Override
      public void onNothingSelected(AdapterView<?> parentView) {}
    });
  }

}