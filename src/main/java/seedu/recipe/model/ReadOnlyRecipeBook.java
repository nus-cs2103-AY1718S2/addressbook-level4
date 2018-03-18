package seedu.recipe.model;

import javafx.collections.ObservableList;
import seedu.recipe.model.recipe.Person;
import seedu.recipe.model.tag.Tag;

/**
 * Unmodifiable view of an recipe book
 */
public interface ReadOnlyRecipeBook {

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
