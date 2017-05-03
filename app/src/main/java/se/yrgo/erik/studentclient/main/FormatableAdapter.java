package se.yrgo.erik.studentclient.main;

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

import se.yrgo.erik.studentclient.formatables.Formatable;
import se.yrgo.erik.studentclient.formatables.FormatableType;

public class FormatableAdapter extends BaseAdapter {
  private final ArrayList<Formatable> list;

  public FormatableAdapter(List<Formatable> list) {
    this.list = (ArrayList<Formatable>)list;
  }

  @Override
  public int getCount() {
    return list.size();
  }

  @Override
  public Formatable getItem(int position) {
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
              .inflate(R.layout.formatable_listview_layout, parent, false);
    } else {
      result = convertView;
    }
    Formatable item = getItem(position);
    TextView text = ((TextView) result.findViewById(R.id.fll_text));
    ImageView icon = ((ImageView) result.findViewById(R.id.fll_icon));
    text.setText(item.toListViewString());
    setImage(item.getItemType(), icon);
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
    return imageMap;
  }

}