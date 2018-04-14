//@@author ZacZequn
package seedu.address.testutil;

import seedu.address.model.dish.Dish;
import seedu.address.model.dish.Name;
import seedu.address.model.dish.Price;

/**
 * A utility class to help with building Dish objects.
 */
public class DishBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PRICE = "3";

    private Name name;
    private Price price;

    public DishBuilder() {
        name = new Name(DEFAULT_NAME);
        price = new Price(DEFAULT_PRICE);
    }

    /**
     * Initializes the DishBuilder with the data of {@code personToCopy}.
     */
    public DishBuilder(Dish dishToCopy) {
        name = dishToCopy.getName();
        price = dishToCopy.getPrice();
    }

    /**
     * Sets the {@code Name} of the {@code Dish} that we are building.
     */
    public DishBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Dish} that we are building.
     */
    public DishBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }


    public Dish build() {
        return new Dish(name, price);
    }

}
