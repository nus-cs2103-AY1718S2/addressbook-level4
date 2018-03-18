package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.RecipeBookChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final RecipeBook recipeBook;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given recipeBook and userPrefs.
     */
    public ModelManager(ReadOnlyRecipeBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.recipeBook = new RecipeBook(addressBook);
        filteredPersons = new FilteredList<>(this.recipeBook.getPersonList());
    }

    public ModelManager() {
        this(new RecipeBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyRecipeBook newData) {
        recipeBook.resetData(newData);
        indicateRecipeBookChanged();
    }

    @Override
    public ReadOnlyRecipeBook getRecipeBook() {
        return recipeBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateRecipeBookChanged() {
        raise(new RecipeBookChangedEvent(recipeBook));
    }

    @Override
    public synchronized void deletePerson(Person target) throws PersonNotFoundException {
        recipeBook.removePerson(target);
        indicateRecipeBookChanged();
    }

    @Override
    public synchronized void addPerson(Person person) throws DuplicatePersonException {
        recipeBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateRecipeBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        recipeBook.updatePerson(target, editedPerson);
        indicateRecipeBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code recipeBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return recipeBook.equals(other.recipeBook)
                && filteredPersons.equals(other.filteredPersons);
    }

}
