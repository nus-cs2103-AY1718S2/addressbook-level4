package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.NoPlayerException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;
import seedu.address.model.team.exceptions.DuplicateTeamException;
import seedu.address.model.team.exceptions.TeamNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(Person target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Person person) throws DuplicatePersonException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code teamName}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(TeamName targetTeam) throws TeamNotFoundException;

    /**
     * Sorts players in address book by field in asc or desc order
     * @param field
     * @param order
     */
    void sortPlayers(String field, String order) throws NoPlayerException;

    /** Removes the given {@code tag} from all {@code Person}s. */
    void deleteTag(Tag tag);

    /** Create the given team */
    void createTeam(Team team) throws DuplicateTeamException;

    /** Assign person to team */
    void assignPersonToTeam(Person person, TeamName teamName)
            throws DuplicatePersonException;

    /** Removes person from team */
    void removePersonFromTeam(Person person, TeamName teamName) throws PersonNotFoundException;

    /** Removes the given team */
    void removeTeam(TeamName teamName) throws TeamNotFoundException;

    /** Renames the given team */
    void renameTeam(Team targetTeam, TeamName teamName);

    /** sets the given {@code tag} to color. */
    boolean setTagColour(Tag tag, String colour);

    /** Returns an unmodifiable view of the team list */
    ObservableList<Team> getInitTeamList();
}
