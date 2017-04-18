package se.yrgo.erik.studentclient.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import se.yrgo.erik.studentclient.formatables.Formatable;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalService;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalException;

public class StudentsActivity extends AppCompatActivity {

  private Spinner courseSpinner;
  private ListView listView;
  private EditText inputSearch;
  private List<Formatable> coursesList;
  private List<Formatable> studentsList;
  private DataRetrievalService drs;
  private int listViewClickId;

  private static final String TAG = "StudentsActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_students);
    Log.v(TAG, "onCreate");
    drs = DataRetrievalService.getInstance();
    listView = (ListView) findViewById(R.id.student_listview);
    courseSpinner = (Spinner) findViewById(R.id.student_select_spinner);
    inputSearch = (EditText) findViewById(R.id.student_searchinput);
    new listAndSpinnerUpdate().execute();
    setListeners();
  }
  //NOTE TO SELF: BEHÖVER DENNA TA ETT ARGUMENT?
  private List<String> createListForSpinner(List<Formatable> list) {
    Log.v(TAG, "createListForSpinner");
    List<String> returnee;
    if (list == null || list.size() == 0 ) {
      returnee = new ArrayList<String>();
    } else {
      returnee = list.stream()
              .map(c -> c.getName())
              .collect(Collectors.toList());
    }
    returnee.add(0, getString(R.string.spinner_all_text));
    return returnee;
  }

  private class listAndSpinnerUpdate extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
      try {
        studentsList = drs.allStudents();
        coursesList = drs.allCourses();
      } catch (DataRetrievalException dre) {
        Log.v(TAG, "Failed to retrieve items!\n"+dre.getMessage());
        Intent intent = new Intent(StudentsActivity.this, MainActivity.class);
        startActivity(intent);
        this.cancel(true);
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      super.onPostExecute(result);
      listView.setAdapter(new ArrayAdapter<Formatable>(StudentsActivity.this,
              android.R.layout.simple_list_item_1, studentsList)
      );
      courseSpinner.setAdapter(new ArrayAdapter<String>(StudentsActivity.this,
              R.layout.spinner_item,R.id.textview, createListForSpinner(coursesList))
      );
      courseSpinner.setSelection(Session.getInstance().courseSpinnerPossision);
    }
  }

  private class CourseSpinnerAction extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
      try {
        if (Session.getInstance().courseSpinnerPossision == 0) {
          Log.v(TAG, "All courses selected in spinner");
          studentsList = drs.allStudents();
        } else {
          int courseId = coursesList.get(Session.getInstance().courseSpinnerPossision - 1).getId();
          //IMPORTANT! -1 on position on row above is due to an "all" element
          // that is added first in spinnerlist, see:createListForSpinner
          Log.v(TAG, "Course with id " + courseId + " selected in spinner");
          studentsList = drs.allStudentsInCourse(courseId);
        }
      } catch (DataRetrievalException dre) {
        Log.v(TAG, "CourseSpinnerAction: Failed to retrieve students!\n" + dre.getMessage());
        Intent intent = new Intent(StudentsActivity.this, MainActivity.class);
        startActivity(intent);
        this.cancel(true);
      }
      return null;
    }

    @Override
    protected void onPostExecute (Void result){
      super.onPostExecute(result);
      listView.setAdapter(new ArrayAdapter<Formatable>(StudentsActivity.this,
              android.R.layout.simple_list_item_1, studentsList)
      );
    }
  }

  private class SwitchToInfoActivity extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
      try {
        Session.getInstance().clickedStudent = drs.fullInfoForStudent(listViewClickId).get(0);
        Session.getInstance().lastClick = Session.getInstance().clickedStudent;
      } catch (DataRetrievalException dre) {
        Log.v(TAG, "setOnItemClickListener: Failed to retrieve student!\n" + dre.getMessage());
        Intent intent = new Intent(StudentsActivity.this, MainActivity.class);
        startActivity(intent);
        this.cancel(true);
      } catch (IndexOutOfBoundsException ioobe) {
        Log.v(TAG, "setOnItemClickListener: Failed to retrieve student!");
        Intent intent = new Intent(StudentsActivity.this, MainActivity.class);
        startActivity(intent);
        this.cancel(true);
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      super.onPostExecute(result);
      Intent intent = new Intent(StudentsActivity.this, InfoActivity.class);
      startActivity(intent);
    }
  }

  private void setListeners() {
    Log.v(TAG, "setListeners");
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listViewClickId = ((Formatable) listView.getAdapter().getItem(position)).getId();
        Log.v(TAG, "ListView clicked id: " + listViewClickId);
        new SwitchToInfoActivity().execute();
      }
    });
    courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

      @Override
      public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position,
                                 long id) {
        Session.getInstance().courseSpinnerPossision = position;
        new CourseSpinnerAction().execute();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parentView) {
      }
    });
    inputSearch.addTextChangedListener(new TextWatcher() {

      @Override
      public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
        //((ArrayAdapter)((ListView)findViewById(R.id.student_listview)).getAdapter()).getFilter()
        // .filter(cs);

        // Filter above is shitty, only searches begining of words, but probably has some advantages
        // since it is default filter for adapters in android. Filter below filters like a boss.

        listView.setAdapter(new ArrayAdapter<Formatable>(StudentsActivity.this,
                android.R.layout.simple_list_item_1, studentsList.stream()
                .filter( s -> s.toString().toLowerCase().contains(cs.toString().toLowerCase()))
                .collect(Collectors.toList()))
        );
      }

      @Override
      public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

      @Override
      public void afterTextChanged(Editable arg0) {}
    });
  }

}
