package se.yrgo.erik.studentclient.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import se.yrgo.erik.studentclient.formatables.Formatable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InfoActivity extends AppCompatActivity {

  private Formatable currentItem;
  private List<String> subitemTypes;
  private static final String TAG = "InfoActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item_info);
    Log.v(TAG, "onCreate");

    currentItem = Session.getInstance().lastClick;
    subitemTypes = findSubItemTypes();

    ListView propertieslistView = (ListView) findViewById(R.id.item_info_listview);
    propertieslistView.setAdapter(new ListViewRowAdapter(transformFormatableForListView()));
  }

  private List<String> findSubItemTypes() {
    Log.v(TAG, "findSubItemTypes");
    //List<String> returningList = new ArrayList<>();
    //for(Formatable item : currentItem.getSubItems()) {
    //  if (!returningList.contains(item.getItemType())){
    //    returningList.add(item.getItemType());
    //  }
    //}
    //return returningList;
    return currentItem.getSubItems().stream()
            .map(i -> i.getItemType())
            .distinct()
            .collect(Collectors.toList());
  }

  private List<ListViewRow> transformFormatableForListView() {
    Log.v(TAG, "transformFormatableForListView");
    List<ListViewRow> returningList = new ArrayList<>();
    returningList.add(new ListViewRow(ListViewRow.TYPE.HEADER,
            currentItem.getItemType()+" Information:")
    );
    for (String key:currentItem.getProperties().keySet()){
      returningList.add(new ListViewRow(ListViewRow.TYPE.STUDENT, key,
              currentItem.getProperties().get(key))
      );
    }
    for (String type:subitemTypes) {
      if (type.equals("phonenumbers")) {
        returningList.add(new ListViewRow(ListViewRow.TYPE.HEADER, "Phonenumbers:"));
        Map<String, String> phoneNumbers = currentItem.getSubItems().stream()
                .filter(f -> f.getItemType().equals(type))
                .collect(Collectors.toList())
                .get(0)
                .getProperties();
        for (String phonetype : phoneNumbers.keySet()) {
          returningList.add(new ListViewRow(ListViewRow.TYPE.OTHER, phonetype,
                  phoneNumbers.get(phonetype))
          );
        }
      } else if (type.equals("course") || type.equals("student")) {
        returningList.add(new ListViewRow(ListViewRow.TYPE.HEADER, type + "s:"));
        List<Formatable> items = currentItem.getSubItems().stream()
                .filter(f -> f.getItemType().equals(type))
                .collect(Collectors.toList());
        for (Formatable item : items) {
          returningList.add(new ListViewRow(convertStringToTypeEnum(type), type + ":",
                  item.toListViewString())
          );
        }
      }
    }
    return returningList;
  }
  //Note To Self: Should Formatable contain Enum??
  private ListViewRow.TYPE convertStringToTypeEnum(String typeAsString) {
    Log.v(TAG, "convertStringToTypeEnum");
    Map<String,ListViewRow.TYPE> types = new HashMap<String, ListViewRow.TYPE>() {{
            put("student", ListViewRow.TYPE.STUDENT);
            put("course", ListViewRow.TYPE.COURSE);
    }};
    if (types.containsKey(typeAsString)){
      return types.get(typeAsString);
    } else {
      return ListViewRow.TYPE.OTHER;
    }
  }
}
