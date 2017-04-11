package com.example.erik.studentclient.formatables;

import android.util.Log;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class FormatableItem implements Formatable {

    private int id;
    private String itemType;
    private Map<String, String> properties;
    private List<Formatable> subItems = new ArrayList<>();
    private static final String TAG = "FormatableItem";

    public FormatableItem(String itemType, int id, Map<String,String> properties, List<Formatable> subItems){
        this.id = id;
        this.itemType = itemType;
        this.properties = properties;
        this.subItems = subItems;
        Log.v(TAG, "new created of type "+itemType+" with id "+id);
    }
    public FormatableItem(String itemType, int id, Map<String,String> properties){
        this.id = id;
        this.itemType = itemType;
        this.properties = properties;
        Log.v(TAG, "new created of type "+itemType+" with id "+id);
    }
    public void addSubItem(Formatable item){
        subItems.add(item);
    }
    public int getId(){
        return id;
    }
    public String getItemType(){
        return itemType;
    }
    public Map<String,String> getProperties(){
        return properties;
    }
    public List<Formatable> getSubItems(){
        return subItems;
    }
    public String toString(){
        return itemType + " " + Integer.toString(id);
    }
    public String toListViewString(){ return toString(); }
}