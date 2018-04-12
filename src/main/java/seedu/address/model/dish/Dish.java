//@@author ZacZequn
package seedu.address.model.dish;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;



/**
 * Represents a Dish in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Dish {

    private final Name name;
    private final Price price;


    /**
     * If we know the name and price.
     */
    public Dish(Name name, Price price) {
        requireAllNonNull(name, price);
        this.name = name;
        this.price = price;
    }

    /**
     * If we only know the name. Only used for testing purpose
     */
    public Dish(Name name) {
        requireAllNonNull(name);
        this.name = name;
        this.price = new Price("3");
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Dish)) {
            return false;
        }

        Dish otherDish = (Dish) other;
        return otherDish.getName().equals(this.getName())
                && otherDish.getPrice().equals(this.getPrice());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Price: ")
                .append(getPrice());
        return builder.toString();
    }

}
