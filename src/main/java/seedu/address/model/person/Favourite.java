package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represent Person's favourite attribute i.e. whether the Person is in Favourites
 */
public class Favourite {
    private static final String MESSAGE_ADDRESS_CONSTRAINTS = "Favourite should be 'true' or 'false' only";

    public final String value;

    public Favourite(boolean isFav) {
        this.value = Boolean.toString(isFav);
    }

    public Favourite(String isFav) {
        requireNonNull(isFav);
        checkArgument(isValidBoolean(isFav), MESSAGE_ADDRESS_CONSTRAINTS);
        this.value = isFav;
    }

    /**
     * Returns true if a given string is a valid boolean string.
     */
    private static boolean isValidBoolean(String test) {
        return test.equals("true") || test.equals("false");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
            || obj instanceof Favourite
            && value.equals(((Favourite) obj).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
