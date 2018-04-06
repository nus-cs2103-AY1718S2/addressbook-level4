package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.ParserUtil.UNSPECIFIED_FIELD;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Player's position in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPosition(String)}
 */
public class Position {

    public static final String MESSAGE_POSITION_CONSTRAINTS =
            "Player's position should be an integer from 1 - 4 where 1 - Striker, 2 - Midfield, 3 - Defender, "
                   + "and 4 - Goalkeeper.";

    public static final String RATING_VALIDATION_REGEX = "[1-4]";
    private static final Map<String, String> myMap;
    static {
        Map<String, String> aMap = new HashMap<>();
        aMap.put("1", "Striker");
        aMap.put("2", "Midfielder");
        aMap.put("3", "Defender");
        aMap.put("4", "Goalkeeper");
        myMap = Collections.unmodifiableMap(aMap);
    }
    public final String value;
    /**
     * Constructs an {@code Position}.
     *
     * @param position A valid position.
     */
    public Position(String position) {
        requireNonNull(position);
        checkArgument(isValidPosition(position), MESSAGE_POSITION_CONSTRAINTS);
        this.value = position;
    }

    /**
     * Returns true if a given string is a valid player's position.
     */
    public static boolean isValidPosition(String test) {
        return test.matches(RATING_VALIDATION_REGEX) || test.equals(UNSPECIFIED_FIELD);
    }

    /**
     * Returns position name according to value
     * @return position name
     */
    public String getPositionName() {
        return myMap.get(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Position // instanceof handles nulls
                && this.value.equals(((Position) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
