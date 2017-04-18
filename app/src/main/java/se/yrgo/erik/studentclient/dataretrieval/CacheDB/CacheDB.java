package se.yrgo.erik.studentclient.dataretrieval.CacheDB;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import se.yrgo.erik.studentclient.formatables.Student;

public class CacheDB {

  // Database fields
  private SQLiteDatabase database;
  private CacheDBHelper dbHelper;
  private String[] allColumns = { CacheDBHelper.COLUMN_ID,
          CacheDBHelper.COLUMN_NAME, CacheDBHelper.COLUMN_SURNAME };

  public CacheDB(Context context) {
    dbHelper = new CacheDBHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public void addStudent(Student student) {
    ContentValues values = new ContentValues();
    values.put(CacheDBHelper.COLUMN_NAME, student.getName());
    values.put(CacheDBHelper.COLUMN_SURNAME, student.getSurname());
    long insertId = database.insert(CacheDBHelper.TABLE_STUDENTS, null, values);
  }

//  public void deleteComment(Comment comment) {
//    long id = comment.getId();
//    System.out.println("Comment deleted with id: " + id);
//    database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
//            + " = " + id, null);
//  }

//  public List<Comment> getAllComments() {
//    List<Comment> comments = new ArrayList<Comment>();
//
//    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
//            allColumns, null, null, null, null, null);
//
//    cursor.moveToFirst();
//    while (!cursor.isAfterLast()) {
//      Comment comment = cursorToComment(cursor);
//      comments.add(comment);
//      cursor.moveToNext();
//    }
//    // make sure to close the cursor
//    cursor.close();
//    return comments;
//  }

//  private Comment cursorToComment(Cursor cursor) {
//    Comment comment = new Comment();
//    comment.setId(cursor.getLong(0));
//    comment.setComment(cursor.getString(1));
//    return comment;
//  }
}