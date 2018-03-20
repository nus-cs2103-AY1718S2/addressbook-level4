package seedu.progresschecker.model;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.util.CollectionUtil.requireAllNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.progresschecker.commons.core.ComponentManager;
import seedu.progresschecker.commons.core.LogsCenter;
import seedu.progresschecker.commons.events.model.ProgressCheckerChangedEvent;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.model.person.exceptions.DuplicatePersonException;
import seedu.progresschecker.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the ProgressChecker data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ProgressChecker progressChecker;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given progressChecker and userPrefs.
     */
    public ModelManager(ReadOnlyProgressChecker progressChecker, UserPrefs userPrefs) {
        super();
        requireAllNonNull(progressChecker, userPrefs);

        logger.fine("Initializing with ProgressChecker: " + progressChecker + " and user prefs " + userPrefs);

        this.progressChecker = new ProgressChecker(progressChecker);
        filteredPersons = new FilteredList<>(this.progressChecker.getPersonList());
    }

    public ModelManager() {
        this(new ProgressChecker(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyProgressChecker newData) {
        progressChecker.resetData(newData);
        indicateProgressCheckerChanged();
    }

    @Override
    public ReadOnlyProgressChecker getProgressChecker() {
        return progressChecker;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateProgressCheckerChanged() {
        raise(new ProgressCheckerChangedEvent(progressChecker));
    }

    @Override
    public synchronized void deletePerson(Person target) throws PersonNotFoundException {
        progressChecker.removePerson(target);
        indicateProgressCheckerChanged();
    }

    @Override
    public synchronized void addPerson(Person person) throws DuplicatePersonException {
        progressChecker.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateProgressCheckerChanged();
    }

    @Override
    public synchronized void sort() {
        progressChecker.sort();
        indicateProgressCheckerChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        progressChecker.updatePerson(target, editedPerson);
        indicateProgressCheckerChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code progressChecker}
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
    public void uploadPhoto(Path path) throws IOException {
        try {
            progressChecker.uploadPhoto(path);
        } catch (FileNotFoundException FNF) {
            throw new FileNotFoundException();
        }
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateProgressCheckerChanged();
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
        return progressChecker.equals(other.progressChecker)
                && filteredPersons.equals(other.filteredPersons);
    }

}
