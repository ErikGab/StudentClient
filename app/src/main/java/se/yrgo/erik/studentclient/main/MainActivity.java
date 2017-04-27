package se.yrgo.erik.studentclient.main;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import se.yrgo.erik.studentclient.dataretrieval.CacheDB.PreCacheRunner;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.v(TAG, "onCreate");
    setContentView(R.layout.activity_main);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    Session.getInstance().context = getApplicationContext();
    Button studentButton = (Button) findViewById(R.id.students_btn);
    Button courseButton = (Button) findViewById(R.id.courses_btn);
    Button settingsButton = (Button) findViewById(R.id.settings_btn);

    studentButton.setOnClickListener( new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //Session.getInstance().selectActivity= Session.ListType.STUDENT;
        switch2StudentsActivity();
      }
    });

    courseButton.setOnClickListener( new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //Session.getInstance().selectActivity= Session.ListType.COURSE;
        switch2CoursesActivity();
      }
    });

    settingsButton.setOnClickListener( new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //Session.getInstance().selectActivity= Session.ListType.COURSE;
        switch2SettingsActivity();
      }
    });
  }

  private void switch2StudentsActivity() {
    Log.v(TAG, "switch2StudentsActivity");
    Intent intent = new Intent(this, StudentsActivity.class);
    startActivity(intent);
  }

  private void switch2CoursesActivity() {
    Log.v(TAG, "switch2CoursesActivity");
    Intent intent = new Intent(this, CoursesActivity.class);
    startActivity(intent);
  }

  private void switch2SettingsActivity() {
    Log.v(TAG, "switch2SettingsActivity");
    Intent intent = new Intent(this, SettingsActivity.class);
    startActivity(intent);
  }

}

