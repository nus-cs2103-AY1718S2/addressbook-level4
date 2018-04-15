//@@author ZacZequn
package seedu.address.model.util;

import seedu.address.model.Menu;
import seedu.address.model.ReadOnlyMenu;
import seedu.address.model.dish.Dish;
import seedu.address.model.dish.Name;
import seedu.address.model.dish.Price;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleMenuDataUtil {
    public static Dish[] getSampleDishes() {
        return new Dish[] {
            new Dish(new Name("Chicken Rice"), new Price("3")),
            new Dish(new Name("Curry Chicken"), new Price("4")),
            new Dish(new Name("Chicken Chop"), new Price("5")),
            new Dish(new Name("Ban Mian"), new Price("4")),
            new Dish(new Name("Ice Milo"), new Price("2")),
            new Dish(new Name("Coffee"), new Price("2"))
        };
    }

    public static ReadOnlyMenu getSampleMenu() {
        Menu sampleMenu = new Menu();
        for (Dish sampleDish : getSampleDishes()) {
            sampleMenu.addDish(sampleDish);
        }
        return sampleMenu;
    }

}
