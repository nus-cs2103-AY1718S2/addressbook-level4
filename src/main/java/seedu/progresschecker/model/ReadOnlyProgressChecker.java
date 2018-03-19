package seedu.progresschecker.model;

import javafx.collections.ObservableList;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.model.tag.Tag;

/**
 * Unmodifiable view of an ProgressChecker
 */
public interface ReadOnlyProgressChecker {

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

}
