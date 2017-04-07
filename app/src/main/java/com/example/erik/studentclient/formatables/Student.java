package com.example.erik.studentclient.formatables;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Student implements Formatable{

    private int id;
    private final String ITEMTYPE = "student";
    private Map<String, String> properties;
    private List<Formatable> subItems = new ArrayList<>();

    public Student(int id, String name, String surname){
        this.id = id;
        properties = new LinkedHashMap<>();
        properties.put("name", name);
        properties.put("surname", surname);
    }
    public Student(int id, String name, String surname, int age, String postAddress, String streetAddress){
        this(id, name, surname, age, postAddress, streetAddress, null, null);
    }
    public Student(int id, String name, String surname, int age, String postAddress, String streetAddress, List<Formatable> courses){
        this(id, name, surname, age, postAddress, streetAddress, null, courses);
    }
    public Student(int id, String name, String surname, int age, String postAddress, String streetAddress, Formatable phonenumbers){
        this(id, name, surname, age, postAddress, streetAddress, phonenumbers, null);
    }
    public Student(int id, String name, String surname, int age, String postAddress, String streetAddress, Formatable phonenumbers, List<Formatable> courses){
        this.id = id;
        properties = new LinkedHashMap<>();
        properties.put("name", name);
        properties.put("surname", surname);
        properties.put("age", Integer.toString(age));
        properties.put("postAddress", postAddress);
        properties.put("streetAddress", streetAddress);
        if (phonenumbers != null) {
            subItems.add(phonenumbers);
        }
        if (courses != null) {
            for(Formatable course:courses){
                subItems.add(course);
            }
        }
    }
    public Student(int id, Map<String,String> properties, List<Formatable> subItems){
        this.id = id;
        this.properties = properties;
        this.subItems = subItems;
    }
    public Student(int id, Map<String,String> properties){
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

    public String getName(){
        return String.valueOf(properties.get("name"));
    }

    public String getSurname(){
        return String.valueOf(properties.get("surname"));
    }

    @Override
    public String toString(){
        return getName() + " " + getSurname();
    }

    public String toListViewString(){
        return new StringBuilder()
                .append(getName())
                .append(" ")
                .append(getSurname())
                .append(" ")
                .append(String.valueOf(properties.get("status")))
                .append(" ")
                .append(String.valueOf(properties.get("grade")))
                .toString();
    }
}
