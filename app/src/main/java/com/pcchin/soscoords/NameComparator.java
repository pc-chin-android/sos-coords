package com.pcchin.soscoords;

import java.util.ArrayList;
import java.util.Comparator;

public class NameComparator implements Comparator<ArrayList<String>> {
    public int compare(ArrayList<String> a, ArrayList<String> b) { return a.get(1).compareTo(b.get(1)); }
}
