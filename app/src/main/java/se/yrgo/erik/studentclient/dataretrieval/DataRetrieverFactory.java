package se.yrgo.erik.studentclient.dataretrieval;


import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class DataRetrieverFactory {

  private static String classNames[] = {
          "se.yrgo.erik.studentclient.dataretrieval.retrievers.JSONMockDataRetriever",
          "se.yrgo.erik.studentclient.dataretrieval.retrievers.DefaultDataRetriever"
  };
  private static Map<String,DataRetriever> retrievers;
  private static final String TAG = "DataRetrieverFactory";
  private static DataRetrieverFactory instance;
  private static final String DEFAULT = "default";

  static {
    Log.v(TAG, "static block running");
    instance = new DataRetrieverFactory();
    retrievers = new HashMap<>();
    try{
      for (String className : classNames) {
        Log.v(TAG, "loading class: " + className);
        Class.forName(className);
      }
    }catch(ClassNotFoundException cnfe){
      System.err.println(cnfe.getMessage());
    }
  }

  private DataRetrieverFactory() {}

  public static void register(String format, DataRetriever retriever) {
    Log.v(TAG, "registering: "+format);
    retrievers.put(format, retriever);
  }

//  public static DataRetrieverFactory getInstance() {
//    return instance;
//  }

  public static DataRetriever getRetriever() throws DataRetrievalException {
    if (retrievers.containsKey(DEFAULT)) {
      return retrievers.get(DEFAULT);
    } else {
      Log.v(TAG, "No default retriever found");
      throw new DataRetrievalException("No default retriever found");
    }
  }

  public static DataRetriever getRetriever(String requestedType) throws DataRetrievalException {
    if (retrievers.containsKey(requestedType)) {
      return retrievers.get(requestedType);
    } else {
      Log.v(TAG, "No retriever found of type: " + requestedType);
      throw new DataRetrievalException("No retriever found of type: " + requestedType);
    }
  }

}
