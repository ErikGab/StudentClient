package se.yrgo.erik.studentclient.main;

public class ListViewRow {
  private TYPE rowType;
  private String rowName;
  private String rowData;
  private int traceId;

  public ListViewRow(TYPE rowType, String rowData) {
    this(rowType, null, rowData);
  }

  public ListViewRow(TYPE rowType,String rowName, String rowData) {
    this(rowType, rowName, rowData, 0);
  }

  public ListViewRow(TYPE rowType,String rowName, String rowData, int traceId) {
    this.rowData = rowData;
    this.rowName = rowName;
    this.rowType = rowType;
    this.traceId = traceId;
  }

  public TYPE getRowType() {
    return rowType;
  }

  public String getRowName() {
    return rowName;
  }

  public String getRowData() {
    return rowData;
  }

  public int getTraceId() {
    return traceId;
  }

  public static enum TYPE {
    HEADER,
    STUDENT,
    COURSE,
    OTHER;
  }
}
