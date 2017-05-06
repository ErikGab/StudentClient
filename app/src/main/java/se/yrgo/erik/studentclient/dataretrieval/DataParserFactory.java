package se.yrgo.erik.studentclient.dataretrieval;


import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import se.yrgo.erik.studentclient.dataretrieval.parsers.DataParserException;

public class DataParserFactory {

  private static String classNames[] = {
          "se.yrgo.erik.studentclient.dataretrieval.parsers.JSONDataParser",
          "se.yrgo.erik.studentclient.dataretrieval.parsers.XMLDataParser"
  };
  private static Map<String,DataParser> parsers;
  private static final String TAG = "DataParserFactory";
  //private static DataParserFactory instance;

  static {
    Log.v(TAG, "static block running");
    //instance = new DataParserFactory();
    parsers = new HashMap<>();
    try{
      for (String className : classNames) {
        Log.v(TAG, "loading class: " + className);
        Class.forName(className);
      }
    }catch(ClassNotFoundException cnfe){
      System.err.println(cnfe.getMessage());
    }
  }

  private DataParserFactory() {}

  public static void register(String format, DataParser parser) {
    Log.v(TAG, "registering: "+format);
    parsers.put(format, parser);
  }

  public static DataParser getParser(String requestedType) throws DataParserException {
    if (parsers.containsKey(requestedType)) {
      return parsers.get(requestedType);
    } else {
      Log.v(TAG, "No parser found of type: " + requestedType);
      throw new DataParserException("No parser found of type: " + requestedType);
    }
  }

}
