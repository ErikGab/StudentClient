package com.example.erik.studentclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.erik.studentclient.formatables.FormatableMockData;
import com.example.erik.studentclient.formatables.Student;
import com.example.erik.studentclient.storage.DataRetievalService;
import com.example.erik.studentclient.storage.DataRetrievalException;
import com.example.erik.studentclient.storage.JSONMockDataRetriever;
import com.example.erik.studentclient.parseresponse.DataParserUtil;

import java.util.List;
import java.util.stream.Collectors;

public class StudentsActivity extends AppCompatActivity {

    Spinner courseSpinner;
    ListView listView;
    EditText inputSearch;
    List<String> coursesList;
    List<Student> studentsList, studentsListReduced;
    DataRetievalService drs;

    private static final String TAG = "StudentsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        drs = DataRetievalService.getInstance();

        coursesList = FormatableMockData.getCoursesByYear(0).stream().map(c -> c.getName()).collect(Collectors.toList());
        //studentsList = FormatableMockData.getStudentsByCourse(0);
        try {
            studentsList = drs.allStudents();
        } catch (DataRetrievalException dre) {
            Log.v(TAG, "onCreate: Failed to retrieve students!");
            Intent intent = new Intent(StudentsActivity.this, MainActivity.class);
            startActivity(intent);
        }
        studentsListReduced = FormatableMockData.getStudentsByCourse(0).stream()
                                                                        .filter(f -> f.getId() < 50)
                                                                        .collect(Collectors.toList());

        courseSpinner = (Spinner) findViewById(R.id.student_select_spinner);
        courseSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item,R.id.textview, coursesList));
        courseSpinner.setSelection(Session.getInstance().courseSpinnerPossision);

        listView = (ListView) findViewById(R.id.student_listview);
        listView.setAdapter(new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_1, studentsList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Session.getInstance().clickedStudent=(Student)parent.getItemAtPosition(position);
                Session.getInstance().clickedStudent=(Student)FormatableMockData.getStudent(0).get(0);
                Session.getInstance().lastClick=FormatableMockData.getStudent(0).get(0);
                Intent intent = new Intent(StudentsActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        inputSearch = (EditText) findViewById(R.id.student_searchinput);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                ((ArrayAdapter)((ListView)findViewById(R.id.student_listview)).getAdapter()).getFilter().filter(cs);
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
            @Override
            public void afterTextChanged(Editable arg0) {}
        });

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Session.getInstance().courseSpinnerPossision=position;
                if (position == 0){
                    listView.setAdapter(new ArrayAdapter<Student>(StudentsActivity.this, android.R.layout.simple_list_item_1, studentsList));
                    Log.v(TAG, "full list"+studentsList.size());
                } else {
                    listView.setAdapter(new ArrayAdapter<Student>(StudentsActivity.this, android.R.layout.simple_list_item_1, studentsListReduced));
                    Log.v(TAG, "reduced list"+studentsListReduced.size());
                }
                //listView.invalidateViews();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }




}
