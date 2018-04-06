package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_ARSENAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_BARCELONA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;
import seedu.address.model.team.exceptions.DuplicateTeamException;

//@@author jordancjq
/**
 * A utility class containing a list of {@code Team} objects to be used in tests.
 */
public class TypicalTeams {

    public static final Team CHELSEA = new Team(new TeamName("Chelsea"));
    public static final Team LIVERPOOL = new Team(new TeamName("Liverpool"));

    // Manually added - Team's details found in {@code CommandTestUtil}
    public static final Team ARSENAL = new Team(new TeamName(VALID_TEAM_ARSENAL));
    public static final Team BARCELONA = new Team(new TeamName(VALID_TEAM_BARCELONA));

    private TypicalTeams() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical teams.
     */
    public static AddressBook getTypicalAddressBookWithTeams() {
        AddressBook ab = new AddressBook();
        for (Team team : getTypicalTeams()) {
            try {
                ab.createTeam(team);
            } catch (DuplicateTeamException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical teams and persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Team team : getTypicalTeams()) {
            try {
                ab.createTeam(team);
            } catch (DuplicateTeamException e) {
                throw new AssertionError("not possible");
            }
        }

        for (Person person : TypicalPersons.getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Team> getTypicalTeams() {
        return new ArrayList<>(Arrays.asList(CHELSEA, LIVERPOOL));
    }
}
