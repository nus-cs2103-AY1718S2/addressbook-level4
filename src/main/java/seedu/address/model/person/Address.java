package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.ParserUtil.UNSPECIFIED_FIELD;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Person addresses can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ADDRESS_VALIDATION_REGEX = "[^\\s].*";

    public final String value;
    private boolean isPrivate;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_ADDRESS_CONSTRAINTS);
        this.value = address;
        this.isPrivate = false;
    }

    public Address(String address, boolean isPrivate) {
        this(address);
        this.setPrivate(isPrivate);
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX) || test.equals(UNSPECIFIED_FIELD);
    }

    @Override
    public String toString() {
        if (isPrivate) {
            return "<Private Address>";
        }
        return value;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void togglePrivacy() {
        this.isPrivate = isPrivate ? false : true;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && this.value.equals(((Address) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
