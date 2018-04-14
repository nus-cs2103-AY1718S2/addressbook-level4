package seedu.address.model.activity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author YuanQLLer
/**
 * This class is to store location info in an event
 */
public class Location {
    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Event location should not be blank";

    public static final String LOCATION_VALIDATION_REGEX = ".+";

    public final String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param location A valid location.
     */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_LOCATION_CONSTRAINTS);
        this.value = location;
    }

    /**
     * Returns true if a given string is a valid location.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && this.value.equals(((Location) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
