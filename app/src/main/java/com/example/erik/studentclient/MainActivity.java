package com.example.erik.studentclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button studentButton = (Button) findViewById(R.id.students_btn);
        Button courseButton = (Button) findViewById(R.id.courses_btn);

        studentButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch2StudentsActivity();
            }
        });

        courseButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch2CoursesActivity();
            }
        });

    }

    private void switch2StudentsActivity() {
        Intent intent = new Intent(this, StudentsActivity.class);
        startActivity(intent);
    }

    private void switch2CoursesActivity() {
        Intent intent = new Intent(this, CoursesActivity.class);
        startActivity(intent);
    }

}

