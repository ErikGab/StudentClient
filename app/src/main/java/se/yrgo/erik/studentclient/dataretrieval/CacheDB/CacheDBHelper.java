package se.yrgo.erik.studentclient.dataretrieval.CacheDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CacheDBHelper extends SQLiteOpenHelper {

  private static final String TAG = "CacheDBHelper";

  public static final String TABLE_RESPONSECACHE = "responsedata";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_TIMESTAMP = "date";
  public static final String COLUMN_CONTENTTYPE = "contenttype";
  public static final String COLUMN_REQUESTTYPE = "requesttype";
  public static final String COLUMN_REQUEST = "request";
  public static final String COLUMN_RESPONSE = "response";

  private static final String DATABASE_NAME = "response.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String CREATE_DB_QUERY =
          "CREATE TABLE " + TABLE_RESPONSECACHE + "( "
          + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
          + COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
          + COLUMN_CONTENTTYPE + " TEXT NOT NULL, "
          + COLUMN_REQUESTTYPE + " TEXT NOT NULL, "
          + COLUMN_REQUEST + " TEXT NOT NULL, "
          + COLUMN_RESPONSE + " TEXT NOT NULL);";

  public CacheDBHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    Log.v(TAG, "onCreate");
    database.execSQL(CREATE_DB_QUERY);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(CacheDBHelper.class.getName(),
            "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESPONSECACHE);
    onCreate(db);
  }

}