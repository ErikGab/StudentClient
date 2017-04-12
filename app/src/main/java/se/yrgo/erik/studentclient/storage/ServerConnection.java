package se.yrgo.erik.studentclient.storage;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class ServerConnection {

  private static final String TAG = "ServerConnection";
  private static HttpURLConnection serverConnection;

  private ServerConnection() {}

  public static String connectAndGET(URL serverURL, Map<String,String> getParams) throws ServerConnectionException {
    try {
      String urlPath = serverURL.toString() + getParamsToString(getParams);
      serverConnection = (HttpURLConnection) new URL(urlPath).openConnection();
      serverConnection.setRequestMethod("GET");
      BufferedReader reader = new BufferedReader(
                              new InputStreamReader(
                              new BufferedInputStream(serverConnection.getInputStream())));
      StringBuilder sb = new StringBuilder();

      String line;
      try {
        while ((line = reader.readLine()) != null) {
          sb.append(line).append('\n');
        }
      } catch (IOException e) {
        throw new ServerConnectionException("Could not read response: " + e.getMessage());
      } finally {
        try {
          reader.close();
        } catch (IOException e) {
          Log.v(TAG, "Could not close reader: " + e.getMessage());
        }
      }
      return sb.toString();
    } catch (ProtocolException pe) {
      throw new ServerConnectionException("Could not connect to server: " + pe.getMessage());
    } catch (IOException ioe) {
      throw new ServerConnectionException("Could not connect to server: " + ioe.getMessage());
    }
  }

  private static String getParamsToString(Map<String,String> getParams) {
    StringBuilder sb = new StringBuilder();
    sb.append("?");
    int counter = 0;
    for (String key : getParams.keySet()) {
      sb.append(key + "=" + getParams.get(key));
      if (counter != getParams.size()) {
        sb.append("&");
      }
      counter++;
    }
    return sb.toString();
  }

}
