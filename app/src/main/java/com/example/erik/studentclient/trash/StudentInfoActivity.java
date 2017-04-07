package com.example.erik.studentclient.trash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.erik.studentclient.Formatable;
import com.example.erik.studentclient.MapAdapter;
import com.example.erik.studentclient.R;
import com.example.erik.studentclient.Session;
import com.example.erik.studentclient.Student;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentInfoActivity extends AppCompatActivity {

    private Student currentStudent;
    private List<String> subitemTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        currentStudent = Session.getInstance().clickedStudent;
        subitemTypes = findSubitemTypes();

        TextView propertiesHeader = (TextView) findViewById(R.id.properties_textview);
        propertiesHeader.setText(R.string.student_info_header);

        ListView propertieslistView = (ListView) findViewById(R.id.properties_listview);
        propertieslistView.setAdapter(new MapAdapter(flattenFormatable()));

        LinearLayout layout = (LinearLayout) findViewById(R.id.formatable_item_layout);

    }

    private List<String> findSubitemTypes(){
        List<String> returningList = new ArrayList<>();
        for(Formatable item:currentStudent.getSubItems()){
            if (!returningList.contains(item.getItemType())){
                returningList.add(item.getItemType());
            }
        }
        return returningList;
    }

    private Map<String,String> flattenFormatable(){
        Map<String,String> mapForListView = currentStudent.getProperties();
        for (String type:subitemTypes) {
            if (type.equals("phonenumbers")) {
                Map<String, String> phonenumbers = currentStudent.getSubItems().stream().filter(f -> f.getItemType().equals(type)).collect(Collectors.toList()).get(0).getProperties();
                for (String phonetype : phonenumbers.keySet()) {
                    mapForListView.put("phone: " + phonetype, phonenumbers.get(phonetype));
                }
            } else if (type.equals("course") || type.equals("student")) {
                List<Formatable> items = currentStudent.getSubItems().stream().filter(f -> f.getItemType().equals(type)).collect(Collectors.toList());
                for (Formatable item:items){
                    mapForListView.put(item.getItemType(), item.toListViewString());
                }
            }
        }
        return mapForListView;
    }

}
