package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.Activity;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Activity> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given activity. */
    void deletePerson(Activity target) throws PersonNotFoundException;

    /** Adds the given activity */
    void addPerson(Activity activity) throws DuplicatePersonException;

    /**
     * Replaces the given activity {@code target} with {@code editedActivity}.
     *
     * @throws DuplicatePersonException if updating the activity's details causes the activity to be equivalent to
     *      another existing activity in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Activity target, Activity editedActivity)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered activity list */
    ObservableList<Activity> getFilteredPersonList();

    /**
     * Updates the filter of the filtered activity list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Activity> predicate);

}
