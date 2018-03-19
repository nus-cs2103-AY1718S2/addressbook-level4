package seedu.recipe.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's URL in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUrl(String)}
 */
public class Url {


    public static final String MESSAGE_URL_CONSTRAINTS = "URL should be valid";
    public final String value;

    /**
     * Constructs a {@code Url}.
     *
     * @param url A valid Url.
     */
    public Url(String url) {
        requireNonNull(url);
        checkArgument(isValidUrl(url), MESSAGE_URL_CONSTRAINTS);
        this.value = url;
    }

    /**
     * TO BE IMPLEMENTED
     */
    public static boolean isValidUrl(String test) {
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Url // instanceof handles nulls
                && this.value.equals(((Url) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
