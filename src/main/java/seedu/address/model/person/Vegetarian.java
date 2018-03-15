package seedu.address.model.person;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's preference on food  in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidVegetarian(String)}
 */
public class Vegetarian {
    public static final String MESSAGE_VEGETARIAN_CONSTRAINTS =
            "Person's preference should only be Vegeatrian or Non-vegetarian";
    public final String value;

    /**
     * Constructs a {@code Vegetarian}.
     *
     * @param vegetarian A valid vegetarian.
     */
    public Vegetarian(String vegetarian){
        if(isNull(vegetarian)){
            this.value = "Non-vegetarian";
        }else{
            checkArgument(isValidVegetarian(vegetarian),MESSAGE_VEGETARIAN_CONSTRAINTS);
            this.value = vegetarian;
        }
    }

    /**
     * Returns true if a given string is a valid halal preference.
     */
    public static boolean isValidVegetarian(String test) {
        requireNonNull(test);
        if(test.equals("Vegetarian")||(test.equals("Non-vegetarian"))){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Vegetarian // instanceof handles nulls
                && this.value.equals(((Vegetarian) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
