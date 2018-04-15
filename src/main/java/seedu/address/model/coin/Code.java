package seedu.address.model.coin;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.core.CoinSubredditList;

/**
 * Represents a Coin's currency code in the book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Code {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Coin names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alpha}][\\p{Alpha} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Code(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_NAME_CONSTRAINTS);
        this.fullName = name;
    }

    /**
     * Returns true if a given string is a valid coin name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    public static boolean isRecognizedName(Coin test) {
        return CoinSubredditList.isRecognized(test);
    }

    public boolean contains(String substring) {
        return fullName.contains(substring);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Code // instanceof handles nulls
                && this.fullName.equals(((Code) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
}
