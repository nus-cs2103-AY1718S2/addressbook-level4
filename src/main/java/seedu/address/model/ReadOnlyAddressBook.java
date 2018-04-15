package seedu.address.model;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.ObservableList;
import seedu.address.model.alias.Alias;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

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

    //@@author jingyinno
    /**
     * Returns an unmodifiable view of the aliases list.
     * This list will not contain any duplicate aliases.
     */
    ObservableList<Alias> getAliasList();

    /**
     * Returns the HashMap of alias list.
     * This list will not contain any duplicate aliases.
     */
    HashMap<String, String> getAliasMapping();

    /**
     * Returns an ArrayList of ArrayList of alias strings.
     * This list will not contain any duplicate aliases.
     */
    ArrayList<ArrayList<String>> getUiFormattedAliasList();

    /**
     * Resets the alias list to an empty list
     */
    void resetAliasList();
    //@@author

    //@@author yeggasd
    /**
     * Returns the hashed password
     */
    Password getPassword();
    //@@author
}
