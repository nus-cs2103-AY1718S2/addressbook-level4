package seedu.progresschecker.model;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.progresschecker.commons.core.ComponentManager;
import seedu.progresschecker.commons.core.LogsCenter;
import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.commons.events.model.ProgressCheckerChangedEvent;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.credentials.GitDetails;
import seedu.progresschecker.model.exercise.Exercise;
import seedu.progresschecker.model.issues.Issue;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.model.person.exceptions.DuplicatePersonException;
import seedu.progresschecker.model.person.exceptions.PersonNotFoundException;
import seedu.progresschecker.model.photo.PhotoPath;
import seedu.progresschecker.model.photo.exceptions.DuplicatePhotoException;

/**
 * Represents the in-memory model of the ProgressChecker data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ProgressChecker progressChecker;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Exercise> filteredExercises;

    /**
     * Initializes a ModelManager with the given progressChecker and userPrefs.
     */
    public ModelManager(ReadOnlyProgressChecker progressChecker, UserPrefs userPrefs) {
        super();
        requireAllNonNull(progressChecker, userPrefs);

        logger.fine("Initializing with ProgressChecker: " + progressChecker + " and user prefs " + userPrefs);

        this.progressChecker = new ProgressChecker(progressChecker);
        filteredPersons = new FilteredList<>(this.progressChecker.getPersonList());
        filteredExercises = new FilteredList<>(this.progressChecker.getExerciseList());
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

    //@author adityaa1998
    @Override
    public synchronized void loginGithub(GitDetails gitdetails) throws IOException, CommandException {
        progressChecker.loginGithub(gitdetails);
        indicateProgressCheckerChanged();
    }
    @Override
    public synchronized void closeIssueOnGithub(Index index) throws IOException, CommandException {
        progressChecker.closeIssueOnGithub(index);
        indicateProgressCheckerChanged();
    }

    @Override
    public synchronized void createIssueOnGitHub(Issue issue) throws IOException, CommandException {
        progressChecker.createIssueOnGitHub(issue);
        indicateProgressCheckerChanged();
    }
    //@@author

    @Override
    public synchronized void sort() {
        progressChecker.sort();
        indicateProgressCheckerChanged();
    }

    //@@author adityaa1998
    @Override
    public synchronized void reopenIssueOnGithub(Index index) throws IOException, CommandException {
        progressChecker.reopenIssueOnGithub(index);
        indicateProgressCheckerChanged();
    }
    //@@author

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        progressChecker.updatePerson(target, editedPerson);
        indicateProgressCheckerChanged();
    }

    //@@author adityaa1998
    @Override
    public void updateIssue(Index index, Issue editedIssue) throws IOException {
        requireAllNonNull(index, editedIssue);

        progressChecker.updateIssue(index, editedIssue);
        indicateProgressCheckerChanged();
    }
    //@@author

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

    //@@author Livian1107
    @Override
    public void uploadPhoto(Person target, String path)
            throws PersonNotFoundException, DuplicatePersonException {
        progressChecker.uploadPhoto(target, path);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateProgressCheckerChanged();
    }

    @Override
    public void addPhoto(PhotoPath photoPath) throws DuplicatePhotoException {
        progressChecker.addPhotoPath(photoPath);
        indicateProgressCheckerChanged();
    }
    //@@author

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

    //@@author iNekox3
    //=========== Filtered Exercise List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Exercise} backed by the internal list of
     * {@code progressChecker}
     */
    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        return FXCollections.unmodifiableObservableList(filteredExercises);
    }

}
