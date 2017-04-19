package se.yrgo.erik.studentclient.dataretrieval;

import se.yrgo.erik.studentclient.dataretrieval.parsers.DataParserException;
import se.yrgo.erik.studentclient.formatables.Formatable;
import java.util.List;

public interface DataParser {

  List<Formatable> string2Students(String data) throws DataParserException;
  List<Formatable> string2Courses(String data) throws DataParserException;
}

