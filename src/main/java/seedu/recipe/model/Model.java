package seedu.recipe.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.recipe.model.recipe.Person;
import seedu.recipe.model.recipe.exceptions.DuplicatePersonException;
import seedu.recipe.model.recipe.exceptions.PersonNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyRecipeBook newData);

    /** Returns the RecipeBook */
    ReadOnlyRecipeBook getRecipeBook();

    /** Deletes the given recipe. */
    void deletePerson(Person target) throws PersonNotFoundException;

    /** Adds the given recipe */
    void addPerson(Person person) throws DuplicatePersonException;

    /**
     * Replaces the given recipe {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the recipe's details causes the recipe to be equivalent to
     *      another existing recipe in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered recipe list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered recipe list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

}
