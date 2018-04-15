package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.cell.Cell;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.user.User;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    //@@author sarahgoh97
    /**
     * Returns an unmodifiable view of the cells list.
     * This list will not contain any duplicate cells.
     */
    ObservableList<Cell> getCellList();

    //@@author zacci
    /**
     * Returns an unmodifiable view of the users list.
     * This list will not contain any duplicate users.
     */
    ObservableList<User> getUserList();
    //@@author

}
