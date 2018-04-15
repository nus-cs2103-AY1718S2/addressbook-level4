//@@author Wuhao-ooo
package seedu.address.model;

import java.util.HashMap;


/**
 * Wraps all data at the customer-stats level
 */
public class CustomerStats implements ReadOnlyCustomerStats {

    private HashMap<String, Integer> ordersCount;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        ordersCount = new HashMap<>();
    }

    public CustomerStats() {}

    public void setOrdersCount(HashMap<String, Integer> ordersCountHashMap) {
        ordersCount = ordersCountHashMap;
    }

    /**
     * Update the count of a given phone number in the customers' stats.
     * If the phone does not exist in the key set, put it to the key set with corresponding value 1
     * Otherwise add 1 to the corresponding value
     */
    public void addCount(String phone) {
        if (!ordersCount.containsKey(phone)) {
            ordersCount.put(phone, 1);
        } else {
            ordersCount.put(phone, ordersCount.get(phone) + 1);
        }
    }


    @Override
    public HashMap<String, Integer> getOrdersCount() {
        return new HashMap<>(ordersCount);
    }
}
