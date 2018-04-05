# meerakanani10
###### \java\seedu\address\logic\commands\FilterCommand.java
``` java
/**
 * Filters and lists all persons in address book whose date contains any of the argument dates.
 * Keyword matching is case sensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters the list based on the specifed field and given value.";

    private static String stringDuration;

    private final DatePredicate predicate;

    public FilterCommand(DatePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException, IOException {

        RouteOptimization route = new RouteOptimization();
        List<String> optimizedRoute;

        model.updateFilteredPersonList(predicate);
        optimizedRoute = route.getAddresses(model);
        stringDuration = getDuration(optimizedRoute);

```
###### \java\seedu\address\logic\commands\FilterCommand.java
``` java
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }

    public static String getDuration(List<String> route) {
        Double duration;
        GetDistance distance = new GetDistance();
        Double totalDuration = 0.0;
        for (int  i = 0; i < route.size() - 1; i++) {
            duration = distance.getTime(route.get(i), route.get(i + 1));
            totalDuration = totalDuration + duration;
        }
        String stringDuration = totalDuration.toString() + " mins";
        return stringDuration;
    }

    public static String getStringDuration() {
        return stringDuration;
    }
}
```
###### \java\seedu\address\logic\GetDistance.java
``` java
        int space = distance.indexOf(" ");
        String units = distance.substring(space + 1, distance.length());
        double metres;
        distanceWithoutUnit = distance.substring(0, space);
        if (units.equals("m")) {
            metres = Double.parseDouble(distanceWithoutUnit) / 1000.0;
            return metres;
        } else {
            return Double.parseDouble(distanceWithoutUnit);
        }
    }


    public double getTime(String origin, String destination) {
        String durationWithoutUnit = "";
        DistanceMatrix matrix = null;
        matrix = getMatrix(origin, destination);
        String duration = matrix.rows[0].elements[0].duration.toString();
        int space = duration.indexOf(" ");
        String units = duration.substring(space + 1, duration.length());
        durationWithoutUnit = duration.substring(0, space);
        return Double.parseDouble(durationWithoutUnit);
    }
}
```
###### \java\seedu\address\logic\parser\FilterCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FilterCommand(new DatePredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### \java\seedu\address\logic\RouteOptimization.java
``` java
/**
 * logic for the shortest delivery route
 */
public class RouteOptimization {
    public static final String HQ_ADDRESS = "Kent Ridge MRT";

    /**
     *
     * @param model
     * @return
     */
    public List<String> getAddresses(Model model) {
        List<Person> lastShownList = model.getFilteredPersonList();
        List<String> filteredAddresses = new ArrayList<>();
        List<String> optimizedRoute = new ArrayList<>();
        String addressWithoutUnit;
        String startingPoint;

        if (lastShownList.size() == 1) {
            Address address = lastShownList.get(0).getAddress();
            if (isFindableAddress(address)) {
                String name = lastShownList.get(0).getName().toString();
                addressWithoutUnit = removeUnit(address);
                optimizedRoute.add(addressWithoutUnit);
            }
        } else {
            //need to figure out what the key should be to make sure we know what the hashmap is storing
            for (int i = 0; i < lastShownList.size(); i++) {
                Address address = lastShownList.get(i).getAddress();
                if (isFindableAddress(address)) {
                    addressWithoutUnit = removeUnit(address);
                    filteredAddresses.add(addressWithoutUnit);
                }
            }
            if (!filteredAddresses.isEmpty()) {
                optimizedRoute = getStartingAddress(filteredAddresses, optimizedRoute);
                filteredAddresses = removeAddress(optimizedRoute.get(0), filteredAddresses);
                if (!filteredAddresses.isEmpty()) {
                    optimizedRoute = getDistances(filteredAddresses, optimizedRoute.get(0), optimizedRoute);
                }
            }
        }
        return optimizedRoute;
    }

```
###### \java\seedu\address\logic\RouteOptimization.java
``` java
    public List<String> getStartingAddress(List<String> filteredAddresses, List<String> optimizedRoute) {
        Map<String, Double> startingRoute = new LinkedHashMap<>();
        GetDistance distance = new GetDistance();
        SortAddresses sort = new SortAddresses();
        Map<String, Double> dummy = new LinkedHashMap<>();
        String first;


        for (int i = 0; i < filteredAddresses.size(); i++) {
            String destination = filteredAddresses.get(i);
            String origin = HQ_ADDRESS;
            startingRoute.put(labelRoutes(origin, destination), distance.getDistance(origin, destination));
        }
        dummy = sort.cleanSorted(sort.sortByComparator(startingRoute));
        sort.printMap(dummy);
        Map.Entry<String, Double> entry = dummy.entrySet().iterator().next();
        first = entry.getKey().split("_")[1];
        optimizedRoute.add(first);
        return optimizedRoute;

    }

    /**
     *
     * @param address
     * @param filteredAddresses
     * @return
     */
    public List<String> removeAddress(String address, List<String> filteredAddresses) {
        for (int i = 0; i < filteredAddresses.size(); i++) {
            if (filteredAddresses.get(i).equals(address)) {
                filteredAddresses.remove(i);
                break;
            }
        }
        return filteredAddresses;
    }

    public List<String> getDistances(List<String> filteredAddresses, String origin, List<String> optimizedRoute) {
        Map<String, Double> paths = new LinkedHashMap<>();
        Map<String, Double> dummy = new HashMap<>();
        SortAddresses sort = new SortAddresses();
        String next;
        GetDistance distance = new GetDistance();
        for (int i = 0; i < filteredAddresses.size(); i++) {
            String destination = filteredAddresses.get(i);
            paths.put(labelRoutes(origin, destination), distance.getDistance(origin, destination));
        }
        dummy = sort.cleanSorted(sort.sortByComparator(paths));
        Map.Entry<String, Double> entry = dummy.entrySet().iterator().next();
        next = entry.getKey().split("_")[1];
        optimizedRoute.add(next);
        filteredAddresses = removeAddress(next, filteredAddresses);
        if (filteredAddresses.size() != 0) {
            optimizedRoute = getDistances(filteredAddresses, next, optimizedRoute);
        }
        return optimizedRoute;
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

    /**
     *
     * @param combinedAddresses - the key from the hashmaps
     * @return the addresses split, in an array.
     */
    public String[] splitLabel(String combinedAddresses) {
        String[] addresses = combinedAddresses.split("_");
        return addresses;
    }

    /**
     *
     * @param address address to be edited
     * @return
     */
    public String removeUnit(Address address) {
        String addressValue = address.value.trim();
        int stringCutIndex;
        String addressWithoutUnit;
        if (addressValue.indexOf('#') > 2) {
            stringCutIndex = addressValue.indexOf('#') - 2;
            addressWithoutUnit = addressValue.substring(0, stringCutIndex);
        } else {
            addressWithoutUnit = addressValue;
        }
        return addressWithoutUnit;
    }
}

```
###### \java\seedu\address\logic\SortAddresses.java
``` java
/**
 * Sorts a hashmap of addresses and distances based on the distances
 */
public class SortAddresses {

    /**
     *
     * @param unsortMap
     * @return
     */
    public Map<String, Double> sortByComparator(Map<String, Double> unsortMap) {
        List<Entry<String, Double>> list = new LinkedList<Entry<String, Double>>(unsortMap.entrySet());
        boolean order = true;
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
        Map<String, Double> cleanSortedMap = new LinkedHashMap<String, Double>();
        for (Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        cleanSortedMap = cleanSorted(sortedMap);
        return cleanSortedMap;
    }

    /**
     *
     * @param map
     */
    public void printMap(Map<String, Double> map) {
        for (Entry<String, Double> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
        }
    }

    /**
     *
     */
    public Map<String, Double> cleanSorted(Map<String, Double> sortedMap) {
        for (Entry<String, Double> entry : sortedMap.entrySet()) {
            String pairAddresses = entry.getKey();
            String[] addresses = pairAddresses.split("_");

            if (addresses.length > 1 && addresses[0].equals(addresses[1])) {
                sortedMap.remove(entry.getKey());
            }
        }
        for (Entry<String, Double> entry : sortedMap.entrySet()) {



            /*TODO: WANT TO PUT SOME LOGIC HERE SO THAT THE DISTANCES ARE RANKED IN ORDER OF SHORTEST DISTANCE BUT ALSO
             * THE ADDRESSES MAKE SENSE SO IF SHORTEST IS A - B THE NEXT SHOULD BE THE SHORTEST DISTANCE FROM B - X
             * WHERE X IS UNKNOWN
             */

        }
        return sortedMap;
    }
}
```
