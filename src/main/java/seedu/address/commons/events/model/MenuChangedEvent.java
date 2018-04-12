//@@author ZacZequn
package seedu.address.commons.events.model;

import java.util.HashMap;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyMenu;
import seedu.address.model.dish.Dish;

/** Indicates the Menu in the model has changed*/
public class MenuChangedEvent extends BaseEvent {

    public final ReadOnlyMenu data;

    public MenuChangedEvent(ReadOnlyMenu data) {
        this.data = data;
    }

    @Override
    public String toString() {
        HashMap<String, Dish> theMenu = data.getDishes();
        return theMenu.toString();
    }
}
