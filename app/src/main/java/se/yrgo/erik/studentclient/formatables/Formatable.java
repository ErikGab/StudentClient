package se.yrgo.erik.studentclient.formatables;

import java.util.Map;
import java.util.List;

public interface Formatable {
    public int getId();
    public FormatableType getItemType();
    public Map<String,String> getProperties();
    public List<Formatable> getSubItems();
    public String toListViewString();
    public String toListViewStringHeader();
    public String getName();
}