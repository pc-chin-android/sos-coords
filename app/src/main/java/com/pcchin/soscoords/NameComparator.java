package com.pcchin.soscoords;

import java.util.Comparator;
import java.util.List;

public class NameComparator implements Comparator<List<String>> {
    public int compare(List<String> a, List<String> b) { return a.get(1).compareTo(b.get(1)); }
}
