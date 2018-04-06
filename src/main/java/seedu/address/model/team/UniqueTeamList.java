package seedu.address.model.team;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.team.exceptions.DuplicateTeamException;
import seedu.address.model.team.exceptions.TeamNotFoundException;

//@@author jordancjq
/**
 * A list of teams that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Team#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTeamList implements Iterable<Team> {

    private final ObservableList<Team> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent team as the given argument.
     */
    public boolean contains(Team toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Returns true if the list contains an equivalent team as the given argument.
     */
    public boolean contains(TeamName toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(t -> t.getTeamName().equals(toCheck));
    }

    /**
     * Returns {@code Team} that is specified by {@code toGet}.
     */
    public Team getTeam(TeamName toGet) {
        requireNonNull(toGet);
        return internalList.stream().filter(t -> t.getTeamName().equals(toGet)).findFirst().get();
    }

    /**
     * Adds a team to the list.
     *
     * @throws DuplicateTeamException if the team to add is a duplicate of an existing team in the list.
     */
    public void add(Team toAdd) throws DuplicateTeamException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTeamException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the team {@code target} in the list with {@code editedTeam}.
     *
     * @throws DuplicateTeamException if the replacement is equivalent to another existing team in the list.
     * @throws TeamNotFoundException if {@code target} could not be found in the list.
     */
    public void setTeam(Team target, Team editedTeam)
            throws DuplicateTeamException, TeamNotFoundException {
        requireNonNull(editedTeam);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TeamNotFoundException();
        }

        if (!target.equals(editedTeam) && internalList.contains(editedTeam)) {
            throw new DuplicateTeamException();
        }

        internalList.set(index, editedTeam);
    }

    public void setTeams(UniqueTeamList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTeams(List<Team> teams) throws DuplicateTeamException {
        requireAllNonNull(teams);
        final UniqueTeamList replacement = new UniqueTeamList();
        for (final Team team : teams) {
            replacement.add(team);
        }
        setTeams(replacement);
    }

    /**
     * Removes the equivalent team from the list.
     *
     * @throws TeamNotFoundException if no such team could be found in the list.
     */
    public boolean remove(Team toRemove) throws TeamNotFoundException {
        requireNonNull(toRemove);
        final boolean teamFoundAndDeleted = internalList.remove(toRemove);
        if (!teamFoundAndDeleted) {
            throw new TeamNotFoundException();
        }
        return teamFoundAndDeleted;
    }

    /**
     * Assign a {@code person} to a {@code team}.
     * @throws DuplicatePersonException if person already exist in the team
     */
    public void assignPersonToTeam(Person person, Team target) throws DuplicatePersonException {
        requireAllNonNull(person, target);

        if (target.getTeamPlayers().contains(person)) {
            throw new DuplicatePersonException();
        }

        target.add(person);
    }

    /**
     * Removes a {@code person} from a {@code team}.
     */
    public void removePersonFromTeam(Person person, Team target) throws PersonNotFoundException {
        requireAllNonNull(person, target);
        try {
            target.remove(person);
        } catch (PersonNotFoundException e) {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Team> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Team> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTeamList // instanceof handles nulls
                && this.internalList.equals(((UniqueTeamList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
