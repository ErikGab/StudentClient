package se.yrgo.erik.studentclient.main;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import se.yrgo.erik.studentclient.dataretrieval.CacheDB.PreCacheRunner;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalService;

public class SettingsActivity extends AppCompatActivity {

  Button runPreCacheBtn;
  Button clearCacheBtn;
  String orgBtnText;
  TextView cacheSize;
  Button formatBtn;
  DataRetrievalService drs;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    drs = DataRetrievalService.getInstance();

    runPreCacheBtn = (Button) findViewById(R.id.precache_btn);
    clearCacheBtn = (Button) findViewById(R.id.clearcache_btn);
    cacheSize = (TextView) findViewById(R.id.cachesize_textview);
    orgBtnText = runPreCacheBtn.getText().toString();
    formatBtn = (Button) findViewById(R.id.formatbutton);
    updateFormatButton();

    formatBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String currentFormat = drs.getFormat();
        if (currentFormat.equals("json")) {
          drs.setFormat("xml");
          updateFormatButton();
        } else if (currentFormat.equals("xml")) {
          drs.setFormat("json");
          updateFormatButton();
        }
      }
    });
    new SizeOfCacheCheck().execute();

    runPreCacheBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          runPreCacheBtn.setClickable(false);
          new RunPreCacheRunner().execute();
        }
    });

    clearCacheBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        new ClearCache().execute();
      }
    });

  }

  private void updateFormatButton() {
    formatBtn.setText(getString(R.string.switch_format_btn_text) + drs.getFormat());
  }

  protected class RunPreCacheRunner extends AsyncTask<Integer, Integer, Integer> {

    PreCacheRunner pcr;

    public RunPreCacheRunner() {
      super();
      this.pcr = new PreCacheRunner();
      this.pcr.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
      while (pcr.getStatus() != AsyncTask.Status.FINISHED) {
        try {
          Thread.sleep(250);
        } catch (InterruptedException ie) {
          //ignore
        }
        publishProgress(pcr.getProgress());
      }
      return null;
    }

    @Override
    protected void onPostExecute(Integer result) {
      runPreCacheBtn.setText(orgBtnText);
      runPreCacheBtn.setClickable(true);
      new SizeOfCacheCheck().execute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
      super.onProgressUpdate(values);
      runPreCacheBtn.setText(orgBtnText + " Running...(" + values[0] + "%)" );
    }

  }

  protected class ClearCache extends AsyncTask<Integer, Integer, Integer> {

    @Override
    protected Integer doInBackground(Integer... integers) {
      drs.clearCache();
      return null;
    }

    @Override
    protected void onPostExecute(Integer result) {
      new SizeOfCacheCheck().execute();
    }

  }

  protected class SizeOfCacheCheck extends AsyncTask<Integer, Integer, Integer> {

    int size;

    @Override
    protected Integer doInBackground(Integer... integers) {
      size = drs.cacheSize();
      return null;
    }

    @Override
    protected void onPostExecute(Integer result) {
      cacheSize.setText(size + " " + getString(R.string.cache_size_deco));
    }

  }


}
