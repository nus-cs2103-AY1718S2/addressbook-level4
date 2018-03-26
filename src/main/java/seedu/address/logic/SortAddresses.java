package seedu.address.logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 */
public class SortAddresses {

    /**
     *
     * @param unsortMap
     * @return
     */
    public Map<String, Double> sortByComparator(Map<String, Double> unsortMap) {
        List<Entry<String, Double>> list = new LinkedList<Entry<String, Double>>(unsortMap.entrySet());
        boolean order = false;
        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Double>>() {
            public int compare(Entry<String, Double> val1, Entry<String, Double> val2) {
                if (order) {
                    return val1.getValue().compareTo(val2.getValue());
                } else {
                    return val2.getValue().compareTo(val1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    /**
     *
     * @param map
     */
    public void printMap(Map<String, Double> map) {
        for (Entry<String, Double> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey() + " Value : "+ entry.getValue());
        }
    }
}
