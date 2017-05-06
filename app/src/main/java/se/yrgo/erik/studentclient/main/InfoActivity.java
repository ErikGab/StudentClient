package se.yrgo.erik.studentclient.main;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
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
import se.yrgo.erik.studentclient.formatables.ItemType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InfoActivity extends AppCompatActivity {

  private Formatable currentItem;
  private List<ItemType> subitemTypes;
  private static final String TAG = "InfoActivity";
  private ListView listView;
  private int listViewClickId;
  private ItemType listViewClickType;
  private DataRetrievalService drs;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item_info);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    Log.v(TAG, "onCreate");
    drs = DataRetrievalService.getInstance();
    listView = (ListView) findViewById(R.id.item_info_listview);
    init();
    setListeners();
  }

  private void init() {
    currentItem = Session.getInstance().lastClick;
    subitemTypes = findSubItemTypes();
    listView.setAdapter(new ListViewRowAdapter(formatable2ListViewRows()));
  }

  private List<ItemType> findSubItemTypes() {
    Log.v(TAG, "findSubItemTypes");
    return currentItem.getSubItems().stream()
            .map(i -> i.getItemType())
            .distinct()
            .collect(Collectors.toList());
  }

  //Converts a formatable to a list of ListViewRows
  private List<ListViewRow> formatable2ListViewRows() {
    Log.v(TAG, "formatable2ListViewRows");
    List<ListViewRow> returningList = new ArrayList<>();
    returningList.add(new ListViewRow(ItemType.HEADER,
            itemType2Resource(currentItem.getItemType()), getString(R.string.LWR_header_row2)));
    for (String key : currentItem.getProperties().keySet()) {
      returningList.add(new ListViewRow(ItemType.OTHER, translate(key),
              currentItem.getProperties().get(key)));
    }
    for (ItemType type : subitemTypes) {
      if (type == ItemType.PHONENUMBERS) {
        returningList.add(new ListViewRow(ItemType.HEADER, subitemType2Resource(type),
                getString(R.string.LWR_header_row2))
        );
        Map<String, String> phoneNumbers = currentItem.getSubItems().stream()
                .filter(f -> f.getItemType().equals(type))
                .collect(Collectors.toList())
                .get(0)
                .getProperties();
        for (String phonetype : phoneNumbers.keySet()) {
          returningList.add(new ListViewRow(ItemType.PHONENUMBERS, translate(phonetype),
                  phoneNumbers.get(phonetype))
          );
        }
      } else if (type == ItemType.COURSE || type == ItemType.STUDENT) {
        returningList.add(new ListViewRow(ItemType.HEADER, subitemType2Resource(type),
                getString(R.string.LWR_header_row2))
        );
        List<Formatable> items = currentItem.getSubItems().stream()
                .filter(f -> f.getItemType().equals(type))
                .collect(Collectors.toList());
        for (Formatable item : items) {
          returningList.add(new ListViewRow(type, translate(item.toListViewStringHeader()),
                  item.toListViewString(), item.getId()));
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
          if (listViewClickType == ItemType.STUDENT) {
            Session.getInstance().clickedStudent = drs.fullInfoForStudent(listViewClickId).get(0);
            Session.getInstance().lastClick = Session.getInstance().clickedStudent;
          } else if (listViewClickType == ItemType.COURSE) {
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

  private String itemType2Resource(ItemType type) {
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

  private String subitemType2Resource(ItemType type) {
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

  private String translate(String str) {
    Map<String, String> translations = buildStringMap();
    String key = str.toLowerCase().trim();
    if (translations.containsKey(key)) {
      return translations.get(key);
    } else {
      return str; //USE GOOGLE TRANSLATE HERE?
    }
  }

  //Automatic Dynamic but does not work
/*private Map<String, String> buildStringMap(){
    Map<String, String> translationMap = new HashMap<>();
    List<Field> fields = Arrays.asList(R.string.class.getFields());
    fields.stream()
            .filter(f -> f.getName().substring(0,8).equals("dbColumn"))
            .forEach(f -> translationMap.put(f.getName(), f.get()));
    return translationMap;
  }*/

  //This is BAD.
  private Map<String,String> buildStringMap(){
    Map<String,String> translations = new HashMap<>();
    try {
      translations.put("id", getString(R.string.dbColumn_id));
      translations.put("name", getString(R.string.dbColumn_name));
      translations.put("surname", getString(R.string.dbColumn_surname));
      translations.put("age", getString(R.string.dbColumn_age));
      translations.put("complete", getString(R.string.dbColumn_complete));
      translations.put("description", getString(R.string.dbColumn_description));
      translations.put("aborted", getString(R.string.dbColumn_aborted));
      translations.put("enddate", getString(R.string.dbColumn_endDate));
      translations.put("fax", getString(R.string.dbColumn_fax));
      translations.put("grade", getString(R.string.dbColumn_grade));
      translations.put("home", getString(R.string.dbColumn_home));
      translations.put("mobile", getString(R.string.dbColumn_mobile));
      translations.put("ongoing", getString(R.string.dbColumn_ongoing));
      translations.put("phonenumbers", getString(R.string.dbColumn_phonenumbers));
      translations.put("points", getString(R.string.dbColumn_points));
      translations.put("postaddress", getString(R.string.dbColumn_postAddress));
      translations.put("startdate", getString(R.string.dbColumn_startDate));
      translations.put("status", getString(R.string.dbColumn_status));
      translations.put("streetaddress", getString(R.string.dbColumn_streetAddress));
      translations.put("work", getString(R.string.dbColumn_work));
    } catch (Resources.NotFoundException nfe) {
      Log.v("TRANSLATORMAP: ", "RESOURCES NOT FOUND");
      return new HashMap<String,String>();
    }
    return translations;
  }

}