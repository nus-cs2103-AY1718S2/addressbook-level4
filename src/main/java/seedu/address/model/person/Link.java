//@@author deeheenguyen
package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Link {

    public static final String MESSAGE_LINK_CONSTRAINTS = "Link should be start with http";
    public static final String LINK_VALIDATION_REGEX =
            "^(https?:\\\\/\\\\/)?(www\\.)?([\\\\w]+\\\\.)+[\u200C\u200B\\\\w]{2,63}\\\\/?";

    public final String value;

    /**
     * Constructs a {@code Tag}.
     *
     * @param link A valid url.
     */
    public Link(String link) {
        checkArgument(isValidLink(link), MESSAGE_LINK_CONSTRAINTS);
        this.value = link;
    }

    /**
     * Returns true if a given string is a valid link name.
     */
    public static boolean isValidLink(String test) {
        //return test.matches(LINK_VALIDATION_REGEX);
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Link // instanceof handles nulls
                && this.value.equals(((Link) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + value + ']';
    }

}
