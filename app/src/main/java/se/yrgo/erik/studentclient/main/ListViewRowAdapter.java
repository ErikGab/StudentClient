package se.yrgo.erik.studentclient.main;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.yrgo.erik.studentclient.formatables.ItemType;

public class ListViewRowAdapter extends BaseAdapter {
  private final ArrayList<ListViewRow> list;

  public ListViewRowAdapter(List<ListViewRow> list) {
    this.list = (ArrayList<ListViewRow>)list;
  }

  @Override
  public int getCount() {
    return list.size();
  }

  @Override
  public ListViewRow getItem(int position) {
    return list.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final View result;

    if (convertView == null) {
      result = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.list_view_row_adapter_layout, parent, false);
    } else {
      result = convertView;
    }

    ListViewRow item = getItem(position);

    TextView firstRow = ((TextView) result.findViewById(android.R.id.text1));
    TextView secondRow = ((TextView) result.findViewById(android.R.id.text2));
    ImageView decoration = ((ImageView) result.findViewById(R.id.decoration));

    if (item.getRowType() == ItemType.HEADER) {
      firstRow.setText(item.getRowName());
      firstRow.setTextSize(28);
      firstRow.setTextColor(Color.DKGRAY);
      firstRow.setPadding(0,40,0,0);
      secondRow.setText(item.getRowData());
      secondRow.setTextSize(14);
      decoration.setImageResource(0);
    } else {
      firstRow.setText(item.getRowName());
      firstRow.setTextSize(14);
      firstRow.setPadding(30,0,0,0);
      secondRow.setText(item.getRowData());
      secondRow.setTextSize(20);
      secondRow.setPadding(30,0,0,0);
      setImage(item.getRowType(), decoration);
    }
    return result;
  }

  private void setImage(ItemType type, ImageView view) {
    Map<ItemType, Integer> imageMap = generateImageMap();
    if (imageMap.containsKey(type)) {
      view.setImageResource(imageMap.get(type));
    }  else {
      view.setImageResource(0);
    }
  }

  private Map<ItemType, Integer> generateImageMap() {
    Map<ItemType, Integer> imageMap = new HashMap<>();
    imageMap.put(ItemType.COURSE, R.mipmap.course_bw);
    imageMap.put(ItemType.STUDENT, R.mipmap.student_bw);
    imageMap.put(ItemType.PHONENUMBERS, R.mipmap.phonenumber_bw);
    imageMap.put(ItemType.OTHER, R.mipmap.other_bw);
    return imageMap;
  }

}