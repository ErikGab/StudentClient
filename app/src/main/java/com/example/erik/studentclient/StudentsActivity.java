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
import com.example.erik.studentclient.formatables.Course;
import com.example.erik.studentclient.storage.DataRetievalService;
import com.example.erik.studentclient.storage.DataRetrievalException;

import java.util.List;
import java.util.stream.Collectors;

public class StudentsActivity extends AppCompatActivity {

    Spinner courseSpinner;
    ListView listView;
    EditText inputSearch;
    List<Course> coursesList;
    List<Student> studentsList;
    DataRetievalService drs;

    private static final String TAG = "StudentsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        drs = DataRetievalService.getInstance();

        try {
            studentsList = drs.allStudents();
            coursesList = drs.allCourses();
        } catch (DataRetrievalException dre) {
            Log.v(TAG, "onCreate: Failed to retrieve items!\n"+dre.getMessage());
            Intent intent = new Intent(StudentsActivity.this, MainActivity.class);
            startActivity(intent);
        }

        listView = (ListView) findViewById(R.id.student_listview);
        listView.setAdapter(new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_1, studentsList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int studentId = ((Student)listView.getAdapter().getItem(position)).getId();
                Log.v(TAG, "ListView clicked id: "+studentId);
                Session.getInstance().clickedStudent=(Student)FormatableMockData.getStudent(0).get(0);
                Session.getInstance().lastClick=FormatableMockData.getStudent(0).get(0);
                Intent intent = new Intent(StudentsActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });
        Log.v(TAG, "onCreate: listView done");

        courseSpinner = (Spinner) findViewById(R.id.student_select_spinner);
        courseSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item,R.id.textview, courseListForSpinner(coursesList)));
        courseSpinner.setSelection(Session.getInstance().courseSpinnerPossision);
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Session.getInstance().courseSpinnerPossision=position;
                try {
                    if (position == 0){
                        Log.v(TAG, "All courses selected in spinner");
                        studentsList = drs.allStudents();
                    }
                    else {
                        int courseId = coursesList.get(position-1).getId();
                        //IMPORTANT! -1 on position on row above is due to an "all" element
                        // that is added first in spinnerlist, see:courseListForSpinner
                        Log.v(TAG, "Course with id "+courseId+" selected in spinner");
                        studentsList = drs.allStudentsInCourse(courseId);
                    }
                    listView.setAdapter(new ArrayAdapter<Student>(StudentsActivity.this, android.R.layout.simple_list_item_1, studentsList));
                } catch (DataRetrievalException dre) {
                    Log.v(TAG, "setOnItemSelectedListener: Failed to retrieve students!\n"+dre.getMessage());
                    Intent intent = new Intent(StudentsActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        Log.v(TAG, "onCreate: spinner done");

        inputSearch = (EditText) findViewById(R.id.student_searchinput);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                //((ArrayAdapter)((ListView)findViewById(R.id.student_listview)).getAdapter()).getFilter().filter(cs);

                // Filter above is shitty, only searches begining of words, but probably better performance than what is used below.
                // Revert to shitty filter if preformance of streams filter below is to slow when number of students rises.

                listView.setAdapter(new ArrayAdapter<Student>(StudentsActivity.this, android.R.layout.simple_list_item_1, studentsList.stream()
                    .filter( student -> student.toString().toLowerCase().contains(cs.toString().toLowerCase()))
                    .collect(Collectors.toList())));
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
            @Override
            public void afterTextChanged(Editable arg0) {}
        });
        Log.v(TAG, "onCreate: inputSearch done");
    }

    private List<String> courseListForSpinner(List<Course> list){
        Log.v(TAG, "courseListForSpinner");
        List<String> returnee = list.stream()
                .map(c -> c.getName())
                .collect(Collectors.toList());
        returnee.add(0, "all");
        return returnee;
    }
}
