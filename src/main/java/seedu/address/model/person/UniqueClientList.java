package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author shookshire
/**
 * A list of client that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Client#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueClientList implements Iterable<Client> {

    private final ObservableList<Client> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent client as the given argument.
     */
    public boolean contains(Client toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a client to the active list.
     *
     * @throws DuplicatePersonException if the client to add is a duplicate of an existing client in the list.
     */
    public void add(Client toAdd, UniqueClientList closedList) throws DuplicatePersonException {
        requireNonNull(toAdd);
        if (contains(toAdd) || closedList.contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds a client to TuitionCor.
     *
     * @throws AssertionError as it's impossible to have a duplicate given that we have checked for duplication
     * before adding it into active list.
     */
    public void add(Client toAdd) throws AssertionError {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new AssertionError("It's impossible to have a duplicate person here");
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the client {@code target} in the list with {@code editedClient}.
     *
     * @throws DuplicatePersonException if the replacement is equivalent to another existing client in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * Returns true if success.
     */
    public Boolean setClient(Client target, Client editedClient, UniqueClientList closedList)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedClient);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.equals(editedClient) && (internalList.contains(editedClient)
                || closedList.contains(editedClient))) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedClient);
        return true;
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(Client toRemove) throws PersonNotFoundException {
        requireNonNull(toRemove);
        final boolean clientFoundAndDeleted = internalList.remove(toRemove);
        if (!clientFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return clientFoundAndDeleted;
    }

    public void setClients(UniqueClientList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setClients(List<Client> clients) throws DuplicatePersonException {
        requireAllNonNull(clients);
        final UniqueClientList replacement = new UniqueClientList();
        for (final Client client : clients) {
            replacement.add(client);
        }
        setClients(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Client> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Client> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueClientList // instanceof handles nulls
                && this.internalList.equals(((UniqueClientList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
