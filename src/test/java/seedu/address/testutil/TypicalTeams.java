package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.team.Team;
import seedu.address.model.team.exceptions.DuplicateTeamException;

//@@author jordancjq
/**
 * A utility class containing a list of {@code Team} objects to be used in tests.
 */
public class TypicalTeams {

    public static final Team CHELSEA = new TeamBuilder().withTeamName("Chelsea").build();
    public static final Team LIVERPOOL = new TeamBuilder().withTeamName("Liverpool").build();

    // Manually added - Team's details found in {@code CommandTestUtil}
    public static final Team ARSENAL = new TeamBuilder().withTeamName("Arsenal").build();
    public static final Team BARCELONA = new TeamBuilder().withTeamName("Barcelona").build();

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
    public static AddressBook getTypicalAddressBookWithPersonsAndTeams() {
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
