package se.yrgo.erik.studentclient.storage;


import java.util.List;

import se.yrgo.erik.studentclient.formatables.Formatable;

public interface DataRetrievalClient {
  public void recieveFromService(List<Formatable> list, DataRetrievalService2.GETTYPE requestedData);
}
