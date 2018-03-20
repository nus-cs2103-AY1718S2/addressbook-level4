package seedu.address.model.building;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Building in National University of Singapore.
 * Guarantees: immutable; is valid as declared in {@link #isValidBuilding(String)}
 */
public class Building {

    public static final String MESSAGE_BUILDING_CONSTRAINTS =
            "Building names should only contain alphanumeric characters and it should not be blank";
    public static final String BUILDING_VALIDATION_REGEX = "\\p{Alnum}+";

    /**
     * Represents an array of Buildings in National University of Singapore
     */
    public static final String[] BUILDINGS = {
        "AS1", "AS2", "AS3", "AS4", "AS5", "AS6", "AS7", "AS8", "COM1", "COM2", "I3", "BIZ1", "BIZ2",
        "SDE", "S1", "S1A", "S2", "S3", "S4", "S4A", "S5", "S8", "S11", "S12", "S13", "S14", "S16",
        "S17", "E1", "E2", "E2A", "E3", "E3A", "E4", "E4A", "E5", "EA", "ERC", "UTSRC", "LT"
    };

    public final String buildingName;

    /**
     * Constructs a {@code Building}.
     *
     * @param buildingName A valid building name.
     */
    public Building(String buildingName) {
        requireNonNull(buildingName);
        checkArgument(isValidBuilding(buildingName), MESSAGE_BUILDING_CONSTRAINTS);
        this.buildingName = buildingName;
    }

    /**
     * Returns true if a given string is a valid building name.
     */
    public static boolean isValidBuilding(String test) {
        boolean isFound = false;
        for (String building : BUILDINGS) {
            if (building.equals(test)) {
                isFound = true;
            }
        }
        return isFound && test.matches(BUILDING_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return buildingName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.person.Name // instanceof handles nulls
                && this.buildingName.equals(((seedu.address.model.person.Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return buildingName.hashCode();
    }

}
