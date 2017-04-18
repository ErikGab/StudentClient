package se.yrgo.erik.studentclient.dataretrieval.CacheDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CacheDBHelper extends SQLiteOpenHelper {

  public static final String TABLE_STUDENTS = "students";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_NAME = "name";
  public static final String COLUMN_SURNAME = "surname";

  private static final String DATABASE_NAME = "students.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
          + TABLE_STUDENTS + "( " + COLUMN_ID
          + " integer primary key autoincrement, " + COLUMN_NAME
          + " text not null, " + COLUMN_SURNAME + " text not null);";

  public CacheDBHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(CacheDBHelper.class.getName(),
            "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
    onCreate(db);
  }

}