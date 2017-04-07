package com.example.erik.studentclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Course implements Formatable {

    private int id;
    private final String ITEMTYPE = "course";
    private Map<String, String> properties;
    private List<Formatable> subItems = new ArrayList<>();

    public Course(int id, String name){
        this.id = id;
        properties.put("name",name);
    }
    public Course(int id, String startDate, String endDate, String name, int points, String description){
        this(id, startDate, endDate, name, points, description, null);
    }
    public Course(int id, String startDate, String endDate, String name, int points, String description, List<Student> students){
        this.id = id;
        properties.put("startDate", startDate);
        properties.put("endDate", endDate);
        properties.put("name", name);
        properties.put("points", Integer.toString(points));
        properties.put("description", description);
        if (students != null) {
            //subItems = students;
            for (Formatable student:students){
                subItems.add(student);
            }
        }
    }
    public Course(int id, Map<String,String> properties, List<Formatable> subItems){
        this.id = id;
        this.properties = properties;
        this.subItems = subItems;
    }
    public Course(int id, Map<String,String> properties){
        this.id = id;
        this.properties = properties;
    }

    @Override
    public int getId(){
        return id;
    }
    @Override
    public String getItemType(){
        return ITEMTYPE;
    }
    @Override
    public Map<String,String> getProperties(){
        return properties;
    }
    @Override
    public List<Formatable> getSubItems(){
        return subItems;
    }

    public String getYear(){
        int len = String.valueOf(properties.get("name")).length();
        return String.valueOf(properties.get("name")).substring(len-4, len);
    }

    public String getName(){
        return String.valueOf(properties.get("name"));
    }

    @Override
    public String toString(){
        return getName() + " id(" + Integer.toString(id) + ")";
    }

    public String toListViewString(){
        return new StringBuilder().append(getName()).append(" ").append(String.valueOf(properties.get("description"))).toString();
    }
}
