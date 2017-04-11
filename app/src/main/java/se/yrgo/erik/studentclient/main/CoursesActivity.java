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

import java.util.List;
import java.util.stream.Collectors;

public class CoursesActivity extends AppCompatActivity {

    private List<String> yearList;
    private Spinner yearSpinner;
    private List<Course> coursesList;
    private ListView listView;
    private static final String TAG = "CoursesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        setContentView(R.layout.activity_courses);

        yearList = FormatableMockData.getCoursesByYear(0).stream()
                                                         .map( f -> f.getYear())
                                                         .collect(Collectors.toList());
        yearSpinner = (Spinner) findViewById(R.id.course_select_spinner);
        yearSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.textview, yearList));
        yearSpinner.setSelection(Session.getInstance().yearSpinnerPossision);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //((ArrayAdapter) ((ListView) findViewById(R.id.course_listview)).getAdapter()).getFilter().filter(yearList.get(position));
                listView.setAdapter(new ArrayAdapter<Course>(   CoursesActivity.this,
                                                                android.R.layout.simple_list_item_1,
                                                                coursesList.stream()
                                                                .filter( c -> c.getYear().equals(yearList.get(position)))
                                                                .collect(Collectors.toList())));
                Session.getInstance().yearSpinnerPossision=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        Log.v(TAG, "onCreate: Spinner Done");

        coursesList = FormatableMockData.getCoursesByYear(0);
        listView = (ListView) findViewById(R.id.course_listview);
        listView.setAdapter(new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_1, coursesList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Session.getInstance().clickedCourse=(Course)parent.getItemAtPosition(position);
                Session.getInstance().clickedCourse=FormatableMockData.getCourse(0).get(0);
                Session.getInstance().lastClick=FormatableMockData.getCourse(0).get(0);
                Intent intent = new Intent(CoursesActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });
        Log.v(TAG, "onCreate: Listview Done");
    }

}
