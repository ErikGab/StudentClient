package se.yrgo.erik.studentclient.main;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import se.yrgo.erik.studentclient.formatables.Formatable;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalException;
import se.yrgo.erik.studentclient.dataretrieval.DataRetrievalService;
import se.yrgo.erik.studentclient.formatables.FormatableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InfoActivity extends AppCompatActivity {

  private Formatable currentItem;
  private List<FormatableType> subitemTypes;
  private static final String TAG = "InfoActivity";
  private ListView listView;
  private int listViewClickId;
  private FormatableType listViewClickType;
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

  private List<FormatableType> findSubItemTypes() {
    Log.v(TAG, "findSubItemTypes");
    return currentItem.getSubItems().stream()
            .map(i -> i.getItemType())
            .distinct()
            .collect(Collectors.toList());
  }

  private List<ListViewRow> transformFormatableForListView() {
    Log.v(TAG, "transformFormatableForListView");
    List<ListViewRow> returningList = new ArrayList<>();
    returningList.add(new ListViewRow(FormatableType.HEADER,
            itemType2Resource(currentItem.getItemType()), getString(R.string.LWR_header_row2))
    );
    for (String key : currentItem.getProperties().keySet()) {
      returningList.add(new ListViewRow(FormatableType.STUDENT, key,
              currentItem.getProperties().get(key))
      );
    }
    for (FormatableType type : subitemTypes) {
      if (type == FormatableType.PHONENUMBERS) {
        returningList.add(new ListViewRow(FormatableType.HEADER, subitemType2Resource(type),
                getString(R.string.LWR_header_row2))
        );
        Map<String, String> phoneNumbers = currentItem.getSubItems().stream()
                .filter(f -> f.getItemType().equals(type))
                .collect(Collectors.toList())
                .get(0)
                .getProperties();
        for (String phonetype : phoneNumbers.keySet()) {
          returningList.add(new ListViewRow(FormatableType.OTHER, phonetype,
                  phoneNumbers.get(phonetype))
          );
        }
      } else if (type == FormatableType.COURSE || type == FormatableType.STUDENT) {
        returningList.add(new ListViewRow(FormatableType.HEADER, subitemType2Resource(type),
                getString(R.string.LWR_header_row2))
        );
        List<Formatable> items = currentItem.getSubItems().stream()
                .filter(f -> f.getItemType().equals(type))
                .collect(Collectors.toList());
        for (Formatable item : items) {
          returningList.add(new ListViewRow(type,
                  subitemType2Resource(type).substring(0, subitemType2Resource(type).length()-2)
                  + ":", item.toListViewString(), item.getId())
          );
        }
      }
    }
    return returningList;
  }

  private void setListeners() {
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listViewClickId = ((ListViewRow) listView.getAdapter().getItem(position)).getTraceId();
        listViewClickType = ((ListViewRow) listView.getAdapter().getItem(position)).getRowType();
        Log.v(TAG, "ListView clicked id: " + listViewClickId);
        new resetActivity().execute();
      }
    });
  }

  private class resetActivity extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
      if (listViewClickType != currentItem.getItemType()) {
        try {
          if (listViewClickType == FormatableType.STUDENT) {
            Session.getInstance().clickedStudent = drs.fullInfoForStudent(listViewClickId).get(0);
            Session.getInstance().lastClick = Session.getInstance().clickedStudent;
          } else if (listViewClickType == FormatableType.COURSE) {
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

  private String itemType2Resource(FormatableType type){
    switch (type) {
      case COURSE:
        return getString(R.string.LWR_course);
      case STUDENT:
        return getString(R.string.LWR_student);
      case PHONENUMBERS:
        return getString(R.string.LWR_phone_numbers);
      default:
        return "";
    }
  }

  private String subitemType2Resource(FormatableType type){
    switch (type) {
      case COURSE:
        return getString(R.string.LWR_course_subitem);
      case STUDENT:
        return getString(R.string.LWR_student_subitem);
      case PHONENUMBERS:
        return getString(R.string.LWR_phone_numbers_subitem);
      default:
        return "";
    }
  }

}