package se.yrgo.erik.studentclient.formatables;

import java.util.Map;
import java.util.List;

public interface Formatable {
    int getId();
    ItemType getItemType();
    Map<String,String> getProperties();
    List<Formatable> getSubItems();
    String toListViewString();
    String toListViewStringHeader();
    String getName();
}