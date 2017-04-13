package se.yrgo.erik.studentclient.main;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import se.yrgo.erik.studentclient.formatables.Formatable;
import se.yrgo.erik.studentclient.storage.DataRetrievalException;
import se.yrgo.erik.studentclient.storage.DataRetrievalService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InfoActivity extends AppCompatActivity {

  private Formatable currentItem;
  private List<String> subitemTypes;
  private static final String TAG = "InfoActivity";
  private ListView listView;
  private int listViewClickId;
  private String listViewClickType;
  private DataRetrievalService drs;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item_info);
    Log.v(TAG, "onCreate");
    drs = DataRetrievalService.getInstance();
    listView = (ListView) findViewById(R.id.item_info_listview);
    init();
    setListeners();
  }

  private void init() {
    currentItem = Session.getInstance().lastClick;
    subitemTypes = findSubItemTypes();
    listView.setAdapter(new ListViewRowAdapter(transformFormatableForListView()));
  }

  private List<String> findSubItemTypes() {
    Log.v(TAG, "findSubItemTypes");
    return currentItem.getSubItems().stream()
            .map(i -> i.getItemType())
            .distinct()
            .collect(Collectors.toList());
  }

  private List<ListViewRow> transformFormatableForListView() {
    Log.v(TAG, "transformFormatableForListView");
    List<ListViewRow> returningList = new ArrayList<>();
    returningList.add(new ListViewRow(ListViewRow.TYPE.HEADER,
            currentItem.getItemType() + " Information:")
    );
    for (String key : currentItem.getProperties().keySet()) {
      returningList.add(new ListViewRow(ListViewRow.TYPE.STUDENT, key,
              currentItem.getProperties().get(key))
      );
    }
    for (String type : subitemTypes) {
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
                  item.toListViewString(), item.getId())
          );
        }
      }
    }
    return returningList;
  }

  //Note To Self: Should Formatable contain Enum??
  private ListViewRow.TYPE convertStringToTypeEnum(String typeAsString) {
    Log.v(TAG, "convertStringToTypeEnum");
    Map<String, ListViewRow.TYPE> types = new HashMap<String, ListViewRow.TYPE>() {{
      put("student", ListViewRow.TYPE.STUDENT);
      put("course", ListViewRow.TYPE.COURSE);
    }};
    if (types.containsKey(typeAsString)) {
      return types.get(typeAsString);
    } else {
      return ListViewRow.TYPE.OTHER;
    }
  }

  private void setListeners() {
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listViewClickId = ((ListViewRow) listView.getAdapter().getItem(position)).getTraceId();
        listViewClickType = ((ListViewRow) listView.getAdapter().getItem(position)).getRowType().toString(); //SHIT THIS IS NOT GOOD USE STRING OR ENUM NOT MIX
        Log.v(TAG, "ListView clicked id: " + listViewClickId);
        new resetActivity().execute();
      }
    });
  }

  private class resetActivity extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
      if (!listViewClickType.toLowerCase().equals(currentItem.getItemType())) {
        try {
          if (listViewClickType.toLowerCase().equals("student")) { //SHIT THIS IS BAD !!
            Session.getInstance().clickedStudent = drs.fullInfoForStudent(listViewClickId).get(0);
            Session.getInstance().lastClick = Session.getInstance().clickedStudent;
          } else if (listViewClickType.toLowerCase().equals("course")) {
            Session.getInstance().clickedCourse = drs.fullInfoForCourse(listViewClickId).get(0);
            Session.getInstance().lastClick = Session.getInstance().clickedCourse;
          }
        } catch (DataRetrievalException dre) {
          Log.v(TAG, "resetActivity: Failed to retrieve item!\n" + dre.getMessage());
          this.cancel(true);
        } catch (IndexOutOfBoundsException ioobe) {
          Log.v(TAG, "resetActivity: Failed to retrieve item!\n");
          this.cancel(true);
        }
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      super.onPostExecute(result);
      init();
    }

  }

}