package seedu.ptman.model.outlet;

import static java.util.Objects.requireNonNull;
import static seedu.ptman.commons.util.AppUtil.checkArgument;

//@@author SunBangjie
/**
 * Represents an outlet's name in PTMan.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class OutletName {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Outlet name should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public OutletName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_NAME_CONSTRAINTS);
        this.fullName = name;
    }

    /**
     * Returns true if a given string is a valid outlet name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OutletName // instanceof handles nulls
                && this.fullName.equals(((OutletName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}

