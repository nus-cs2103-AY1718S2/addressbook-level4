//@@author ZacZequn
package seedu.address.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.address.model.dish.Dish;
import seedu.address.model.dish.Name;
import seedu.address.model.dish.Price;


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
        //only used for testing
        dishes.put("Chicken Rice",new Dish(new Name("Chicken Rice"), new Price("3")));
    }

    public Menu() {}

    /**
     * Only used for testing purpose
     * @param dish
     */
    public Menu(String dish){
        dishes.put(dish, new Dish(new Name(dish)));
    }

    public void setMenu(HashMap<String, Dish> theDishes) {
        dishes = theDishes;
    }

    public void setMenu(Menu theMenu) {
        dishes = theMenu.getDishes();
    }
    /**
     * Returns the dish in Menu if available.
     * Otherwise return null.
     */
    public Dish get(String dish) { return dishes.get(dish); }

    /**
     * Returns a copy of the data in Menu.
     * Modifications on this copy will not affect the original Menu data
     */
    @Override
    public HashMap<String, Dish> getDishes() { return new HashMap<>(dishes); }

    @Override
    public String toString() {
        String menu = "";
        for(String name : dishes.keySet()){
            menu += (name + " ");
        }
        menu = menu.trim();
        return menu;
    }
}
