package seedu.address.model.team;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

//@@author jordancjq
/**
 * Represents a Team in the application.
 * Guarantees: details are present and not null, field values are validated, im!mutable
 */
public class Team extends UniquePersonList {

    public static final String MESSAGE_TEAM_CONSTRAINTS = "Team names should be a string";

    private final TeamName teamName;

    /**
     * Every field must be present and not null.
     */
    public Team(TeamName teamName) {
        this.teamName = teamName;
    }

    /**
     * Constructs {@code Team} with {@code teamName} and {@code players}.
     * Every field must be present and not null.
     */
    public Team(TeamName teamName, List<Person> players) {
        this.teamName = teamName;
        try {
            super.setPersons(players);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Team should not have duplicated person from loading from database");
        }
    }

    public TeamName getTeamName() {
        return teamName;
    }

    public ObservableList<Person> getTeamPlayers() {
        return super.asObservableList();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTeamName())
                .append(", ")
                .append(super.asObservableList().size())
                .append(" players: ")
                .append("\n");
        getTeamPlayers().forEach(builder::append);
        return builder.toString();
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Team // instanceof handles nulls
                && this.teamName.equals(((Team) other).teamName));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(teamName);
    }
}
