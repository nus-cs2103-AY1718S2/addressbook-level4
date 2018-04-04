//@@author kush1509
package seedu.address.model.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Job's team in contactHeRo.
 * Guarantees: immutable; is valid as declared in {@link #isValidTeam(String)}
 */
public class Team {

    public static final String MESSAGE_TEAM_CONSTRAINTS =
            "Job teams should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the team must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TEAM_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code Team}.
     *
     * @param team A valid team.
     */
    public Team(String team) {
        requireNonNull(team);
        checkArgument(isValidTeam(team), MESSAGE_TEAM_CONSTRAINTS);
        this.value = team;
    }

    /**
     * Returns true if a given string is a valid person team.
     */
    public static boolean isValidTeam(String test) {
        return test.matches(TEAM_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Team // instanceof handles nulls
                && this.value.equals(((Team) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
