package seedu.address.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;


/**
 * logic for the shortest delivery route
 */
public class RouteOptimization {

    /**
     *
     * @param model
     * @return
     */
    public Map<String, Double> getAddresses(Model model) {
        List<Person> lastShownList = model.getFilteredPersonList();
        List<String> filtered = new ArrayList<>();
        Map<String, Double> sortedDistances = new HashMap<>();
        int stringCutIndex;
        String addressWithoutUnit;

        //need to figure otu what the key should be to make sure we know what the hashmap is storing
        for (int i = 0; i < lastShownList.size(); i++) {
            Address address = lastShownList.get(i).getAddress();
            String name = lastShownList.get(i).getName().toString();
            String addressValue = address.value.trim();
            if (addressValue.indexOf('#') > 2) {
                stringCutIndex = addressValue.indexOf('#') - 2;
                addressWithoutUnit = addressValue.substring(0, stringCutIndex);
            } else {
                addressWithoutUnit = addressValue;
            }

            filtered.add(addressWithoutUnit);
        }
        sortedDistances = getAllDistances(filtered);
        return sortedDistances;
    }

    /**
     *
     * @param filtered
     * @return
     */
    public Map<String, Double> getAllDistances(List<String> filtered) {
        Map<String, Double> allDistances = new HashMap<>();
        Map<String, Double> sortedDistances = new HashMap<>();
        GetDistance distance = new GetDistance();
        List<Double> test = new ArrayList<>();
        List<String> route = new ArrayList<>();

        for (int i = 0; i < filtered.size(); i++) {
            String add1 = filtered.get(i);
            for (int j = 1; j < filtered.size(); j++) {
                String add2 = filtered.get(j);
                allDistances.put(labelRoutes(add1, add2), distance.getDistance(add1, add2));
            }
        }
        sortedDistances = sortDistances(allDistances);
        return sortedDistances;
    }

    /**
     *
     * @param allDistances
     * @return
     */
    public Map<String, Double> sortDistances(Map<String, Double> allDistances) {
        SortAddresses sorting = new SortAddresses();
        Map<String, Double> sortedDistances = new HashMap<>();
        sortedDistances = sorting.sortByComparator(allDistances);
        sorting.printMap(sortedDistances);
        return sortedDistances;
    }

    /**
     *
     * @param origin - starting point
     * @param destination - ending point
     * @return
     */
    public String labelRoutes(String origin, String destination) {

        String routeKey;
        routeKey = origin + "_" + destination;

        return routeKey;
    }
}

