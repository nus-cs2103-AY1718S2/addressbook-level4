//@@author ZacZequn
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Menu;
import seedu.address.model.dish.Dish;

/**
 * A utility class containing a list of {@code Dish} objects to be used in tests.
 */
public class TypicalDishes {

    public static final Dish CHICKENRICE = new DishBuilder().withName("Chicken Rice").withPrice("3").build();
    public static final Dish CURRYCHICKEN = new DishBuilder().withName("Curry Chicken").withPrice("4").build();
    public static final Dish CHICKENCHOP = new DishBuilder().withName("Chicken Chop").withPrice("5").build();
    public static final Dish BANMIAN = new DishBuilder().withName("Ban Mian").withPrice("4").build();
    public static final Dish ICEMILO = new DishBuilder().withName("Ice Milo").withPrice("2").build();
    public static final Dish COFFEE = new DishBuilder().withName("Coffee").withPrice("2").build();


    private TypicalDishes() {} // prevents instantiation

    /**
     * Returns an {@code Menu} with all the typical dishes.
     */
    public static Menu getTypicalMenu() {
        Menu menu = new Menu();
        for (Dish dish : getTypicalDishes()) {
            menu.addDish(dish);
        }
        return menu;
    }

    public static List<Dish> getTypicalDishes() {
        return new ArrayList<>(Arrays.asList(CHICKENRICE, CURRYCHICKEN, CHICKENCHOP, BANMIAN, ICEMILO, COFFEE));
    }
}
