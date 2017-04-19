package se.yrgo.erik.studentclient.dataretrieval.CacheDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import se.yrgo.erik.studentclient.dataretrieval.parsers.DataParserException;

public class CacheDB {

  private SQLiteDatabase database;
  private CacheDBHelper dbHelper;
  private static final String TAG = "CacheDB";

  public CacheDB(Context context) {
    dbHelper = new CacheDBHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public void addResponse(String response, String request, String contentType, String requestType) {
    ContentValues values = new ContentValues();
    values.put(CacheDBHelper.COLUMN_CONTENTTYPE, contentType);
    values.put(CacheDBHelper.COLUMN_REQUESTTYPE, requestType);
    values.put(CacheDBHelper.COLUMN_REQUEST, request);
    values.put(CacheDBHelper.COLUMN_RESPONSE, response);
    String where = CacheDBHelper.COLUMN_REQUEST + " = \"" + request + "\"";
    try {
      database.delete(CacheDBHelper.TABLE_RESPONSECACHE, where, null);
      database.insert(CacheDBHelper.TABLE_RESPONSECACHE, null, values);
    } catch (android.database.sqlite.SQLiteException sqle) {
      Log.v(TAG, "DEAR SIR, YOUR SQL IS BAD. GÖR OM GÖR RÄTT!");
    }
  }

  public Map<String, String> getResponse(String request) {

    String table = CacheDBHelper.TABLE_RESPONSECACHE;
    String[] columns = {CacheDBHelper.COLUMN_TIMESTAMP, CacheDBHelper.COLUMN_CONTENTTYPE,
            CacheDBHelper.COLUMN_REQUESTTYPE, CacheDBHelper.COLUMN_RESPONSE};
    String where = CacheDBHelper.COLUMN_REQUEST + " = \"" + request + "\"";
    String orderBy = CacheDBHelper.COLUMN_TIMESTAMP; // THIS IS REDUNDANCY, ONLY ONE TUPEL SHOULD BE QUERIED
    Cursor cursor = database.query(table, columns, where, null, null, null, orderBy);
    cursor.moveToFirst();
    Map<String, String> responseMap = new HashMap<>();
    if (!cursor.isAfterLast()) {
      responseMap.put("time", cursor.getString(0));
      responseMap.put("contentType", cursor.getString(1));
      responseMap.put("requestType", cursor.getString(2));
      responseMap.put("response", cursor.getString(3));
    }
    return responseMap;
  }

}