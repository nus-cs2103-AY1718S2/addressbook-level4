# ZacZequn
###### \java\seedu\address\model\Menu.java
``` java
package seedu.address.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.address.model.dish.Dish;
import seedu.address.model.dish.Name;


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
        try {
            ObjectMapper mapper = new ObjectMapper();
            Dish dish = mapper.readValue(new File("data/menu.xml"), Dish.class);
            Name name = dish.getName();
            dishes.put(name.toString(), dish);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Menu() {}

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
}
```
###### \java\seedu\address\model\ReadOnlyMenu.java
``` java
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
```
