//@@author RyanAngJY
package seedu.recipe.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

import java.net.URL;

/**
 * Represents a Recipe's URL in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUrl(String)}
 */
public class Url {

    public static final String NULL_URL_REFERENCE = "-";
    public static final String MESSAGE_URL_CONSTRAINTS = "URL should start with a http:// or https://";
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

    //@@author RyanAngJY-reused
    //Reused from https://stackoverflow.com/questions/2230676/how-to-check-for-a-valid-url-in-java with exception handling
    /**
     *  Returns true if a given string is a valid web url, or no url has been assigned
     */
    public static boolean isValidUrl(String testUrl) {
        if (testUrl.equals(NULL_URL_REFERENCE)) {
            return true;
        }
        try {
            URL url = new URL(testUrl);
            url.toURI();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    //@@author RyanAngJY

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
//@@author
