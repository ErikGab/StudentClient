package se.yrgo.erik.studentclient;

import org.junit.Test;

import java.util.List;

import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalException;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalService;
import se.yrgo.erik.studentclient.formatables.Formatable;

import static org.junit.Assert.*;

public class DataRetrieverServiceTest {

  //Can not run due to Log.v in DataRetrievelService... 2Do Investigate...
  /*@Test
  public void drsCanGetStuffFromMockData() throws DataRetrievalException {
    DataRetrievalService drs = DataRetrievalService.getInstance();
    drs.setDataRetriever("jsonMock");
    List<Formatable> retrievedData = drs.allCourses();
    assertTrue(retrievedData.size() == 2);
  }*/
}