package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashSet;
import java.util.Set;

//@@author shookshire
/**
 * Represents a Person's available Location in the TuitionCor.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {

    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Location should only be north, south, east, west and central in decreasing order of preference without "
                    + "any repetitions";

    private static final String LOCATION_VALIDATION_REGEX_NORTH = "(?i)North";
    private static final String LOCATION_VALIDATION_REGEX_SOUTH = "(?i)South";
    private static final String LOCATION_VALIDATION_REGEX_EAST = "(?i)East";
    private static final String LOCATION_VALIDATION_REGEX_WEST = "(?i)West";
    private static final String LOCATION_VALIDATION_REGEX_CENTRAL = "(?i)Central";

    public final String value;

    /**
     * Constructs a {@code Location}.
     *
     * @param location A valid location.
     */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_LOCATION_CONSTRAINTS);
        this.value = location.trim().replaceAll(" +", " ");
    }

    /**
     * Returns true if a given string is a valid client Location.
     */
    public static boolean isValidLocationRegex(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX_NORTH) || test.matches(LOCATION_VALIDATION_REGEX_EAST)
                || test.matches(LOCATION_VALIDATION_REGEX_SOUTH)
                || test.matches(LOCATION_VALIDATION_REGEX_WEST)
                || test.matches(LOCATION_VALIDATION_REGEX_CENTRAL);
    }

    /**
     * Returns true if all of the given string is a valid client Location.
     */
    public static boolean isValidLocation(String test) {
        if (test.matches("\\s+")) {
            return false;
        }
        String[] splitLocation = test.split("\\s+");
        Set<String> isUnique = new HashSet<>();
        boolean isValid = true;
        for (String ss : splitLocation) {
            if (isValid) {
                isValid = isValidLocationRegex(ss);
                isUnique.add(ss);
            }
        }
        if (isUnique.size() != splitLocation.length) {
            isValid = false;
        }
        return isValid;
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
