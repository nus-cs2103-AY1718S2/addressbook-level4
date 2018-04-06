package seedu.address.model.team;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.ParserUtil.UNSPECIFIED_FIELD;

//@@author jordancjq
/**
 * Represents a Team's name in the application.
 * Guarantees: imm utable; is valid as declared in {@link #isValidName(String)}
 */
public class TeamName {
    public static final String MESSAGE_TEAM_NAME_CONSTRAINTS =
            "Team name should only contain alphanumeric characters and spaces,"
            + " and it should not be blank or consist of only numbers";

    /*
     * The first character of the team name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code TeamName}.
     *
     * @param teamName A valid team name.
     */
    public TeamName(String teamName) {
        requireNonNull(teamName);
        checkArgument(isValidName(teamName), MESSAGE_TEAM_NAME_CONSTRAINTS);
        this.fullName = teamName;
    }

    /**
     * Returns true if a given string is a valid team name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX) || test.equals(UNSPECIFIED_FIELD);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TeamName // instanceof handles nulls
                && this.fullName.equals(((TeamName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
}
