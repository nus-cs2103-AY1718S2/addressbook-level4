//@@author ZacZequn
package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;

import seedu.address.model.dish.Dish;




/**
 * Wraps all data at the menu level
 */
public class Menu implements ReadOnlyMenu {

    private HashMap<String, Dish> dishes;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        dishes = new HashMap<>();
    }

    public Menu() {}

    /**
     * Creates an Menu using the Dishes {@code toBeCopied}
     */
    public Menu(ReadOnlyMenu toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public void setMenu(HashMap<String, Dish> theDishes) {

        dishes = theDishes;
    }

    public void setMenu(Menu theMenu) {
        dishes = theMenu.getDishes();
    }

    /**
     * Resets the existing data of this {@code Menu} with {@code newData}.
     */
    public void resetData(ReadOnlyMenu newData) {
        requireNonNull(newData);
        setMenu(newData.getDishes());
    }

    /**
     * Returns the dish in Menu if available.
     * Otherwise return null.
     */
    public Dish get(String dish) {
        return dishes.get(dish);
    }

    /**
     * Adds a dish to the menu.
     */
    public void addDish(Dish dish) {
        dishes.put(dish.getName().toString(), dish);
    }


    /**
     * Returns a copy of the data in Menu.
     * Modifications on this copy will not affect the original Menu data
     */
    @Override
    public HashMap<String, Dish> getDishes() {
        return new HashMap<>(dishes);
    }

    @Override
    public String toString() {
        String menu = "";
        for (String name : dishes.keySet()) {
            menu += (name + " ");
        }
        menu = menu.trim();
        return menu;
    }
}
