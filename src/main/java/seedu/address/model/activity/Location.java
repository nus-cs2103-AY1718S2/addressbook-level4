package seedu.address.model.activity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Location {
    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Activity names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_LOCATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param location A valid location.
     */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidName(location), MESSAGE_LOCATION_CONSTRAINTS);
        this.fullName = location;
    }

    /**
     * Returns true if a given string is a valid activity name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_LOCATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
