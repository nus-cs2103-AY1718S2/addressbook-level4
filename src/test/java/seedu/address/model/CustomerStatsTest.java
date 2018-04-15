//@@author Wuhao-ooo
package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

public class CustomerStatsTest {

    private final CustomerStats customerStats = new CustomerStats();

    @Test
    public void constructor() {
        assertEquals(new HashMap<String, Integer>(), customerStats.getOrdersCount());
    }

}
