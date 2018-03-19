package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the filepath of a Person's displayPic in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPath(String)}
 */
public class DisplayPic {

    public static final String MESSAGE_DISPLAY_PIC_CONSTRAINTS =
            "The filepath should lead to a file that exists and is an image.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ADDRESS_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code DisplayPic}.
     *
     * @param filePath A valid string containing the path to the file.
     */
    public DisplayPic(String filePath) {
        requireNonNull(filePath);
        checkArgument(isValidPath(filePath), MESSAGE_DISPLAY_PIC_CONSTRAINTS);
        this.value = filePath;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidPath(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayPic // instanceof handles nulls
                && this.value.equals(((DisplayPic) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
