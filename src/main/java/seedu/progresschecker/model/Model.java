package seedu.progresschecker.model;

import java.io.IOException;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.credentials.GitDetails;
import seedu.progresschecker.model.exercise.Exercise;
import seedu.progresschecker.model.exercise.exceptions.ExerciseNotFoundException;
import seedu.progresschecker.model.issues.Issue;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.model.person.exceptions.DuplicatePersonException;
import seedu.progresschecker.model.person.exceptions.PersonNotFoundException;
import seedu.progresschecker.model.photo.PhotoPath;
import seedu.progresschecker.model.photo.exceptions.DuplicatePhotoException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Issue> PREDICATE_SHOW_ALL_ISSUES = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyProgressChecker newData);

    /** Returns the ProgressChecker */
    ReadOnlyProgressChecker getProgressChecker();

    /** Deletes the given person. */
    void deletePerson(Person target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Person person) throws DuplicatePersonException;

    /** Sorts the persons in ProgressChecker according to their names in alphabetical order */
    void sort();

    //@@author adityaa1998
    /** authenticates git using password */
    void loginGithub(GitDetails gitdetails) throws IOException, CommandException;

    /** authenticates git using password */
    void logoutGithub() throws CommandException;

    /** creates an issue on github */
    void createIssueOnGitHub(Issue issue) throws IOException, CommandException;

    /** reopen issue on github */
    void reopenIssueOnGithub(Index index) throws IOException, CommandException;

    /** closes an issue issue on github */
    void closeIssueOnGithub(Index index) throws IOException, CommandException;

    /**viwes issues of the specified state */
    void listIssues(String state) throws IllegalValueException, IOException, CommandException;

    /**
     * Replaces the fields in Issue {@code index} with {@code editedIssue}.
     *
     * @throws IOException if while updating the issue there is some problem in authentication
     */
    void updateIssue(Index index, Issue editedIssue) throws IOException, CommandException;

    /** Returns unmodifiable view of the filtered issue list */
    ObservableList<Issue> getFilteredIssueList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredIssueList(Predicate<Issue> predicate);
    //@@author

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    //@@author iNekox3
    /**
     * Replaces the given exercise {@code target} with {@code editedExercise}.
     *
     * @throws ExerciseNotFoundException if {@code target} could not be found in the list.
     */
    void updateExercise(Exercise target, Exercise editedExercise)
            throws ExerciseNotFoundException;

    /** Returns an unmodifiable view of the filtered exercise list */
    ObservableList<Exercise> getFilteredExerciseList();

    /**
     * Updates the filter of the filtered exercise list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExerciseList(Predicate<Exercise> predicate);

    //@@author
    /** Uploads the given photo with given path */
    void uploadPhoto(Person target, String path)
            throws PersonNotFoundException, DuplicatePersonException;

    /** Adds a new uploaded photo path */
    void addPhoto(PhotoPath photoPath) throws DuplicatePhotoException;
}
