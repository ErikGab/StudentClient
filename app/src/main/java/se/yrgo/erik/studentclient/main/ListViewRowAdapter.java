package se.yrgo.erik.studentclient.main;

import android.graphics.Color;
import android.media.Image;
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

import se.yrgo.erik.studentclient.formatables.FormatableType;

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

    if (item.getRowType() == FormatableType.HEADER) {
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

  private void setImage(FormatableType type, ImageView view) {
    Map<FormatableType, Integer> imageMap = generateImageMap();
    if (imageMap.containsKey(type)) {
      view.setImageResource(imageMap.get(type));
    }  else {
      view.setImageResource(0);
    }
  }

  private Map<FormatableType, Integer> generateImageMap() {
    Map<FormatableType, Integer> imageMap = new HashMap<>();
    imageMap.put(FormatableType.COURSE, R.mipmap.course);
    imageMap.put(FormatableType.STUDENT, R.mipmap.student);
    imageMap.put(FormatableType.PHONENUMBERS, R.mipmap.phonenumber);
    imageMap.put(FormatableType.OTHER, R.mipmap.other);
    return imageMap;
  }

}