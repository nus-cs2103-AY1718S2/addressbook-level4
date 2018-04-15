package seedu.address.logic;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//@@author meerakanani10
/**
 * Test for the SortAddresses Class
 */
public class SortAddressesTest {

    private Map<String, Double> unsortMap = new HashMap<String, Double>();
    private Map<String, Double> sortMap = new HashMap<String, Double>();

    @Before
    public void setUp() {
        unsortMap.put("27 Prince George's Park", 3.0);
        unsortMap.put("Blk 30 Geylang Street 29", 5.0);
        unsortMap.put("Blk 436 Serangoon Gardens Street 26", 6.0);
        unsortMap.put("Blk 45 Aljunied Street 85", 2.0);

        sortMap.put("Blk 45 Aljunied Street 85", 2.0);
        sortMap.put("27 Prince George's Park", 3.0);
        sortMap.put("Blk 30 Geylang Street 29", 5.0);
        sortMap.put("Blk 436 Serangoon Gardens Street 26", 6.0);
    }

    @Test
    public void execute_sorting() {
        SortAddresses sortAddresses = new SortAddresses();
        Map<String, Double> sorted = new HashMap<>();
        sorted = sortAddresses.sortByComparator(unsortMap);
        sortAddresses.printMap(sorted);

    }

    /**
     *
     * @param sorted
     */
    public void assertCorrectlySorted(Map<String, Double> sorted) {
        Assert.assertTrue(sorted.equals(sortMap));
    }

}
