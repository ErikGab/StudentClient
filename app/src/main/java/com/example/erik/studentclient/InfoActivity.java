package com.example.erik.studentclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.erik.studentclient.formatables.Formatable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InfoActivity extends AppCompatActivity {

    private Formatable currentItem;
    private List<String> subitemTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        currentItem = Session.getInstance().lastClick;
        subitemTypes = findSubitemTypes();

        ListView propertieslistView = (ListView) findViewById(R.id.item_info_listview);
        propertieslistView.setAdapter(new ListViewRowAdapter(transformFormatableForListView()));

        //LinearLayout layout = (LinearLayout) findViewById(R.id.formatable_item_layout);

    }

    private List<String> findSubitemTypes(){
        List<String> returningList = new ArrayList<>();
        for(Formatable item:currentItem.getSubItems()){
            if (!returningList.contains(item.getItemType())){
                returningList.add(item.getItemType());
            }
        }
        return returningList;
    }


    private List<ListViewRow> transformFormatableForListView(){
        List<ListViewRow> returningList = new ArrayList<>();
        returningList.add(new ListViewRow(ListViewRow.TYPE.HEADER, currentItem.getItemType()+" Information:"));
        for (String key:currentItem.getProperties().keySet()){
            returningList.add(new ListViewRow(ListViewRow.TYPE.STUDENT, key, currentItem.getProperties().get(key)));
        }
        for (String type:subitemTypes) {
            if (type.equals("phonenumbers")) {
                returningList.add(new ListViewRow(ListViewRow.TYPE.HEADER, "Phonenumbers:"));
                Map<String, String> phonenumbers = currentItem.getSubItems().stream().filter(f -> f.getItemType().equals(type)).collect(Collectors.toList()).get(0).getProperties();
                for (String phonetype : phonenumbers.keySet()) {
                    returningList.add(new ListViewRow(ListViewRow.TYPE.OTHER, phonetype, phonenumbers.get(phonetype)));
                }
            } else if (type.equals("course") || type.equals("student")) {
                returningList.add(new ListViewRow(ListViewRow.TYPE.HEADER, type+"s:"));
                List<Formatable> items = currentItem.getSubItems().stream().filter(f -> f.getItemType().equals(type)).collect(Collectors.toList());
                for (Formatable item:items){
                    returningList.add(new ListViewRow(convertStringToTYPE(type), item.toListViewString() ));
                }
            }
        }
        return returningList;
    }

    private ListViewRow.TYPE convertStringToTYPE(String typeAsString){
        if (typeAsString.equals("student")) { return ListViewRow.TYPE.STUDENT; }
        else if (typeAsString.equals("course")) { return ListViewRow.TYPE.COURSE; }
        else { return ListViewRow.TYPE.OTHER; }
    }


}
