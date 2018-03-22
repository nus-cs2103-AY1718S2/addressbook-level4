package seedu.address.model;

import java.util.HashMap;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of customers statistics
 */
public interface ReadOnlyCustomerStats {

    /**
     * Returns a copy of the data in CustomerStats.
     * Modifications on this copy will not affect the original CustomerStats data
     */
    HashMap<String, Integer> getHashMap();
}
