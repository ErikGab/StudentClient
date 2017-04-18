package se.yrgo.erik.studentclient.main;

import se.yrgo.erik.studentclient.formatables.FormatableType;

public class ListViewRow {
  private FormatableType rowType;
  private String rowName;
  private String rowData;
  private int traceId;

  public ListViewRow(FormatableType rowType, String rowData) {
    this(rowType, null, rowData);
  }

  public ListViewRow(FormatableType rowType, String rowName, String rowData) {
    this(rowType, rowName, rowData, 0);
  }

  public ListViewRow(FormatableType rowType, String rowName, String rowData, int traceId) {
    this.rowData = rowData;
    this.rowName = rowName;
    this.rowType = rowType;
    this.traceId = traceId;
  }

  public FormatableType getRowType() {
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

}
