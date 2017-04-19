package se.yrgo.erik.studentclient.dataretrieval.parsers;


import java.util.List;

import se.yrgo.erik.studentclient.dataretrieval.DataParser;
import se.yrgo.erik.studentclient.dataretrieval.DataParserFactory;
import se.yrgo.erik.studentclient.formatables.Formatable;

public class XMLDataParser implements DataParser {

  private static final String TAG = "XMLDataParser";

  static {
    DataParserFactory.register("xml", new XMLDataParser());
  }

  private XMLDataParser() {}

  @Override
  public List<Formatable> string2Students(String data) throws DataParserException {
    return null;
  }

  @Override
  public List<Formatable> string2Courses(String data) throws DataParserException {
    return null;
  }
}
