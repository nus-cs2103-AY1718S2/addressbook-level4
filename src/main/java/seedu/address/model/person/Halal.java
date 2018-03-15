package seedu.address.model.person;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's preference on food  in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHalal(String)}
 */
public class Halal {
    public static final String MESSAGE_HALAL_CONSTRAINTS =
            "Person's preference should only be Halal or Non-halal";
    public final String value;

    /**
     * Constructs a {@code Halal}.
     *
     * @param halal A valid halal.
     */
    public Halal(String halal){
        if(isNull(halal)){
            this.value = "Non-halal";
        }else{
            checkArgument(isValidHalal(halal),MESSAGE_HALAL_CONSTRAINTS);
            this.value = halal;
        }
    }

    /**
     * Returns true if a given string is a valid halal preference.
     */
    public static boolean isValidHalal(String test) {
        requireNonNull(test);
        if(test.equals("Halal")||(test.equals("Non-halal"))){
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
                || (other instanceof Halal // instanceof handles nulls
                && this.value.equals(((Halal) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
