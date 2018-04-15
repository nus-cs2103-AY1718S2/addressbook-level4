//@@author ZacZequn
package seedu.address.model;

import java.util.HashMap;

import seedu.address.model.dish.Dish;


/**
 * Unmodifiable view of customers statistics
 */
public interface ReadOnlyMenu {

    /**
     * Returns a copy of the data in Menu.
     * Modifications on this copy will not affect the original Menu data
     */
    HashMap<String, Dish> getDishes();
}
