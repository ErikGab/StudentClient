package com.example.erik.studentclient.parseresponse;
import com.example.erik.studentclient.formatables.Formatable;
import com.example.erik.studentclient.formatables.FormatableItem;
import com.example.erik.studentclient.formatables.Student;
import com.example.erik.studentclient.formatables.FormatableMockData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.FileHandler;

public class DataParserUtil {

    public static List<Student> Json2Students(String json){

        List<Student> returnee = new ArrayList<Student>();

        try {

            //Create JsonObject from String
            JSONObject jsonObj = new JSONObject(json);

            // Getting JSON Array node
            String type = null;
            if ( jsonObj.has("student")) {
                type = "student";
            } else if ( jsonObj.has("course")) {
                type = "course";
            }
            if (type != null) {
                JSONArray items = jsonObj.getJSONArray(type);
                for (int i = 0; i < items.length(); i++) {
                    JSONObject c = items.getJSONObject(i);
                    if (c.has("id") && c.has("name") && c.has("sureName") && c.has("age") && c.has("postAddress") && c.has("streetAddress")) {
                        returnee.add(new Student(   c.getInt("id"),
                                                    c.getString("name"),
                                                    c.getString("surname"),
                                                    c.getInt("age"),
                                                    c.getString("postAddress"),
                                                    c.getString("streetAddress"),
                                                    extractPhoneNumber(c),
                                                    extractCourses(c)));
                    } else if (c.has("id") && c.has("name") && c.has("sureName")) {
                        returnee.add(new Student(   c.getInt("id"),
                                                    c.getString("name"),
                                                    c.getString("surname")));
                    }
                }
            }

        } catch ( JSONException jse){
            System.err.println(jse.getMessage());
        }
        return returnee;
    }


    private static FormatableItem extractPhoneNumber(JSONObject object){
        FormatableItem returnee = null;
        String key = "phonenumbers";
        if (object.has(key)){
            try {
                JSONArray numbers = object.getJSONArray(key);
                LinkedHashMap<String, String> phonenumbers = new LinkedHashMap<>();
                for (int i = 0; i < numbers.length(); i++) {
                    JSONObject c = numbers.getJSONObject(i);
                    if (c.has("mobile")) {  phonenumbers.put("mobile", c.getString("mobile"));}
                    if (c.has("home")) {  phonenumbers.put("home", c.getString("home"));}
                    if (c.has("fax")) {  phonenumbers.put("fax", c.getString("fax"));}
                    if (c.has("work")) {  phonenumbers.put("work", c.getString("work"));}

                }
                returnee = new FormatableItem("phonenumbers", 0, phonenumbers);
            } catch (JSONException jse){
                System.err.println(jse.getMessage());
            }
        }
        return returnee;
    }

    private static List<Formatable> extractCourses(JSONObject object){
        List<Formatable> returnee = new ArrayList<>();
        String key = "course";
        if (object.has(key)){
            try {
                JSONArray courses = object.getJSONArray(key);
                for (int i = 0; i < courses.length(); i++) {
                    JSONObject c = courses.getJSONObject(i);
                    LinkedHashMap<String, String> properties = new LinkedHashMap<>();
                    properties.put("name", c.getString("name"));
                    properties.put("status", c.getString("status"));
                    properties.put("grade", c.getString("grade"));
                    returnee.add(new FormatableItem("course", c.getInt("id"), properties));
                }
            } catch (JSONException jse){
                System.err.println(jse.getMessage());
            }
        }
        return returnee;
    }

}
