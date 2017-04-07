package com.example.erik.studentclient;

public class ListViewRow {
    private TYPE rowType;
    private String rowName;
    private String rowData;

    public ListViewRow(TYPE rowType, String rowData){
        this(rowType, null, rowData);
    }
    public ListViewRow(TYPE rowType,String rowName, String rowData){
        this.rowData = rowData;
        this.rowName = rowName;
        this.rowType = rowType;
    }

    public TYPE getRowType(){
        return rowType;
    }

    public String getRowName(){
        return rowName;
    }

    public String getRowData(){
        return rowData;
    }

    public static enum TYPE {
        HEADER,
        STUDENT,
        COURSE,
        OTHER;
    }
}
