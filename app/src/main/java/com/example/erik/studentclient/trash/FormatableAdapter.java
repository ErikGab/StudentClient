package com.example.erik.studentclient.trash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.erik.studentclient.Formatable;
import com.example.erik.studentclient.R;

import java.util.ArrayList;
import java.util.List;

public class FormatableAdapter extends BaseAdapter {
    private final ArrayList mData;

    public FormatableAdapter(List<Formatable> list) {
        mData = (ArrayList)list;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Formatable getItem(int position) {
        return (Formatable)mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO implement you own logic with ID
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.formatable_adapter_layout, parent, false);
        } else {
            result = convertView;
        }

        Formatable formatableitem = getItem(position);

        // TODO replace findViewById by ViewHolder
        ((TextView) result.findViewById(android.R.id.text1)).setText(formatableitem.toListViewString());

        ((ImageView) result.findViewById(R.id.formatable_list_icon)).setImageResource(android.R.drawable.presence_online);

        return result;
    }
}